package com.example.paint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

class BrowserTest extends Application {


    @Test
    void browserTest() {
        browser("http://google.com");
    }

    public void browser(String url) {
        getHostServices().showDocument(url);
    }

    @Override
    public void start(Stage stage) throws Exception {}
}

