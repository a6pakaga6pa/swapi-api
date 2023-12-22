package org.example.service.resource;

import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.taskdefs.condition.Matches;
import org.example.service.entity.PeopleEntity;
import org.example.service.entity.PeopleResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.example.utils.RequestSpec.getRequestSpec;

@Slf4j
public class PeopleClient extends BaseResourceClient {

    private Response response;
    private static final String PEOPLE_ENDPOINT = "/api/people";
    private static final String SINGLE_ENDPOINT = "/api/people/%s";
    private List<PeopleEntity> allList = new ArrayList<>();


    /**
     * Constructor
     */
    public PeopleClient() {
    }

    /**
     * Get ALL people
     *
     * @return Response
     */
    public List<PeopleEntity> getAllPeopleEntities(int... page) {
        int pageToSearch = 1;
        reqSpec = getRequestSpec();
        if (page.length != 0) {
            pageToSearch = page[0];
        }
        var currentEntity = given(reqSpec).log().uri()
                .param("page", pageToSearch)
                .get(PEOPLE_ENDPOINT)
                .then().log().all()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("peopleResponseSchema.json"))
                .extract().response().as(PeopleResponseEntity.class);
        if (currentEntity.getNext() != null) {
            allList.addAll(currentEntity.getResults());
            getAllPeopleEntities(Integer.parseInt(currentEntity.getNext().split("page=")[1]));
        } else {
            allList.addAll(currentEntity.getResults());
        }

        return allList;

    }

    /**
     * Find single person
     *
     * @return Response
     */
    public Response getPerson(String id) {

        reqSpec = getRequestSpec();
        var endpointId = String.format(SINGLE_ENDPOINT, id);

        response = given(reqSpec).log().uri()
                .get(endpointId)
                .then().log().all()
                .extract().response();

        return response;
    }

}
