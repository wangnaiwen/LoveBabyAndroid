package com.wnw.lovebaby.upload;

import com.android.volley.Response;

public interface ResponseListener extends Response.ErrorListener {
    public void onResponse(String response);
}
