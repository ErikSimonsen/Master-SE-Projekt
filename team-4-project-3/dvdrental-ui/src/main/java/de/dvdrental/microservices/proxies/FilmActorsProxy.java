package de.dvdrental.microservices.proxies;

import de.dvdrental.entities.utils.Href;

import javax.json.bind.annotation.JsonbProperty;

public class FilmActorsProxy {
    @JsonbProperty("firstname-lastname")
    private String name;
    private String href;

    public FilmActorsProxy() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
