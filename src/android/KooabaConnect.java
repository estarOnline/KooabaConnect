package org.apache.cordova.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Base64;

/**
 * This class echoes a string called from JavaScript.
 */
public class KooabaConnect extends CordovaPlugin {
    private String conttype = "";
    private String auth = "";
    private String todaydate = "";
    private String bodyData = "";
    private String kooabaUrl = "http://query-api.kooaba.com";
    private String kooabaPath = "/v4/query";
	
	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("connect")) {
            conttype = args.getString(0);
            auth = args.getString(1);
            todaydate = args.getString(2);
            bodyData = args.getString(3);
                        
            this.connect(callbackContext);
            return true;
        }
        return false;
    }

    private void connect(CallbackContext callbackContext) {
    	// Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(this.kooabaUrl + this.kooabaPath);
        
        try {
        	httppost.addHeader("Accept", "application/json; charset=utf-8");
        	httppost.addHeader("Authorization", auth);
        	httppost.addHeader("Date", todaydate);
        	httppost.addHeader("Content-Type", conttype);
        	
        	byte[] bodyArray = Base64.decode(bodyData, Base64.DEFAULT);
        	
        	httppost.setEntity(new ByteArrayEntity(bodyArray));
        	
        	HttpResponse response = httpclient.execute(httppost);
        	int responseCode = response.getStatusLine().getStatusCode();
        	switch(responseCode) {
        		case 200:
        			HttpEntity entity = response.getEntity();
        			if(entity != null) {
        				String responseBody = EntityUtils.toString(entity);
        				callbackContext.success(responseBody);
        			}
        			break;
        		default:
        			callbackContext.error("Response not able to be read");
        			break;
        	}
        	
        	
   
        } catch (Exception e) {
        	callbackContext.error("Response not able to be read");
        }       
    }
}