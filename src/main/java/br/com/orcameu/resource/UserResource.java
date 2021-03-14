package br.com.orcameu.resource;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.orcameu.model.User;

@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserResource {
	
	@Transactional
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(User userDto) {
		userDto.persistAndFlush();
	    return Response.ok(userDto).build();
	}	

}