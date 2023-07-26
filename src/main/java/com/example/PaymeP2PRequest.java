package com.example;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PaymeP2PRequest {
    private static final String API_URL = "https://payme.uz/api/p2p.request";

    public static void main(String[] args) throws Exception {
        // Set up the request parameters
        String card = "6281d1f8bebe7ab52e0bc4b9";
        long amount = 100000;

        // Create a JSON object to hold the parameters
        Gson gson = new Gson();
        String json = gson.toJson(new RequestParams(card, amount));

        // Send the API request
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(API_URL);
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(post);

        // Read the API response
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }

    private static class RequestParams {
        public String card;
        public long amount;

        public RequestParams(String card, long amount) {
            this.card = card;
            this.amount = amount;
        }
    }
}
