package de.dvdrental.resources.proxies;

public class CityProxy {
    private String city;
    private String county;
    private Integer id;

    public CityProxy(String city, String county, Integer id) {
        this.city = city;
        this.county = county;
        this.id = id;
    }

    public CityProxy() {
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
