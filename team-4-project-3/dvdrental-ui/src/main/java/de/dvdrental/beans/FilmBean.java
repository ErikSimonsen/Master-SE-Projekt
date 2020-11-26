package de.dvdrental.beans;

import de.dvdrental.entities.Film;
import de.dvdrental.microservices.FilmClient;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Named
@ViewScoped
public class FilmBean implements Serializable {
    @Inject
    FilmClient filmClient;

    private final Integer NUM_ENTRIES_ON_PAGE = 50;

    private List<Film> films;
    private List<String> pages;
    private Integer currentPage = 1;
    private String search;

    protected FilmBean() {
    }

    @PostConstruct
    public void init() {
        int numPages = (int) Math.ceil(filmClient.getNumFilms().doubleValue() / NUM_ENTRIES_ON_PAGE);
        pages = IntStream.rangeClosed(1, numPages).mapToObj(String::valueOf).collect(Collectors.toList());
        refreshFilms();
    }

    public void onSearchChange() {
        currentPage = 1;
        refreshFilms();
    }

    public void refreshFilms() {
        if (search != null && !search.isEmpty()) {
            films = filmClient.searchFilms(search, NUM_ENTRIES_ON_PAGE, (currentPage - 1) * NUM_ENTRIES_ON_PAGE);
        } else {
            films = filmClient.getAllFilms(NUM_ENTRIES_ON_PAGE, (currentPage - 1) * NUM_ENTRIES_ON_PAGE);
        }
    }

    public void nextPage() {
        currentPage++;
    }

    public void previousPage() {
        currentPage--;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
