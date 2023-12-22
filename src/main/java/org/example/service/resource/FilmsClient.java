package org.example.service.resource;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static io.restassured.RestAssured.given;
import static org.example.utils.RequestSpec.getRequestSpec;


@Slf4j
public class FilmsClient extends BaseResourceClient {

    private Response response;
    private static final String FILMS_ENDPOINT = "/api/films";


    /**
     * Constructor
     */
    public FilmsClient() {}


    /**
     * Get ALL people
     *
     * @return Response
     */
    public Response getFilms() {

        reqSpec = getRequestSpec();

        response = given(reqSpec).log().uri()
                .get(FILMS_ENDPOINT)
                .then().log().all()
                .extract().response();

        return response;
    }

}
