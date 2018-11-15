package com.thusitha.contentdownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ImageDownloader  extends AsyncTask<Object, String, String> implements ContentDownloader {
    private String TAG = "ImageDownloader";
    public ImageDownloaderResponse delegate = null;
    private Context context;
    private Bitmap[] imageArray;

    public ImageDownloader(Context context){
        this.context = context;


    }


    @Override
    public void downloadContent() {


    }

    @Override
    protected String doInBackground(Object... objects) {

        JSONObject[] jsonArray = (JSONObject[]) objects[0];
        int count = (int) objects[1];
        String element = (String) objects[2];

        imageArray = new Bitmap[count];
        int arrayIndex = 0;

        for(int i = 0 ; i < count && i < jsonArray.length ; i++)
            try {
                JSONObject jsonObject = (JSONObject) jsonArray[i];
                Log.d(TAG, "URL: " + jsonObject.getString(element) + "\n");
                URL url = new URL(jsonObject.getString(element));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                imageArray[arrayIndex] = myBitmap;
                arrayIndex++;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "Post execute task started");
        delegate.getBitMapArray(imageArray);
    }
}
