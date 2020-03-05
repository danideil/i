package com.daniel.api;
//
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
import com.daniel.beans.UserLoginDetails;
import com.daniel.beans.SuccessfulLoginData;
import com.daniel.beans.UserBean;
import com.daniel.entities.User;
import com.daniel.controllers.UsersController;
import com.daniel.exceptions.ApplicationException;

@RestController
@RequestMapping("/users")
public class UsersApi {

	@Autowired
	private UsersController usersController;
	
	
	//  URL : http://localhost:8080/users/login
	@PostMapping("/login")
	public SuccessfulLoginData login(@RequestBody UserLoginDetails userLoginDetails) throws ApplicationException {
		return this.usersController.login(userLoginDetails);
	}
	
//	@PostMapping
//	public void createUser(@RequestBody User user) throws ApplicationException {
//		this.usersController.addUser(user);
//		System.out.println("user " + user.getId() + " added");
//	}

	@PostMapping
	public void createUser(@RequestBody UserBean userBean) throws ApplicationException {
		this.usersController.addUser(userBean);
		System.out.println("user " + userBean.getId() + " added");
	}
	
	@PutMapping
	public void updateUser(@RequestBody UserBean userBean) throws ApplicationException {
		this.usersController.updateUser(userBean);
		System.out.println("user " + userBean.getId() + " updated");
	}

	@DeleteMapping("{userId}")
	public void deleteUser(@PathVariable("userId") long id) throws ApplicationException {
		this.usersController.deleteUser(id);
		System.out.println(id + "has been deleted");
	}

	@GetMapping("{userId}")
	public User getUser(@PathVariable("userId") long id) throws ApplicationException {
		return this.usersController.getUser(id);
	}
	
	@GetMapping
	public List<User> getAllUsers() throws ApplicationException {
		return this.usersController.getAllUsers();
	}

}
