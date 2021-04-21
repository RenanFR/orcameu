package br.com.orcameu.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.com.orcameu.model.BudgetEntry;
import br.com.orcameu.model.BudgetFile;
import br.com.orcameu.model.User;
import br.com.orcameu.service.BudgetService;

@Path("/budget")
public class BudgetResource {
	
	@Inject
	BudgetService budgetService;
	
	@POST
    @Path("/import")
	@Transactional
    @RolesAllowed("USER")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importFromExcel(@Context SecurityContext securityContext, @MultipartForm BudgetFile budgetFileRaw) throws Exception {
		User user = User.findById(securityContext.getUserPrincipal().getName());
		budgetService.importFromExcel(user, budgetFileRaw.getFile());
		return Response.ok().build();
    }
	
	@GET
	@Path("/entries")
	@RolesAllowed("USER")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAllEntriesFromUserBudget(@Context SecurityContext securityContext, 
			@QueryParam("year") Integer year,
	        @QueryParam("month") Integer month) throws Exception {
		User user = User.findById(securityContext.getUserPrincipal().getName());
		List<BudgetEntry> entries = budgetService.getAllEntriesFromUserBudget(user, year, month);
		return Response.ok(entries).build();
	}	    	
	
	
}
