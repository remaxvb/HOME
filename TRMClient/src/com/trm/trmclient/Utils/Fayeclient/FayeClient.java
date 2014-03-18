package com.trm.trmclient.Utils.Fayeclient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.HandlerThread;

import com.trm.trmclient.Constants.ServerInfo;
import com.trm.trmclient.Utils.ALog;
import com.trm.trmclient.Utils.Fayeclient.Websocket.WebSocket;
import com.trm.trmclient.Utils.Fayeclient.Websocket.WebSocketConnection;
import com.trm.trmclient.Utils.Fayeclient.Websocket.WebSocketException;

/**
 * Created by hieu.t.vo on 3/3/14.
 */
public class FayeClient implements WebSocket.ConnectionHandler {
    private static final boolean DEBUG = true;
    private static final String TAG = FayeClient.class.getSimpleName();

    private static final String HANDSHAKE_CHANNEL = "/meta/handshake";
    private static final String CONNECT_CHANNEL = "/meta/connect";
    private static final String DISCONNECT_CHANNEL = "/meta/disconnect";
    private static final String SUBSCRIBE_CHANNEL = "/meta/subscribe";
    private static final String UNSUBSCRIBE_CHANNEL = "/meta/unsubscribe";

    private static final String KEY_CHANNEL = "channel";
    private static final String KEY_SUCCESS = "successful";
    private static final String KEY_CLIENT_ID = "clientId";
    private static final String KEY_VERSION = "version";
    private static final String KEY_MIN_VERSION = "minimumVersion";
    private static final String KEY_SUBSCRIPTION = "subscription";
    private static final String KEY_SUP_CONN_TYPES = "supportedConnectionTypes";
    private static final String KEY_CONN_TYPE = "connectionType";
    private static final String KEY_DATA = "data";
    private static final String KEY_ID = "id";
    private static final String VALUE_VERSION = "1.0";
    private static final String VALUE_MIN_VERSION = "1.0";
    private static final String VALUE_CONN_TYPE = "websocket";

    private static final String KEY_ADVICE = "advice";
    private static final String KEY_TIMEOUT = "timeout";

    private static final int MAX_CONNECTION_ATTEMPTS = 3;
    private static final int RECONNECT_WAIT = 5000;

    private static final int MAX_HANDSHAKE_ATTEMPTS = 3;
    private static final int REHANDSHAKE_WAIT = 2000;

    private WebSocketConnection webSocketConnection;


    private FayeListener fayeListener;//Which class listen faye event

    private HandlerThread handlerThread;
    private Handler handler;

    //if mWaiting = true, websocket connection and faye connection aren't ready
    // every coming command must wait until mWaiting = fase
    private boolean mWaiting = false;

    //Websocket connection flag
    private boolean mWebsocketConnected = false;
    private int mConnectionAttempts = 0;
    private boolean mReconnecting = false;

    //Faye handshake flag
    private boolean mHanshaked = false;
    private int mHandshakeAttempts = 0;
    private boolean mRehandshaking = false;

    //Test
    private BlockingQueue eventQueue;
//    private EventExecutor eventExecutor;

    private String mfayeClientID;


    private List<String> mfayeChannelList;//List of channels that joined

