package com.deloitte.demo.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.deloitte.demo.entities.User;
import com.deloitte.demo.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Add or update user to database
	 * 
	 * @param user
	 * @return Saved user instance
	 */
	@Transactional
	public User addUser(User user) {
		if (this.getUserByUsername(user.getUsername()) != null) {
			throw new RuntimeException("Username already exists");
		}
		return this.saveUser(user);
	}

	/**
	 * Fetch user based on username. Return {@code null} if user is not present
	 * 
	 * @param username
	 * @return User
	 */
	public User getUserByUsername(String username) {
		return this.getUserByUsername(username, true);
	}

	/**
	 * Fetch user based on username. Optionally return {@code null} if user is not
	 * present
	 * 
	 * @param username
	 * @return User
	 */
	public User getUserByUsername(String username, boolean returnNull) {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		}
		if (returnNull) {
			return null;
		}
		throw new UsernameNotFoundException("User with username '" + username + "' not found");
	}

	/**
	 * Save user to database
	 * 
	 * @param user
	 * @return Saved user instance
	 */
	public User saveUser(User user) {
		Assert.isTrue(StringUtils.isNotBlank(user.getUsername()), "Username cannot be empty or blank");
		return userRepository.save(user);
	}

	/**
	 * Get logged-in user from security context
	 * 
	 * @return Logged-in user principal type cased to User object
	 */
	public User getLoggedInUser() {
		Object userContext = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userContext != null && userContext instanceof UserDetails) {
			return (User) userContext;
		} else {
			throw new RuntimeException("Unable to fetch logged-in user");
		}
	}

}
