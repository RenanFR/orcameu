package br.com.orcameu.service;

import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.orcameu.model.Address;
import br.com.orcameu.model.User;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class UserServiceTest {
	
	@InjectMock
	@RestClient
	AddressService addressService;

	@Inject
	UserService userService;	

	@Test
	public void shouldSaveUserWithAddressReturned() {
		Address address = new Address();
		address.setZipCode("08555660");
		address.setStreet("Rua Porto Rico");
		address.setDistrict("Jardim América");
		address.setCity("Poá");
		address.setState("SP");
		Mockito.when(addressService.getUserAddressByZipCode("05359-001")).thenReturn(address);
		User user = new User();
		user.setEmail("aanthonyiagorocha@tam.com.br");
		user.setUsername("Anthony Iago Rocha");
		user.setPassword("Anthony");
		user.setRoles("USER");
		Address addressUser = new Address();
		addressUser.setZipCode("05359-001");
		user.setAddress(addressUser);
		User testUser = userService.save(user);
		System.out.println(testUser);
		System.out.println(address);
		Assertions.assertTrue(testUser.getAddress().getStreet().equals(address.getStreet()));
	}
	
}
