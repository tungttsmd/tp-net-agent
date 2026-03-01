package com.tpservers;

import com.tpservers.Core.EventHandler;
import com.tpservers.Services.Service;

import io.github.cdimascio.dotenv.Dotenv;

public class App {
    static {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
    }

    public static void main(String[] args) {
        
        Service.boot();
        EventHandler.boot();

    }
}
