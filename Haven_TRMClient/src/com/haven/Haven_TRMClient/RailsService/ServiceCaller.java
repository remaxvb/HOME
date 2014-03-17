package com.haven.Haven_TRMClient.RailsService;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.haven.Haven_TRMClient.Constants.ServiceEventConstant;

public class ServiceCaller {

	/**
	 * Create Http asynctask, execute and return result as JSONObject
	 */

	private final String TAG = ServiceCaller.class.getSimpleName();

	// public static final String URL_HOST = "trm-rails.herokuapp.com";
//	public final String URL_HOST = "192.168.79.1:3000"; // Host for local test
    public final String URL_HOST = "192.168.10.149:3000"; // Ethan server
	public static final String URL_SIGN_IN = "/api/users/sign_in";
	public static final String URL_SIGN_UP = "/api/users/sign_up";
	public static final String URL_UPDATE_ACCOUNT = "/api/users/update";
	
	public static final String URL_CREATE_TOUR = "/api/tours/create";

	private final String AUTH_USERNAME = "admin";
	private final String AUTH_PASSWORD = "admin";
	private final int AUTH_PORT = 80;

	private String urlBasePath;

	private String progressTitle;
	
	private ServiceEventConstant serviceType;

	private ServiceCallback serviceCallback;

	private static ServiceCaller instance = new ServiceCaller();

	public static synchronized ServiceCaller getInstance() {
		return instance;
	}

	public void setServiceCallback(ServiceCallback sc) {
		this.serviceCallback = sc;
	}

	private ServiceCaller() {

	}

	public void executeService(Context context,
			ServiceEventConstant serviceType, boolean isShowProgress,
			JSONObject postData, String progressTitle) {
		this.progressTitle = progressTitle;
		this.serviceType = serviceType;
		executeHttp(context, isShowProgress, getServiceURL(serviceType),
				postData);
	}

	private String getServiceURL(ServiceEventConstant serviceType) {
		if (serviceType == ServiceEventConstant.SIGN_IN) {
			return URL_SIGN_IN;
		}
		
		if(serviceType == ServiceEventConstant.SIGN_UP) {
			return URL_SIGN_UP;
		}
		return null;
	}

	/**
	 * Make a http POST
	 * 
	 * @param context
	 *            : UI thread call this service
	 * @param isShowProgress
	 *            : Do you want to show the progress dialog during the task
	 * @param serviceUrl
	 *            : Service address, exampe: /api/login
	 * @param data
	 *            : Json data you want to post
	 */
	private void executeHttp(Context context, boolean isShowProgress,
			final String serviceUrl, JSONObject data) {
		urlBasePath = "http://" + URL_HOST + serviceUrl;
		new HttpAsyncTask(context, isShowProgress)
				.execute(new JSONObject[] { data });
	}

	/**
	 * Http POST task support
	 * 
	 * @author hieu.t.vo
	 * 
	 */
	class HttpAsyncTask extends AsyncTask<JSONObject, Void, JSONObject> {

		private Context context;
		boolean isShowPregress;

		public HttpAsyncTask(Context context, boolean isShowProgress) {
			this.context = context;
			this.isShowPregress = isShowProgress;
		}

		private ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			if (isShowPregress) {
				pd = new ProgressDialog(context);
				pd.setTitle(progressTitle);
				pd.show();
			}

		}

		@Override
		protected JSONObject doInBackground(JSONObject... params) {
			return sendPost(params[0]);
		}

		@Override
		protected void onPostExecute(JSONObject response) {
			if (isShowPregress) {
				pd.dismiss();
			}
			if (response != null) {
				serviceCallback.onReceiveResponse(serviceType, response);
			} else {
				serviceCallback.onError(serviceType, "Cannot connect to server!");
			}

		}

	}

	/**
	 * Support method: Make a http POST
	 * 
	 * @param requestJson
	 * @return: Response as JSONObject
	 */
	private JSONObject sendPost(JSONObject requestJson) {
		HttpClient httpClient = new DefaultHttpClient();

		HttpResponse httpResponse = null;
		String responeJson = null;

		AuthScope authScope = new AuthScope(this.URL_HOST, AUTH_PORT);
		UsernamePasswordCredentials userCreds = new UsernamePasswordCredentials(
				AUTH_USERNAME, AUTH_PASSWORD);
		((AbstractHttpClient) httpClient).getCredentialsProvider()
				.setCredentials(authScope, userCreds);
		BasicHttpContext localContext = new BasicHttpContext();
		BasicScheme basicAuth = new BasicScheme();
		localContext.setAttribute("preemptive-auth", basicAuth);
		HttpPost httpPost = new HttpPost(this.urlBasePath);
		try {
			StringEntity strEntity = new StringEntity(requestJson.toString());
			httpPost.setEntity(strEntity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			httpResponse = httpClient.execute(httpPost, localContext);

			responeJson = EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {
			Log.d(TAG, e.getMessage());
			return null;
		}

		if (responeJson != null) {
			try {
				return new JSONObject(responeJson);
			} catch (JSONException e) {
				Log.d(TAG, e.getMessage());
			}
		}
		return null;
	}

	public interface ServiceCallback {
		public void onReceiveResponse(ServiceEventConstant serviceType, JSONObject data);

		public void onError(ServiceEventConstant serviceType, Object data);
	}
}
