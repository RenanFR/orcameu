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
	public void save(User userDto) {
		Address userAddress = this.addressService.getUserAddressByZipCode(userDto.getAddress().getZipCode());
		userDto.setAddress(userAddress);
		userDto.setPassword(BcryptUtil.bcryptHash(userDto.getPassword()));
		userDto.persistAndFlush();
	}		

}
