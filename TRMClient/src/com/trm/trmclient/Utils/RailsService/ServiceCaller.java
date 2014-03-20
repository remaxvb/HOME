package com.trm.trmclient.Utils.RailsService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.trm.trmclient.Constants.ServerInfo;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Constants.ServicePath;
import com.trm.trmclient.Utils.ALog;

public class ServiceCaller {
	private final boolean DEBUG = true;

	/**
	 * Create Http asynctask, execute and return result as JSONObject
	 */

	private final String TAG = ServiceCaller.class.getSimpleName();
	private final String AUTH_USERNAME = "admin";
	private final String AUTH_PASSWORD = "admin";
	private final int AUTH_PORT = 80;

	private String urlBasePath;
	
	private int errorCode = 0;

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

	/**
	 * Execute webservice with some option
	 * @param context : Application context
	 * @param serviceType : Type of service, in {@link ServiceEventConstant}
	 * @param isShowProgress : true: Show progress
	 * @param postData : JSON post data
	 * @param progressTitle : title of progress
	 */
	public void executeService(Context context,
			ServiceEventConstant serviceType, boolean isShowProgress,
			JSONObject postData, String progressTitle) {
		this.progressTitle = progressTitle;
		this.serviceType = serviceType;
		executeHttp(context, isShowProgress, getServiceURL(serviceType),
				postData);
		ALog.d(DEBUG, TAG, serviceType + " with data: " + postData.toString());
	}

	private String getServiceURL(ServiceEventConstant serviceType) {
		if (serviceType == ServiceEventConstant.SIGN_IN) {
			return ServicePath.URL_SIGN_IN;
		}
		
		if(serviceType == ServiceEventConstant.SIGN_UP) {
			return ServicePath.URL_SIGN_UP;
		}
		if(serviceType==ServiceEventConstant.CREATE_TOUR){
			return ServicePath.URL_CREATE_TOUR;
		}
		if(serviceType==ServiceEventConstant.UPDATE_TOUR){
			return ServicePath.URL_UPDATE_TOUR;
		}
        if (serviceType == ServiceEventConstant.DELETE_TOUR){
            return ServicePath.URL_DELETE_TOUR;
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
		urlBasePath = "http://" + ServerInfo.URL_HOST + serviceUrl;
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
				serviceCallback.onError(serviceType, errorCode);
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
		final HttpParams httpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParams, 25000);
		HttpClient httpClient = new DefaultHttpClient(httpParams);

		HttpResponse httpResponse = null;
		String responeJson = null;

		AuthScope authScope = new AuthScope(ServerInfo.URL_HOST, AUTH_PORT);
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
		} catch (ConnectTimeoutException e) {
			errorCode = 408;
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			errorCode = -1;
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			errorCode = -1;
			e.printStackTrace();
		} catch (IOException e) {
			errorCode = 405;
			e.printStackTrace();
		}

		if (responeJson != null) {
			try {
				return new JSONObject(responeJson);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public interface ServiceCallback {
		public void onReceiveResponse(ServiceEventConstant serviceType, JSONObject data);

		public void onError(ServiceEventConstant serviceType, int errorCode);
	}
}
