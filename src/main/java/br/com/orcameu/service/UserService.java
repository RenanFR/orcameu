package br.com.orcameu.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.com.orcameu.model.Address;
import br.com.orcameu.model.User;
import io.quarkus.elytron.security.common.BcryptUtil;

@ApplicationScoped
public class UserService {
	
	@Inject
	@RestClient
	AddressService addressService;
	
	@Transactional
	public User save(User userDto) {
		Address userAddress = this.addressService.getUserAddressByZipCode(userDto.getAddress().getZipCode());
		userDto.setAddress(userAddress);
		userDto.setPassword(BcryptUtil.bcryptHash(userDto.getPassword()));
		userDto.persistAndFlush();
		return userDto;
	}		
	
	public User insertTestUser() {
		User user = new User();
		user.setEmail("renanfr1047@gmail.com");
		user.setUsername("Renan Rodrigues");
		user.setPassword("Renan");
		user.setRoles("USER");
		Address address = new Address();
		address.setZipCode("05359-001");
		user.setAddress(address);
		this.save(user);
		return user;	
	}		

}
