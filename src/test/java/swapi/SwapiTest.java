package swapi;

import io.restassured.response.Response;
import org.apache.http.entity.ContentType;
import org.example.service.entity.FilmsResponseEntity;
import org.example.service.entity.PeopleEntity;
import org.example.service.resource.FilmsClient;
import org.example.service.resource.PeopleClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.testng.Assert.assertEquals;

public class SwapiTest {

    private FilmsClient filmsClient;
    private PeopleClient peopleClient;
    private Response response;

    @BeforeClass
    public void init() {
        filmsClient = new FilmsClient();
        peopleClient = new PeopleClient();
    }

    @Test
    public void findTallestPersonInTheMovieTest() {
        response = filmsClient.getFilms();
        assertEquals(response.statusCode(), HTTP_OK, "Status code did not return " + HTTP_OK);
        assertEquals(response.getContentType(), ContentType.APPLICATION_JSON.getMimeType(), "Content type is not " + ContentType.APPLICATION_JSON);
        var filmsResponseEntity = response.as(FilmsResponseEntity.class);

        var filmWithLatestRelease = filmsResponseEntity.getResults().stream().max(Comparator.comparing(
                film -> LocalDate.parse(film.getReleaseDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")
                )
        )).orElseThrow();

        var peopleFromFilm = filmWithLatestRelease.getCharacters().stream()
                .map(url -> peopleClient.getPerson(url.split("people/")[1])
                        .as(PeopleEntity.class))
                .collect(Collectors.toList());

        var tallestPerson = peopleFromFilm.stream().max((Comparator.comparing(
                person -> Integer.valueOf(person.getHeight()))
        )).orElseThrow();

        System.out.printf("The tallest person in '%s' movie is '%s' with height of '%s'%n", filmWithLatestRelease.getTitle(), tallestPerson.getName(), tallestPerson.getHeight());
    }

    @Test
    public void findTallestPerson() {
        var allPeopleEntities = peopleClient.getAllPeopleEntities();

        var tallestPerson = allPeopleEntities.stream()
                .filter(person -> !"unknown".equals(person.getHeight()))
                .max((Comparator.comparing(
                person -> Integer.valueOf(person.getHeight()))
        )).orElseThrow();

        System.out.printf("The tallest person ever in SW movie is '%s' with height of '%s'%n", tallestPerson.getName(), tallestPerson.getHeight());
    }

}
