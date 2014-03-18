package com.trm.trmclient.Utils.Fayeclient;

import org.json.JSONObject;

public interface FayeListener {
	// When connected
	public void onConnected();

	// When disconnected
	public void onDisconnected();

	// When subscribed
	public void onSubscribed(String channel);

	// When un-subscribed
	public void onUnSubscribed(String channel);

	// When got error
	public void onError(String error, Exception e);

	// When received message
	public void onMessage(JSONObject message);
}
