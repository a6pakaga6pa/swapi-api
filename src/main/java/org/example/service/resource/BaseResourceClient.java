package org.example.service.resource;



import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseResourceClient {

	protected RequestSpecification reqSpec;

	/**
	 * Constructor
	 *
	 */
	public BaseResourceClient() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.registerParser("text/plain", Parser.JSON);
		RestAssured.baseURI = "https://swapi.dev/";
	}

}
