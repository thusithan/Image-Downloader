package com.thusitha.contentdownloader;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ResourceFetcher extends AsyncTask<Object, String, String> {
    private String TAG = "ImageDownloader";
    public ResourceFetcherResponse delegate = null;
    String restResponse = "";
    Context context;
    public ResourceFetcher(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... objects) {
        String url = "http://pastebin.com/raw/wgkJgazE";

        RequestQueue queue = Volley.newRequestQueue(context);
        RequestFuture<String> future = RequestFuture.newFuture();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,future,future);

        queue.add(stringRequest);
        try {
            restResponse = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return restResponse;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONArray jsonArray = new JSONArray(restResponse);
            JSONObject[] resultJsonObjects = new JSONObject[jsonArray.length()];
            for (int i = 0 ; i < jsonArray.length() ; i++){
                resultJsonObjects[i] = jsonArray.getJSONObject(i).getJSONObject("urls");
            }
            delegate.getResponseJsonArray(resultJsonObjects);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
