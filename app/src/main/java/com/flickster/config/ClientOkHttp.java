package com.flickster.config;

import okhttp3.OkHttpClient;

/**
 * Singleton class for getting OkHttpClient
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
