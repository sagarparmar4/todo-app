package com.deloitte.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.demo.entities.User;
import com.deloitte.demo.services.UserService;

/**
 * REST controllers for user management
 * 
 * @author Sagar Parmar
 *
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Add or update user to database
	 * 
	 * @param user
	 * @return Saved user instance
	 */
	@PostMapping("/addUser")
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}

	/**
	 * Fetch user based on username. Return {@code null} if user is not present
	 * 
	 * @param username
	 * @return User
	 */
	@GetMapping("/getUser")
	public User getUser(@RequestParam String username) {
		return userService.getUserByUsername(username);
	}

}
