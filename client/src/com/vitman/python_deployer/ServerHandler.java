package com.vitman.python_deployer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ServerHandler {

    private static final String ERROR = "ERROR";
    // JSON Node names
    private static final String TAG_RESULT = "RESULT";
    private static final String TAG_RUNTIME = "RUNTIME";
    private static final String TAG_SCRIPT_SIZE = "SCRIPT_SIZE";
    private static final String TAG_LINES = "LINES";
    private static final String TAG_STRINGS = "STRINGS";
    private static final String TAG_NUMBERS = "NUMBERS";
    private static final String TAG_CLASSES = "CLASSES";
    private static final String TAG_FUNCTIONS = "FUNCTIONS";
    private static final String TAG_BINARY_OPERATIONS = "BINARY_OP";
    private static final String TAG_WHILE_STATEMENTS = "WHILE_ST";
    private static final String TAG_FOR_STATEMENTS = "FOR_ST";
    private static final String TAG_IF_STATEMENTS = "IF_ST";
    private static final String TAG_SCRIPT = "SCRIPT";

    static String response;
    private static String mJSONString;

    /**
     * Making server request
     *
     * @param url    - servers url to make request
     * @param script - string to send to server
     */
    public String makeServerRequest(String url, String script) {
        try {
            //http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(TAG_SCRIPT, script);
            mJSONString = jsonObject.toString();
            httpPost.setEntity(new StringEntity(mJSONString));

            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    //Getting string from json object
    public String parseJSONObject(JSONObject jsonObject) {
        StringBuffer result = new StringBuffer();
        try {
            result.append("CODE STATISTICS:\n");
            String formatRuntime = String.format("%.3f", jsonObject.get(TAG_RUNTIME));
            result.append("Runtime: " + formatRuntime + " seconds\n");
            result.append("Script size: " + jsonObject.get(TAG_SCRIPT_SIZE) + " bytes\n");
            result.append("Lines: " + jsonObject.get(TAG_LINES) + '\n');
            result.append("Strings: " + jsonObject.get(TAG_STRINGS) + '\n');
            result.append("Numbers: " + jsonObject.get(TAG_NUMBERS) + '\n');
            result.append("Classes: " + jsonObject.get(TAG_CLASSES) + '\n');
            result.append("Functions: " + jsonObject.get(TAG_FUNCTIONS) + '\n');
            result.append("Binary operations: " + jsonObject.get(TAG_BINARY_OPERATIONS) + '\n');
            result.append("While statements: " + jsonObject.get(TAG_WHILE_STATEMENTS) + '\n');
            result.append("For statements: " + jsonObject.get(TAG_FOR_STATEMENTS) + '\n');
            result.append("If statements: " + jsonObject.get(TAG_IF_STATEMENTS) + "\n\n");
            result.append("RESULT:\n");
            result.append(jsonObject.get(TAG_RESULT));
        } catch (JSONException e) {
            e.printStackTrace();
            return ERROR;
        }
        return result.toString();
    }
}
