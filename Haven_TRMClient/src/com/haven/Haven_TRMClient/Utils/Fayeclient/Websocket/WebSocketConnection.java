/******************************************************************************
 *
 *  Copyright 2011-2012 Tavendo GmbH
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package com.haven.Haven_TRMClient.Utils.Fayeclient.Websocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.SocketChannel;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.haven.Haven_TRMClient.Utils.ALog;

public class WebSocketConnection implements WebSocket {

   private static final boolean DEBUG = false;
   private static final String TAG = WebSocketConnection.class.getName();

   protected Handler mMasterHandler;

   protected WebSocketReader mReader;
   protected WebSocketWriter mWriter;
   protected HandlerThread mWriterThread;

   protected SocketChannel mTransportChannel;

   private URI mWsUri;
   private String mWsScheme;
   private String mWsHost;
   private int mWsPort;
   private String mWsPath;
   private String mWsQuery;
   private String[] mWsSubprotocols;

   private WebSocket.ConnectionHandler mWsHandler;

   protected WebSocketOptions mOptions;


   /**
    * Asynch socket connector.
    */
   private class WebSocketConnector extends AsyncTask<Void, Void, String> {

      @Override
      protected String doInBackground(Void... params) {

         Thread.currentThread().setName("WebSocketConnector");

         // connect TCP socket
         // http://developer.android.com/reference/java/nio/channels/SocketChannel.html
         //
         try {
            mTransportChannel = SocketChannel.open();

            // the following will block until connection was established or an error occurred!
            mTransportChannel.socket().connect(new InetSocketAddress(mWsHost, mWsPort), mOptions.getSocketConnectTimeout());

            // before doing any data transfer on the socket, set socket options
            mTransportChannel.socket().setSoTimeout(mOptions.getSocketReceiveTimeout());
            mTransportChannel.socket().setTcpNoDelay(mOptions.getTcpNoDelay());

            return null;

         } catch (IOException e) {

            return e.getMessage();
         }
      }

      @Override
      protected void onPostExecute(String reason) {

         if (reason != null) {

            mWsHandler.onClose(WebSocketConnectionHandler.CLOSE_CANNOT_CONNECT, reason);

         } else if (mTransportChannel.isConnected()) {

            try {

               // create WebSocket master handler
               createHandler();

               // create & start WebSocket reader
               createReader();

               // create & start WebSocket writer
               createWriter();

               // start WebSockets handshake
               WebSocketMessage.ClientHandshake hs = new WebSocketMessage.ClientHandshake(mWsHost + ":" + mWsPort);
               hs.mPath = mWsPath;
               hs.mQuery = mWsQuery;
               hs.mSubprotocols = mWsSubprotocols;
               mWriter.forward(hs);

            } catch (Exception e) {

               mWsHandler.onClose(WebSocketConnectionHandler.CLOSE_INTERNAL_ERROR, e.getMessage());

            }

         } else {

            mWsHandler.onClose(WebSocketConnectionHandler.CLOSE_CANNOT_CONNECT, "could not connect to WebSockets server");
         }
      }

   }


   public WebSocketConnection() {
      ALog.d(DEBUG, TAG, "created");
   }


   public void sendTextMessage(String payload) {
      mWriter.forward(new WebSocketMessage.TextMessage(payload));
   }


   public void sendRawTextMessage(byte[] payload) {
      mWriter.forward(new WebSocketMessage.RawTextMessage(payload));
   }


   public void sendBinaryMessage(byte[] payload) {
      mWriter.forward(new WebSocketMessage.BinaryMessage(payload));
   }


   public boolean isConnected() {
      return mTransportChannel != null && mTransportChannel.isConnected();
   }


   private void failConnection(int code, String reason) {

      ALog.d(DEBUG, TAG, "fail connection [code = " + code + ", reason = " + reason);

      if (mReader != null) {
         mReader.quit();
         try {
            mReader.join();
         } catch (InterruptedException e) {
            if (DEBUG) e.printStackTrace();
         }
         //mReader = null;
      } else {
         ALog.d(DEBUG, TAG, "mReader already NULL");
      }

      if (mWriter != null) {
         //mWriterThread.getLooper().quit();
         mWriter.forward(new WebSocketMessage.Quit());
         try {
            mWriterThread.join();
         } catch (InterruptedException e) {
            if (DEBUG) e.printStackTrace();
         }
         //mWriterThread = null;
      } else {
         ALog.d(DEBUG, TAG, "mWriter already NULL");
      }

      if (mTransportChannel != null) {
         try {
            mTransportChannel.close();
         } catch (IOException e) {
            if (DEBUG) e.printStackTrace();
         }
         //mTransportChannel = null;
      } else {
         ALog.d(DEBUG, TAG, "mTransportChannel already NULL");
      }

      if (mWsHandler != null) {
         try {
            mWsHandler.onClose(code, reason);
         } catch (Exception e) {
            if (DEBUG) e.printStackTrace();
         }
         //mWsHandler = null;
      } else {
         ALog.d(DEBUG, TAG, "mWsHandler already NULL");
      }

       ALog.d(DEBUG, TAG, "worker threads stopped");
   }


   public void connect(String wsUri, WebSocket.ConnectionHandler wsHandler) throws WebSocketException {
      connect(wsUri, null, wsHandler, new WebSocketOptions());
   }


   public void connect(String wsUri, WebSocket.ConnectionHandler wsHandler, WebSocketOptions options) throws WebSocketException {
      connect(wsUri, null, wsHandler, options);
   }


   public void connect(String wsUri, String[] wsSubprotocols, WebSocket.ConnectionHandler wsHandler, WebSocketOptions options) throws WebSocketException {

      // don't connect if already connected .. user needs to disconnect first
      //
      if (mTransportChannel != null && mTransportChannel.isConnected()) {
         throw new WebSocketException("already connected");
      }

      // parse WebSockets URI
      //
      try {
         mWsUri = new URI(wsUri);

         if (!mWsUri.getScheme().equals("ws") && !mWsUri.getScheme().equals("wss")) {
            throw new WebSocketException("unsupported scheme for WebSockets URI");
         }

         if (mWsUri.getScheme().equals("wss")) {
            throw new WebSocketException("secure WebSockets not implemented");
         }

         mWsScheme = mWsUri.getScheme();

         if (mWsUri.getPort() == -1) {
            if (mWsScheme.equals("ws")) {
               mWsPort = 80;
            } else {
               mWsPort = 443;
            }
         } else {
            mWsPort = mWsUri.getPort();
         }

         if (mWsUri.getHost() == null) {
            throw new WebSocketException("no host specified in WebSockets URI");
         } else {
            mWsHost = mWsUri.getHost();
         }

         if (mWsUri.getPath() == null || mWsUri.getPath().equals("")) {
            mWsPath = "/";
         } else {
            mWsPath = mWsUri.getPath();
         }

         if (mWsUri.getQuery() == null || mWsUri.getQuery().equals("")) {
            mWsQuery = null;
         } else {
            mWsQuery = mWsUri.getQuery();
         }

      } catch (URISyntaxException e) {

         throw new WebSocketException("invalid WebSockets URI");
      }

      mWsSubprotocols = wsSubprotocols;

      mWsHandler = wsHandler;

      // make copy of options!
      mOptions = new WebSocketOptions(options);

      // use asynch connector on short-lived background thread
      new WebSocketConnector().execute();
   }


   public void disconnect() {
      if (mWriter != null) {
         mWriter.forward(new WebSocketMessage.Close(1000));
      } else {
          ALog.d(DEBUG, TAG, "could not send Close .. writer already NULL");
      }
   }


   /**
    * Create master message handler.
    */
   protected void createHandler() {

      mMasterHandler = new Handler() {

         public void handleMessage(Message msg) {

            if (msg.obj instanceof WebSocketMessage.TextMessage) {

               WebSocketMessage.TextMessage textMessage = (WebSocketMessage.TextMessage) msg.obj;

               if (mWsHandler != null) {
                  mWsHandler.onTextMessage(textMessage.mPayload);
               } else {
                   ALog.d(DEBUG, TAG, "could not call onTextMessage() .. handler already NULL");
               }

            } else if (msg.obj instanceof WebSocketMessage.RawTextMessage) {

               WebSocketMessage.RawTextMessage rawTextMessage = (WebSocketMessage.RawTextMessage) msg.obj;

               if (mWsHandler != null) {
                  mWsHandler.onRawTextMessage(rawTextMessage.mPayload);
               } else {
                   ALog.d(DEBUG, TAG, "could not call onRawTextMessage() .. handler already NULL");
               }

            } else if (msg.obj instanceof WebSocketMessage.BinaryMessage) {

               WebSocketMessage.BinaryMessage binaryMessage = (WebSocketMessage.BinaryMessage) msg.obj;

               if (mWsHandler != null) {
                  mWsHandler.onBinaryMessage(binaryMessage.mPayload);
               } else {
                   ALog.d(DEBUG, TAG, "could not call onBinaryMessage() .. handler already NULL");
               }

            } else if (msg.obj instanceof WebSocketMessage.Ping) {

               WebSocketMessage.Ping ping = (WebSocketMessage.Ping) msg.obj;
                ALog.d(DEBUG, TAG, "WebSockets Ping received");

               // reply with Pong
               WebSocketMessage.Pong pong = new WebSocketMessage.Pong();
               pong.mPayload = ping.mPayload;
               mWriter.forward(pong);

            } else if (msg.obj instanceof WebSocketMessage.Pong) {

               @SuppressWarnings("unused")
               WebSocketMessage.Pong pong = (WebSocketMessage.Pong) msg.obj;

                ALog.d(DEBUG, TAG, "WebSockets Pong received");

            } else if (msg.obj instanceof WebSocketMessage.Close) {

               WebSocketMessage.Close close = (WebSocketMessage.Close) msg.obj;

                ALog.d(DEBUG, TAG, "WebSockets Close received (" + close.mCode + " - " + close.mReason + ")");

               mWriter.forward(new WebSocketMessage.Close(1000));

            } else if (msg.obj instanceof WebSocketMessage.ServerHandshake) {

               @SuppressWarnings("unused")
               WebSocketMessage.ServerHandshake serverHandshake = (WebSocketMessage.ServerHandshake) msg.obj;

                ALog.d(DEBUG, TAG, "opening handshake received");

               if (mWsHandler != null) {
                  mWsHandler.onOpen();
               } else {
                   ALog.d(DEBUG, TAG, "could not call onOpen() .. handler already NULL");
               }

            } else if (msg.obj instanceof WebSocketMessage.ConnectionLost) {

               @SuppressWarnings("unused")
               WebSocketMessage.ConnectionLost connnectionLost = (WebSocketMessage.ConnectionLost) msg.obj;
               failConnection(WebSocketConnectionHandler.CLOSE_CONNECTION_LOST, "WebSockets connection lost");

            } else if (msg.obj instanceof WebSocketMessage.ProtocolViolation) {

               @SuppressWarnings("unused")
               WebSocketMessage.ProtocolViolation protocolViolation = (WebSocketMessage.ProtocolViolation) msg.obj;
               failConnection(WebSocketConnectionHandler.CLOSE_PROTOCOL_ERROR, "WebSockets protocol violation");

            } else if (msg.obj instanceof WebSocketMessage.Error) {

               WebSocketMessage.Error error = (WebSocketMessage.Error) msg.obj;
               failConnection(WebSocketConnectionHandler.CLOSE_INTERNAL_ERROR, "WebSockets internal error (" + error.mException.toString() + ")");

            } else {

               processAppMessage(msg.obj);

            }
         }
      };
   }


   protected void processAppMessage(Object message) {
   }


   /**
    * Create WebSockets background writer.
    */
   protected void createWriter() {

      mWriterThread = new HandlerThread("WebSocketWriter");
      mWriterThread.start();
      mWriter = new WebSocketWriter(mWriterThread.getLooper(), mMasterHandler, mTransportChannel, mOptions);

       ALog.d(DEBUG, TAG, "WS writer created and started");
   }


   /**
    * Create WebSockets background reader.
    */
   protected void createReader() {

      mReader = new WebSocketReader(mMasterHandler, mTransportChannel, mOptions, "WebSocketReader");
      mReader.start();

       ALog.d(DEBUG, TAG, "WS reader created and started");
   }
}
