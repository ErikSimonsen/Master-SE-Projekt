package de.dvdrental.resources.proxies;

import javax.validation.constraints.NotNull;

public class ActorPostProxy {
    @NotNull
    private String firstName;
    private Integer id;
    @NotNull
    private String lastName;

    public ActorPostProxy() {
    }

    public ActorPostProxy(String firstName, Integer id, String lastName) {
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
