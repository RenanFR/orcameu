package br.com.orcameu;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import br.com.orcameu.model.Address;
import br.com.orcameu.model.User;
import br.com.orcameu.service.UserService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class UserResourceTest {
	
	@Inject
	UserService userService;	
	
	private void insertUser() {
		User user = new User();
		user.setEmail("renanfr1047@gmail.com");
		user.setUsername("Renan Rodrigues");
		user.setPassword("Renan");
		user.setRoles("USER");
		Address address = new Address();
		address.setZipCode("05359-001");
		user.setAddress(address);
		userService.save(user);	
	}		
	
	@Test
	public void shouldGetUserAddress() {
		insertUser();
		RestAssured
			.given()
			.auth()
			.basic("renanfr1047@gmail.com", "Renan")
			.get("/users/address")
			.then()
			.statusCode(200);
	}	

}
