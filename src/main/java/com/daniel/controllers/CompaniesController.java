package com.daniel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.daniel.entities.Company;
import com.daniel.daoInterfaces.ICompaniesDAO;
import com.daniel.enums.ErrorTypes;
import com.daniel.exceptions.ApplicationException;

@Controller
public class CompaniesController {

	@Autowired
	private ICompaniesDAO companiesDao;

//	@Autowired
//	private UsersController usersController;
//	
//	@Autowired
//	private CouponsController couponsController;
	
	public CompaniesController() {
	}
	
	// ----------------- Primary controllers: ----------------- //
	
	public void addCompany(Company company) throws ApplicationException {
		validateCreateCompany(company);
		try{
			companiesDao.save(company);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.COMPANY_ADD_FAILED, "Failed to add company");
		}
	}

	public void updateCompany(Company company)  throws ApplicationException {
		if (!isCompanyExistsById(company.getId())) {
			throw new ApplicationException(ErrorTypes.INVALID_COMPANY, "Company doesn't exists.");
		}
		try{
			companiesDao.save(company);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.COMPANY_UPDATE_FAILED, "Failed to update company");
		}
	}
	
	public void deleteCompany(long companyId) throws ApplicationException {
		if (!isCompanyExistsById(companyId)) {
			throw new ApplicationException(ErrorTypes.INVALID_COMPANY, "Company doesn't exists.");
		}
//		deleteCompanyUsers(companyId);
//		deleteCompanyCoupons(companyId);
		try {
			this.companiesDao.deleteById(companyId);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.COMPANY_DELETE_FAILED, "Fail to delete company");
		}
	}

	public Company getCompany(Long companyId) throws ApplicationException {
		try {
			return companiesDao.findById(companyId).get();
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_COMPANY, "Fail to get company");
		}
	}
	
	public List<Company> getAllCompanies() throws ApplicationException {
		try {
			List<Company> companies = (List<Company>) companiesDao.findAll();
			return companies;
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_COMPANY, "Fail to get companies list");
		}
	}

	public Company getCompanyByName(String name) throws ApplicationException {
		if (!isCompanyExistByName(name)) {
			throw new ApplicationException(ErrorTypes.INVALID_COMPANY, "Company doesn't exists.");
		}
		try {
			return companiesDao.findCompanyByName(name);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_COMPANY, "Fail to get company by name");
		}
	}
	// ----------------- Secondary controllers: ----------------- //

//	private void deleteCompanyUsers(long companyId) throws ApplicationException {
//		this.usersController.deleteCompanyUsers(companyId);
//	}
	
	
//	private void deleteCompanyCoupons(long companyId) throws ApplicationException {
//		this.couponsController.deleteCouponsByCompanyId(companyId);
//	}
	
	private void validateCreateCompany(Company company) throws ApplicationException {
		if (isCompanyExistByName(company.getName())) {
			throw new ApplicationException(ErrorTypes.INVALID_COMPANY_NAME, "company name already taken");
		}
	}
	
	private boolean isCompanyExistByName(String companyName) throws ApplicationException {
		try{
			return companiesDao.existsByName(companyName);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.COMPANY_GENERAL_ERROR, "Failed to check if company exists");
		}
	}
	
	public boolean isCompanyExistsById(Long companyId) throws ApplicationException {
		try {
			return companiesDao.existsById(companyId);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.COMPANY_GENERAL_ERROR, "Failed to check if company exists");
		}
	}
}
