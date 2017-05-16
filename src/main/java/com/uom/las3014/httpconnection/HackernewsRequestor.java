package com.uom.las3014.httpconnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

@Component
public class HackernewsRequestor {
    @Value("${com.uom.las3014.hackernews.base.url}")
    private String baseUrl;

    public JsonObject getItem(final Integer itemNo) throws IOException {
        final URL url = new URL(baseUrl + "item/" + itemNo + ".json");
        final HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setRequestMethod("GET");

        final BufferedReader br = new BufferedReader(new InputStreamReader((httpUrlConnection.getInputStream())));
        final String responseBody = br.lines().collect(Collectors.joining());

        return new JsonParser().parse(responseBody).getAsJsonObject();
    }

    public JsonObject getNewItem() throws IOException {
        final URL url = new URL(baseUrl + "newstories.json");
        final HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setRequestMethod("GET");

        final BufferedReader br = new BufferedReader(new InputStreamReader((httpUrlConnection.getInputStream())));
        final String responseBody = br.lines().collect(Collectors.joining());

        return new JsonParser().parse(responseBody).getAsJsonObject();
    }
}
