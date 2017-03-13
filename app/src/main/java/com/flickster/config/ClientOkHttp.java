package com.flickster.config;

import okhttp3.OkHttpClient;

/**
 * Created by Amod on 3/12/17.
 */

public class ClientOkHttp {

    private static OkHttpClient okHttpClient = null;

    private ClientOkHttp() {

    }

    public static OkHttpClient getOkHttpClient() {
        if(okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }
}
