package br.com.orcameu.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.com.orcameu.model.Address;

@Path("/ws")
@RegisterRestClient(configKey = "viacep-webservice")
public interface AddressService {
	
	@GET
	@Path("/{zipCode}/json/unicode")
	@Produces(MediaType.APPLICATION_JSON)
	public Address getUserAddressByZipCode(@PathParam("zipCode") String zipCode);
		
}
