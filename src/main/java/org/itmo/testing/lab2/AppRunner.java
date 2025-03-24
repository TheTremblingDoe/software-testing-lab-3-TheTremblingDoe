package org.itmo.testing.lab2;

import org.itmo.testing.lab2.controller.UserAnalyticsController;
import io.javalin.Javalin;

public class AppRunner {
    public static void main(String[] args) {
        Javalin app = UserAnalyticsController.createApp();
        app.start(7000);
    }
}