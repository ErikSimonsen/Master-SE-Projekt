package de.dvdrental.microservices.interfaces;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class MicroserviceClient {
    protected final Client client;
    protected final String targetURI;

    public MicroserviceClient(String targetURI) {
        this.client = ClientBuilder.newClient();
        this.targetURI = targetURI;
    }

    public boolean testTargetService() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(targetURI).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public <T> T get(String uri, GenericType<T> genericType, Map<String, Object> queryParameters) {
        WebTarget webTarget = client.target(uri);
        for (Map.Entry<String, Object> entry: queryParameters.entrySet()) {
            webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
        }
        return webTarget
                .request(APPLICATION_JSON)
                .get(genericType);
    }

    public <T> T get(String uri, GenericType<T> genericType) {
        return get(uri, genericType, Map.of());
    }

    public <T> Integer post(String uri, T object, Map<String, Object> queryParameters) {
        WebTarget webTarget = client.target(uri);
        for (Map.Entry<String, Object> entry: queryParameters.entrySet()) {
            webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
        }

        Response response = webTarget.request(APPLICATION_JSON)
                                     .post(Entity.entity(object, APPLICATION_JSON));

        String[] segments = response.getLocation().getPath().split("/");
        return Integer.parseInt(segments[segments.length - 1]);
    }

    public <T> Integer post(String uri, T object) {
        return post(uri, object, Map.of());
    }

    public void put(String uri) {
        client
            .target(uri)
            .request(APPLICATION_JSON)
            .put(Entity.json(null));
    }
}
