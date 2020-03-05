package com.daniel.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.daniel.entities.Company;
import com.daniel.controllers.CompaniesController;

@RestController
@RequestMapping("/companies")
public class ComapniesApi {

	@Autowired
	private CompaniesController companiesController;

	@PostMapping
	public void addCompany(@RequestBody Company company) throws Exception {
		companiesController.addCompany(company);
	}

	@PutMapping
	public void updateCompany(@RequestBody Company company)  throws Exception {
		companiesController.updateCompany(company);
	}
	
	@DeleteMapping("{companyId}")
	public void deleteCompany(@PathVariable("companyId") Long companyId) throws Exception {
		companiesController.deleteCompany(companyId);
	}
	
	@GetMapping("{companyId}")
	public Company getCompany(@PathVariable("companyId") Long companyId) throws Exception {
		return companiesController.getCompany(companyId);
	}
	
	@GetMapping
	public List<Company> getAllCompanies() throws Exception {
		return companiesController.getAllCompanies();
	}
}
