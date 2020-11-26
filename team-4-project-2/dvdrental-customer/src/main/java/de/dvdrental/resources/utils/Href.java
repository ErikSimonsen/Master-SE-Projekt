package de.dvdrental.resources.utils;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import java.net.URI;
import java.util.Objects;

public class Href {
    @JsonbProperty("href")
    URI uri;

    public Href() {
        uri = URI.create("");
    }

    public Href(URI uri) {
        this.uri = uri;
    }

    public static Href fromString(String s) {
        return new Href(URI.create(s));
    }

    @JsonbTransient
    public Integer getId() {
        String path = uri.getPath();
        String idStr = path.substring(path.lastIndexOf('/') + 1);
        return Integer.parseInt(idStr);
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Href href = (Href) o;
        return Objects.equals(uri, href.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri);
    }

    @Override
    public String toString() {
        return "Href{" +
                "uri=" + uri +
                '}';
    }
}
