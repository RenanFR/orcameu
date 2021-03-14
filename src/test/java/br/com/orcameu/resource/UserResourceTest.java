package br.com.orcameu.resource;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import br.com.orcameu.service.UserService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class UserResourceTest {
	
	@Inject
	UserService userService;	
	
	@Test
	public void shouldGetUserAddress() {
		userService.insertTestUser();
		RestAssured
			.given()
			.auth()
			.basic("renanfr1047@gmail.com", "Renan")
			.get("/users/address")
			.then()
			.statusCode(200);
	}	

}
