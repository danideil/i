package com.daniel.controllers;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.daniel.enums.ErrorTypes;
import com.daniel.enums.UserType;
import com.daniel.beans.SuccessfulLoginData;
import com.daniel.beans.UserBean;
import com.daniel.controllers.CacheController;
import com.daniel.exceptions.ApplicationException;
import com.daniel.entities.User;
import com.daniel.beans.UserLoginDetails;
import com.daniel.daoInterfaces.IUsersDAO;
import com.daniel.data.UserLoginData;

@Controller
public class UsersController {
	
	private static final String ENCRYPTION_TOKEN_SALT = "AASDFHSJFHJHKAAAAA-3423@#$@#$";

	@Autowired
	private IUsersDAO usersDao;
	
	@Autowired
	private CacheController cacheController;
	
	public UsersController() {
	}
	
 	// ----------------- Primary controllers: ----------------- //
	
	public void addUser(UserBean userBean) throws ApplicationException {
		User user = createUserFromBean(userBean);
		validateCreateUser(user);
		// hash user password:
		user.setPassword(String.valueOf(user.getPassword().hashCode()));

		user.setIsActive(true);
		try{
			usersDao.save(user);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.USER_ADD_FAILED, "Failed to create new user"); // change Exception
		}
	}


	public void deleteUser(long userId) throws ApplicationException {
		try {
			usersDao.deleteById(userId); // we decided not to delete? if so:
//			User user = getUser(userId);
//			user.setActive(false);
//			updateUser(user);
		}
		catch (Exception e){
			throw new ApplicationException(e, ErrorTypes.USER_DELETE_FAILED, "Failed to delete user"); // change Exception
		}
	}
	
	public void updateUser(UserBean userBean) throws ApplicationException  {
		User user = createUserFromBean(userBean);
		
		// handle password:
		user.setPassword(String.valueOf(user.getPassword().hashCode()));
		
		try {
			usersDao.save(user);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.USER_UPDATE_FAILED, "Failed to update the user"); // change Exception
		}
	}
	
	public User getUser(long userId) throws ApplicationException  {
		try {
			return usersDao.findById(userId).get();
		}
		catch (Exception e){
			if (e.getCause() == null) {
				throw new ApplicationException(e, ErrorTypes.USER_ERROR_UNKNOWN, "Failed to get user");
			}
			else {
				throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_USER, "Failed to get user");
			}
		}
	}
	
	public List<User> getAllUsers() throws ApplicationException {
		try {
			List<User> users = (List<User>) usersDao.findAll();
			if (users.isEmpty()) {
				throw new ApplicationException(ErrorTypes.USERS_LIST_EMPTY, "no users");
			}
			return users;
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_USER, "Failed to get users list");
		}
	}

	public SuccessfulLoginData login(UserLoginDetails userLoginDetails) throws ApplicationException{
		userLoginDetails.setPassword(String.valueOf(userLoginDetails.getPassword().hashCode()));
		String email = userLoginDetails.getEmail();
		String password = userLoginDetails.getPassword();
		UserLoginData userLoginData = new UserLoginData();
		try {
			User user = (User) usersDao.findByEmailAndPassword(email, password);
			userLoginData.setId(user.getId());
			userLoginData.setUserType(user.getUserType());
			if (user.getUserType().equals(UserType.COMPANY)) {
				userLoginData.setCompanyId(user.getCompany().getId());
			}
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.LOGIN_FAIL, "Failed to get user");
		}
		int token = generateToken(userLoginDetails);
		long id = userLoginData.getId();
		// The following 2 lines are equivalent, just 2 techniques on how to convert an int --> String
		//cacheController.put(token+"", userLoginData);
		cacheController.put(String.valueOf(token), userLoginData);
		
		return new SuccessfulLoginData(id, token, userLoginData.getUserType());
	}
	// ----------------- Secondary controllers: ----------------- //

	
	public void validateCreateUser(User user) throws ApplicationException {
		String email = user.getEmail();
		if (isEmailExist(email)) {
			throw new ApplicationException(ErrorTypes.INVALID_EMAIL, "Email is already taken.");
		}
		if (user.getPassword().length()<6) {
			throw new ApplicationException(ErrorTypes.PASSWORD_VALIDATION_FAILED, "Password is too short.");
		}
		
		if (user.getPassword().length()>12) {
			throw new ApplicationException(ErrorTypes.PASSWORD_VALIDATION_FAILED, "Password is too long.");
		}
		
		boolean isHasCapitalLetter = false;
		int index = 0;
		while(index < user.getPassword().length() && !isHasCapitalLetter) {
			if (user.getPassword().charAt(index)>='A' && user.getPassword().charAt(index)<='Z') {
				isHasCapitalLetter = true;
			}
			index++;
		}
		
		if (!isHasCapitalLetter) {
			throw new ApplicationException(ErrorTypes.PASSWORD_VALIDATION_FAILED, "Password should include both lower and upper case letters.");
		}
		
		boolean isHasLowerCaseLetter = false;
		index = 0;
		while(index < user.getPassword().length() && !isHasLowerCaseLetter) {
			if (user.getPassword().charAt(index)>='a' && user.getPassword().charAt(index)<='z') {
				isHasLowerCaseLetter = true;
			}
			index++;
		}
		
		if (!isHasLowerCaseLetter) {
			throw new ApplicationException(ErrorTypes.PASSWORD_VALIDATION_FAILED, "Password should include both lower and upper case letters.");
		}
	}
	
	private User createUserFromBean(UserBean userBean) {
		User user = new User();
		user.setId(userBean.getId());
		user.setEmail(userBean.getEmail());
		user.setUserName(userBean.getUserName());
		user.setPassword(userBean.getPassword());
		user.setUserType(userBean.getUserType());
		user.setIsActive(userBean.getIsActive());
		user.setCompany(userBean.getCompany());
		return user;
	}
	
	private int generateToken(UserLoginDetails userLoginDetails) {
		String text = userLoginDetails.getEmail() + Calendar.getInstance().getTime().toString() + ENCRYPTION_TOKEN_SALT;
		return text.hashCode();
	}

	@Transactional
	public void deleteCompanyUsers(Long companyId) throws ApplicationException {
		try {
			usersDao.deleteUsersByCompany(companyId);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.DELETE_COMPANY_USERS_FAILED, "Failed to delete company users");
		}
	}

	public boolean isEmailExist(String email) throws ApplicationException {
		try {
			return usersDao.existsByEmail(email);
		}
		catch (Exception e){
			throw new ApplicationException(e, ErrorTypes.USER_EMAIL_FAILED, "Failed to check if email exists");
		}
	}
	public boolean isUserExistById(long userId) throws ApplicationException {
		try {
			return usersDao.existsById(userId);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.INVALID_USER_ID, "Failed to check if user exists");
		}
	}
	
	
}
