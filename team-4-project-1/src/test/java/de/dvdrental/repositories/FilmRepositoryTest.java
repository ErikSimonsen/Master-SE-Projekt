package de.dvdrental.repositories;

import de.dvdrental.entities.Category;
import de.dvdrental.entities.Film;
import de.dvdrental.repositories.interfaces.RepositoryTest;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(Arquillian.class)
public class FilmRepositoryTest extends RepositoryTest {

    @Inject
    FilmRepository filmRepository;
    @Inject
    LanguageRepository languageRepository;
    @Inject
    CategoryRepository categoryRepository;

    public Timestamp fromString(String s){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
            Date parsedDate = dateFormat.parse(s);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    @Override
    public void createAndDelete() {
        Film film = new Film("Inception2", "TestDesc", Year.of(1998), (short) 7, new BigDecimal("4.99"), (short) 90, new BigDecimal("12.99"),
                Film.Rating.R, languageRepository.get(2), categoryRepository.get(2));
        filmRepository.create(film);
        film.setCategory(categoryRepository.get(7));
        filmRepository.update(film);
        Film film2 = filmRepository.get(film.getFilmId());
        Assert.assertEquals(Film.Rating.R, film2.getRating());
        Assert.assertEquals("Inception2", film2.getTitle());
        Assert.assertEquals("TestDesc", film2.getDescription());
        Assert.assertEquals(Year.of(1998), film2.getReleaseYear());
        Assert.assertEquals(Short.valueOf("7"), film2.getRentalDuration());
        Assert.assertEquals(new BigDecimal("4.99"), film2.getRentalRate());
        Assert.assertEquals(new BigDecimal("12.99"), film2.getReplacementCost());
        Assert.assertEquals(Short.valueOf("90"), film2.getLength());
        Assert.assertEquals(languageRepository.get(2), film2.getLanguage());
        Assert.assertEquals("Italian             ", film2.getLanguage().getName());
        Assert.assertEquals(film, film2);
        Assert.assertNotNull(film2.getCategory());
        Assert.assertEquals(film2.getCategory(), categoryRepository.get(7));

        filmRepository.delete(film2);
        Assert.assertFalse(filmRepository.existsById(film.getFilmId()));
        Assert.assertNull(filmRepository.get(film.getFilmId()));
    }

    @Override
    @Test
    public void createAllAndDeleteAll() {
        Film f1 = new Film("Inception2", "TestDesc", Year.of(2001), (short) 7, new BigDecimal("4.99"), (short) 90, new BigDecimal("12.99"),
                Film.Rating.R, languageRepository.get(1), categoryRepository.get(1));

        Film f2 = new Film("Inception3", "TestDesc", Year.of(2015), (short) 7, new BigDecimal("4.99"), (short) 90, new BigDecimal("12.99"),
                Film.Rating.R, languageRepository.get(2), categoryRepository.get(2));

        List<Film> films = Arrays.asList(f1, f2);
        filmRepository.createAll(films);
        Assert.assertEquals(f1, filmRepository.get(f1.getFilmId()));
        Assert.assertEquals(f2, filmRepository.get(f2.getFilmId()));

        filmRepository.deleteAll(films);
        Assert.assertFalse(filmRepository.existsById(f1.getFilmId()));
        Assert.assertFalse(filmRepository.existsById(f2.getFilmId()));
    }

    @Override
    @Test
    public void get() {
        Film f = filmRepository.get(16);
        Assert.assertEquals(16, f.getFilmId());
        Assert.assertEquals("A Fast-Paced Drama of a Robot And a Composer who must Battle a Astronaut in New Orleans", f.getDescription());
        Assert.assertEquals("Alley Evolution", f.getTitle());
        Assert.assertEquals(Year.of(2006), f.getReleaseYear());
        Assert.assertEquals(languageRepository.get(1), f.getLanguage());
        Assert.assertEquals("English             ", f.getLanguage().getName());
        Assert.assertEquals(Short.valueOf("6"), f.getRentalDuration());
        Assert.assertEquals(new BigDecimal("2.99"), f.getRentalRate());
        Assert.assertEquals(Short.valueOf("180"), f.getLength());
        Assert.assertEquals(new BigDecimal("23.99"), f.getReplacementCost());
        Assert.assertEquals(Film.Rating.NC_17, f.getRating());
    }

    @Override
    @Test
    public void getAll() {
        Assert.assertEquals(1000, filmRepository.getAll().size());
    }

    @Override
    @Test
    public void getAllById() {
        List<Integer> ids = IntStream.range(1, 201).boxed().collect(Collectors.toList());
        List<Film> films = filmRepository.getAllById(ids);

        Assert.assertEquals(200, films.size());

        Film f = films.get(15);
        Assert.assertEquals(16, f.getFilmId());
        Assert.assertEquals("A Fast-Paced Drama of a Robot And a Composer who must Battle a Astronaut in New Orleans", f.getDescription());
        Assert.assertEquals("Alley Evolution", f.getTitle());

        Assert.assertEquals(filmRepository.get(200), films.get(199));
        Assert.assertEquals(filmRepository.get(1), films.get(0));
    }

    @Override
    @Test
    public void count() {
        Assert.assertEquals(Long.valueOf(1000), filmRepository.count());
    }

    @Override
    @Test
    public void existsById() {
        Assert.assertTrue(filmRepository.existsById(67));
        Assert.assertFalse(filmRepository.existsById(1200));
    }

    @Override
    @Test
    public void update() {
        Film f = filmRepository.get(3);
        f.setTitle("inception yeah");
        filmRepository.update(f);

        Assert.assertEquals("inception yeah", filmRepository.get(f.getFilmId()).getTitle());
    }

    @Test
    public void getCategory() {
        Film film = filmRepository.get(1);
        Category category = film.getCategory();
        Assert.assertNotNull(category);
        Assert.assertEquals(category, categoryRepository.get(6));
        Assert.assertNotEquals(category, categoryRepository.get(1));

        film = filmRepository.get(13);
        category = film.getCategory();
        Assert.assertNotNull(category);
        Assert.assertEquals(category, categoryRepository.get(11));
        Assert.assertNotEquals(category, categoryRepository.get(1));

        category = categoryRepository.get(1);
        film.setCategory(category);
        filmRepository.update(film);
        Assert.assertEquals(filmRepository.get(13).getCategory(), category);

        Assert.assertEquals(categoryRepository.get(1), filmRepository.get(13).getCategory());
    }
}
