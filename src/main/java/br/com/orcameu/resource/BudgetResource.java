package br.com.orcameu.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.com.orcameu.model.Address;
import br.com.orcameu.service.AddressService;

@Path("/budget")
public class BudgetResource {
	
	@Inject
	@RestClient
	AddressService addressService;
	
    @GET
    @Path("/address/{zipCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Address getUserAddress(@PathParam("zipCode") String zipCode) {
    	Address userAddress = this.addressService.getUserAddressByZipCode(zipCode);
		return userAddress;
    }	    
    
}
