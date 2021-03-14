package br.com.orcameu;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class BudgetResourceTest {
	
	@Test
	public void shouldGetAddressByZipCode() {
		String zipCode = "05359-001";
		RestAssured
			.given()
			.get("/budget/address/" + zipCode)
			.then()
			.statusCode(200);
	}	

}
