package com.daniel.daoInterfaces;

import org.springframework.data.repository.CrudRepository;
import com.daniel.entities.Company;

public interface ICompaniesDAO extends CrudRepository<Company, Long>{

	Company findCompanyByName(String name);

	boolean existsByName(String companyName);

	boolean existsById(Long companyId);

}