    /**
     * Default constructor
     */
    public FayeClient() {
        webSocketConnection = new WebSocketConnection();//Create new WebsocketConnection

        mfayeChannelList = new ArrayList<String>();//Create list to save all message channels

        //Create handlerthread attach to this thread
        handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    /**
     * Support retry open websocket connection
     */
    private Runnable mConnectionMonitor = new Runnable() {

        @Override
        public void run() {
            if (!mWebsocketConnected) {//If websocket connection is not established
                if (mConnectionAttempts < MAX_CONNECTION_ATTEMPTS) {//Try MAX_CONNECTION_ATTEMPTS times
                    mReconnecting = true; //Set Reconnecting flag
                    mWaiting = true; //Set Waiting flag
                    mConnectionAttempts++;
                    ALog.d(DEBUG, TAG, "Try to open websocket: " + mConnectionAttempts);
                    openWebsocket(); //Open websocket connection
                    getHandler().postDelayed(this, RECONNECT_WAIT); //Post this runnable after RECONNECT_WAIT delay time
                } else {//Tried and could not open websocket connection
                    if (!mWebsocketConnected) {
                        if (fayeListener != null) {
                            fayeListener.onError("Cannot open websocket", null);

                            //Unset flag
                            mWaiting = false;
                            mReconnecting = false;
                            //Reset count variable
                            mConnectionAttempts = 0;
                        }
                    }
                }
            } else {//Websocket connection is established successfully
                getHandler().removeCallbacks(this);
                mWaiting = false;
                mConnectionAttempts = 0;
                mReconnecting = false;
            }
        }
    };

    /**
     * Support retry handshake
     */
    private Runnable mHandshakeMonitor = new Runnable() {
        @Override
        public void run() {
            if (!mHanshaked) {
                if (mHandshakeAttempts < MAX_HANDSHAKE_ATTEMPTS) {
                    mWaiting = true;
                    mHandshakeAttempts++;
                    ALog.d(DEBUG, TAG, "Try to fayeHandshake: " + mHandshakeAttempts);
                    fayeHandshake();
                    getHandler().postDelayed(this, REHANDSHAKE_WAIT);
                } else {
                    if (!mHanshaked) {
                        mWaiting = false;
                        mRehandshaking = false;
                        mHandshakeAttempts = 0;
                        if (fayeListener != null) {
                            fayeListener.onError("Cannot handshake", null);
                        }
                    }
                }
            } else {
                getHandler().removeCallbacks(this);
                mWaiting = false;
                mRehandshaking = false;
                mHandshakeAttempts = 0;
            }
        }
    };

    /**
     * Make faye handshake
     */
    private void fayeHandshake() {
        try {
            // Supported connection types
            JSONArray connTypes = new JSONArray();
            connTypes.put("long-polling");
            connTypes.put("callback-polling");
            connTypes.put("iframe");
            connTypes.put("websocket");
            // Fill the fayeHandshake request format
            JSONObject json = new JSONObject();
            json.put(KEY_CHANNEL, HANDSHAKE_CHANNEL);
            json.put(KEY_VERSION, VALUE_VERSION);
            json.put(KEY_MIN_VERSION, VALUE_MIN_VERSION);
            json.put(KEY_SUP_CONN_TYPES, connTypes);
            // Send request by web socket
            sendTextMessage(json.toString());
        } catch (JSONException ex) {
            // Failed
            fayeListener.onError("Handshake failed", ex);
        }
    }

    /**
     * Make faye connect
     */
    private void fayeConnect() {
        try {
            // Fill connection format
            JSONObject json = new JSONObject();
            json.put(KEY_CHANNEL, CONNECT_CHANNEL);
            json.put(KEY_CLIENT_ID, mfayeClientID);
            json.put(KEY_CONN_TYPE, VALUE_CONN_TYPE);
            // Send request
            sendTextMessage(json.toString());
        } catch (JSONException ex) {
            // Failed
            fayeListener.onError("Connection failed", ex);
        }
    }

    /**
     * Support method, send websocket string message
     * @param message
     */
    private void sendTextMessage(String message) {
        if (mWebsocketConnected) {
            webSocketConnection.sendTextMessage(message);
        } else {
            if (!mReconnecting) {//Call sendTextMessage when connection is not ready
                getHandler().post(mConnectionMonitor);//Retry connect
            }
            if (fayeListener != null) {
                fayeListener.onError("Websocket connection is not ready! Trying to connect, please wait", null);
            }
        }
    }

    /**
     * Faye subscribe method
     * @param fayeSubChannel : Channel want to subscribe
     */
    public void fayeSubscribe(final String fayeSubChannel) {
        try {
            // Fill Subscribe format
            JSONObject json = new JSONObject();
            json.put(KEY_CHANNEL, SUBSCRIBE_CHANNEL);
            json.put(KEY_CLIENT_ID, mfayeClientID);
            json.put(KEY_SUBSCRIPTION, fayeSubChannel);
            sendTextMessage(json.toString());
            if (!mfayeChannelList.contains(fayeSubChannel)) {
                mfayeChannelList.add(fayeSubChannel);
            }
        } catch (JSONException ex) {
            if (fayeListener != null) {
                fayeListener.onError("Subscribe failed", ex);
            }
        }
    }

    /**
     * Faye unsubscribe method
     * @param fayeSubChannel : Channel want to unsubscribe
     */
    public void fayeUnSubscribe(final String fayeSubChannel) {
        try {
            // Fill Subscribe format
            JSONObject json = new JSONObject();
            json.put(KEY_CHANNEL, UNSUBSCRIBE_CHANNEL);
            json.put(KEY_CLIENT_ID, mfayeClientID);
            json.put(KEY_SUBSCRIPTION, fayeSubChannel);
            sendTextMessage(json.toString());
        } catch (JSONException ex) {
            if (fayeListener != null) {
                fayeListener.onError("Unsubscribe failed", ex);
            }
        }
    }

    /**
     * Send message to particular faye channel
     * @param message : message content
     * @param channel : channel want to send
     */
    public void fayePublishMessage(final String message,
                                   final String channel) {
        long number = (new Date()).getTime();
        String messageId = String.format("msg_%d_%d", number, 1);
        try {
            JSONObject json = new JSONObject();
            json.put(KEY_CHANNEL, channel);
            json.put(KEY_CLIENT_ID, mfayeClientID);
            json.put(KEY_DATA, message);
            json.put(KEY_ID, messageId);
            sendTextMessage(json.toString());
        } catch (JSONException ex) {
            if (fayeListener != null) {
                fayeListener.onError("Sending message failed", ex);
            }
        }
    }

    /**
     * Send heart beat
     * @param delay : Send after a delay time
     */
    private void beatHeart(final int delay) {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fayeConnect();
                ALog.d(DEBUG, TAG, "Sent beatheart");
            }
        });
        t.start();
    }

    /**
     * Calculate delay time base on timeout value from server
     * @param jsonMessageElement : message from server
     * @return
     */
    private int calDelay(final JSONObject jsonMessageElement) {
        // Caculate delay time
        int delay = 5000;
        try {
            JSONObject advice = jsonMessageElement.getJSONObject(KEY_ADVICE);
            String timeout = advice.optString(KEY_TIMEOUT);
            if (timeout != null) {
                delay = Integer.valueOf(timeout);
                delay /= 2;
            }

        } catch (JSONException e) {
            delay = 5000;
        }
        return delay;
    }

    /**
     * Parse message from server
     * @param messageArray
     */
    private void parseFayeMessage(final JSONArray messageArray) {
        // Fetch message
        for (int i = 0; i < messageArray.length(); i++) {
            // Convert message to json format
            JSONObject jsonMessageElement = messageArray.optJSONObject(i);
            // Skip null message
            if (jsonMessageElement == null) {
                continue;
            }

            // Get channel
            String channel = jsonMessageElement.optString(KEY_CHANNEL);
            // If successful
            if (channel.equals(HANDSHAKE_CHANNEL)) {
                boolean isSuccessed = jsonMessageElement
                        .optBoolean(KEY_SUCCESS);
                if (!isSuccessed) {// Unsuccess
                    mHanshaked = false;
                    //When handshake error, retry
                    getHandler().post(mHandshakeMonitor);
                    return;
                }
                // Success

                mfayeClientID = jsonMessageElement.optString(KEY_CLIENT_ID);//Save clientID
                ALog.d(DEBUG, TAG, "Handshake successful! ClientID: " + mfayeClientID);
                mHanshaked = true;
                if (fayeListener != null) {
                    fayeListener.onConnected();//Fire callback
                }
                // Send connect message after success handshake
                fayeConnect();
                ALog.d(DEBUG, TAG, "Sent first connect message");
                return;
            }

            if (channel.equals(CONNECT_CHANNEL)) {
                boolean isSuccessed = jsonMessageElement
                        .optBoolean(KEY_SUCCESS);
                if (!isSuccessed) {
                    return;
                }

                // Success
                mHanshaked = true;
                ALog.d(DEBUG, TAG, "Received beatheart");
                // Send connect message to Faye after delay (milisecond)
                beatHeart(calDelay(jsonMessageElement));
                return;
            }

            if (channel.equals(SUBSCRIBE_CHANNEL)) {
                boolean isSuccessed = jsonMessageElement
                        .optBoolean(KEY_SUCCESS);
                if (!isSuccessed) {
                    return;
                }

                // Success
                String subChannel = jsonMessageElement
                        .optString(KEY_SUBSCRIPTION);
                ALog.d(DEBUG, TAG, "Subscribe successful, channel: " + subChannel);
                ALog.d(DEBUG, TAG, "Channel list: " + mfayeChannelList.toString());
                if (fayeListener != null) {
                    fayeListener.onSubscribed(subChannel);
                }
                return;
            }

            if (channel.equals(DISCONNECT_CHANNEL)) {
                boolean isSuccessed = jsonMessageElement
                        .optBoolean(KEY_SUCCESS);
                if (!isSuccessed) {
                    return;
                }

                // Success
                ALog.d(DEBUG, TAG, "Disconnect successful!");
                mHanshaked = false;
                closeWebSoketConnection();
                if (fayeListener != null) {
                    fayeListener.onDisconnected();
                }

                return;
            }

            if (channel.equals(UNSUBSCRIBE_CHANNEL)) {
                boolean isSuccessed = jsonMessageElement
                        .optBoolean(KEY_SUCCESS);
                if (!isSuccessed) { //Unsuccessful
                    return;
                }

                // Successful
                String subChannel = jsonMessageElement
                        .optString(KEY_SUBSCRIPTION);

                ALog.d(DEBUG, TAG, "Unsubscribe successful, channel: " + subChannel);
                mfayeChannelList.remove(subChannel);//Remove unsubscrible channel
                ALog.d(DEBUG, TAG, "Channel list: " + mfayeChannelList.toString());

                if (fayeListener != null) {
                    fayeListener.onUnSubscribed(subChannel);
                }

                return;
            }

            if (mfayeChannelList.contains(channel)) {
                ALog.d(DEBUG, TAG, "Received message: " + jsonMessageElement.toString());
                if (fayeListener != null) {
                    fayeListener.onMessage(jsonMessageElement);
                }
                return;
            }
        }
    }

    private void closeWebSoketConnection() {
        webSocketConnection.disconnect();
        webSocketConnection = null;
    }

    private Handler getHandler() {
        return handler;
    }

    public void setFayeListener(FayeListener fayeListener) {
        this.fayeListener = fayeListener;
    }

    public void startFaye() {
        getHandler().post(mConnectionMonitor);
    }

    private void openWebsocket() {
        try {
            webSocketConnection.connect(ServerInfo.FAYE_URI, this);
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    //When websocket connection already establish
    @Override
    public void onOpen() {
        mWebsocketConnected = true;
        //Faye handshake when websocket is ready
        fayeHandshake();
        ALog.d(DEBUG, TAG, "Websocket opened!");
    }

    @Override
    public void onClose(int code, String reason) {
        mWebsocketConnected = false;
        ALog.d(DEBUG, TAG, reason);
    }

    //When receive websocket message
    @Override
    public void onTextMessage(String payload) {
        try {
            //Parse message, get faye action
            parseFayeMessage(new JSONArray(payload));
        } catch (JSONException e) {
            if (fayeListener != null) {
                fayeListener.onError("Cannot parse faye message", e);
            }
        }
    }

    @Override
    public void onRawTextMessage(byte[] payload) {
    	
    }

    @Override
    public void onBinaryMessage(byte[] payload) {

    }


}
