package org.example.utils;

import io.restassured.specification.RequestSpecification;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

public class RequestSpec {

    public static RequestSpecification getRequestSpec() {
        return
                given().contentType(MediaType.APPLICATION_JSON)
                        .when();
    }

}
