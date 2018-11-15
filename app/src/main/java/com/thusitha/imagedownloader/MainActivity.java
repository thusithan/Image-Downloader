package com.thusitha.imagedownloader;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.thusitha.contentdownloader.ImageDownloader;
import com.thusitha.contentdownloader.ImageDownloaderResponse;
import com.thusitha.contentdownloader.ResourceFetcher;
import com.thusitha.contentdownloader.ResourceFetcherResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements ResourceFetcherResponse , ImageDownloaderResponse{


    ResourceFetcher resourceFetcher;
    private JSONObject[] JsonObjectArray;
    private ImageView firstImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstImage = (ImageView) findViewById(R.id.imgFirst);

        resourceFetcher = new ResourceFetcher(MainActivity.this);
        resourceFetcher.delegate = MainActivity.this;
        resourceFetcher.execute();
    }

    @Override
    public void getResponseJsonArray(JSONObject[] response) throws JSONException {
        this.JsonObjectArray = response;
        Object[] imageDownloadParameters = new Object[3];
        imageDownloadParameters[0] = JsonObjectArray;
        imageDownloadParameters[1] = 2;
        imageDownloadParameters[2] = "small";

        ImageDownloader imageDownloader = new ImageDownloader(MainActivity.this);
        imageDownloader.delegate = MainActivity.this;
        imageDownloader.execute(imageDownloadParameters);
    }

    @Override
    public void getBitMapArray(Bitmap[] imageArray) {
        //Adding images to recycler view is done here
        firstImage.setImageBitmap(imageArray[0]);
    }
}
