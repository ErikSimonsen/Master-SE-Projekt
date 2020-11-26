package de.dvdrental.resources.proxies;

import javax.json.bind.annotation.JsonbProperty;
import java.net.URI;

public class ActorFilmsProxy {

    private String title;
    @JsonbProperty("href")
    private URI uri;

    public ActorFilmsProxy() {
    }

    public ActorFilmsProxy(String title, URI uri) {
        this.title = title;
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
