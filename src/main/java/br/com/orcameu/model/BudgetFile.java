package br.com.orcameu.model;

import java.io.File;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class BudgetFile {

    @FormParam("budgetFileRaw")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}	
	
}
