package com.thusitha.contentdownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface ResourceFetcherResponse {
    void getResponseJsonArray(JSONObject[] response) throws JSONException;
}
