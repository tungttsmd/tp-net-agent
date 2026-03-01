package com.tpservers.Core;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import com.google.gson.JsonObject;
import com.tpservers.Helpers.Tungtt;
import com.tpservers.Services.Facade.ConsoleService;

public class RequestCore {

    private RequestCore() {
    }

    private static class Holder {

        static final RequestCore INSTANCE = new RequestCore();
    }

    public static RequestCore getInstance() {
        return Holder.INSTANCE;
    }

    public static String post(String url, JsonObject headers, JsonObject payload) throws Exception {

        /* ========= CONNECTION ========= */

        HttpURLConnection connection = null;
        try {

            connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            ConsoleService.info("Request URL: " + url);
            ConsoleService.info("Request Method: " + connection.getRequestMethod());
            ConsoleService.info("Request Do Output: " + connection.getDoOutput());
        } catch (Exception e) {

            ConsoleService.error("Request failed");
            ConsoleService.error("Request error message: " + e.getMessage());
            e.printStackTrace();
        }

        /* ========= HEADERS ======== */

        if (headers != null) {
            headers.addProperty("Content-Type", "application/json");
            ConsoleService.info("[HEADER] COUNT: " + headers.size());
            ConsoleService.info("[HEADER] CONTENT-TYPE: " + headers.get("Content-Type").getAsString());
            ConsoleService.info("[HEADER] " + Tungtt.toJson(headers));
            int i = 1;

            for (String key : headers.keySet()) {

                connection.setRequestProperty(key, headers.get(key).getAsString());
                ConsoleService.info("[HEADER] " + i++ + ". " + key + ": " + headers.get(key).getAsString());
            }
        }

        /* ========= PAYLOAD ========= */

        try (OutputStream outStream = connection.getOutputStream()) {

            outStream.write(payload.toString().getBytes());
            ConsoleService.info("[PAYLOAD] " + Tungtt.toJson(payload));
        } catch (Exception e) {

            ConsoleService.error("Request failed");
            ConsoleService.error("Request error message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        /* ========= RESPONSE ========= */

        try {

            return new String(connection.getInputStream().readAllBytes());
        } catch (Exception e) {

            ConsoleService.error("Request failed");
            ConsoleService.error("Request error message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
