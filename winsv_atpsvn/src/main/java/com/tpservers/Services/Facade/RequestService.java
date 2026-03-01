package com.tpservers.Services.Facade;

import com.google.gson.JsonObject;
import com.tpservers.Core.RequestCore;

public final class RequestService {

    private RequestService() {
    }

    private static class Holder {

        static final RequestService INSTANCE = new RequestService();
    }

    public static RequestService getInstance() {
        return Holder.INSTANCE;
    }

    public static String post(String url, JsonObject headers, JsonObject payload) throws Exception {

        return RequestCore.post(url, headers, payload);
    }
}