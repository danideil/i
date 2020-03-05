package com.daniel.daoInterfaces;

import org.springframework.data.repository.CrudRepository;
import com.daniel.entities.User;

public interface IUsersDAO extends CrudRepository<User, Long>{

	public void deleteUsersByCompany(long companyId);
	public User findByEmailAndPassword(String email, String password);
	public boolean existsByEmail(String email);
}
