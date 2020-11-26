package de.dvdrental.entities.utils;

import java.util.Objects;

public class Href {
    private String href;

    public Href() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Href href1 = (Href) o;
        return Objects.equals(href, href1.href);
    }

    @Override
    public int hashCode() {
        return Objects.hash(href);
    }
}
