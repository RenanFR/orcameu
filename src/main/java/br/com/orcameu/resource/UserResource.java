package br.com.orcameu.resource;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import br.com.orcameu.model.User;
import br.com.orcameu.service.UserService;

@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserResource {
	
	@Inject
	UserService userService;
	
	@Transactional
	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postNewUser(User userDto) {
		System.out.println(userDto);
		userService.save(userDto);
	    return Response.ok(userDto).build();
	}
	
    @GET
    @Path("/address")
    @RolesAllowed("USER")
    public Response getUserAddress(@Context SecurityContext securityContext) {
    	User user = User.findById(securityContext.getUserPrincipal().getName());
		return Response.ok(user.getAddress()).build();
    }	    	

}