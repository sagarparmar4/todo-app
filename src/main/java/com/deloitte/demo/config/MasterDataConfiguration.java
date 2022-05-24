package com.deloitte.demo.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.deloitte.demo.entities.ToDoItem;
import com.deloitte.demo.entities.User;
import com.deloitte.demo.repositories.ToDoItemRepository;
import com.deloitte.demo.services.UserService;

/**
 * Configuration class to add default master data in database
 * 
 * @author Sagar Parmar
 *
 */
@Configuration
public class MasterDataConfiguration {

	@Autowired
	UserService userService;

	@Autowired
	ToDoItemRepository toDoItemRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * Add default users with few ToDo items against them
	 */
	@PostConstruct
	public void addDefaultUsers() {
		List<ToDoItem> toDoList = new ArrayList<>();
		toDoList.add(new ToDoItem("Item Name A", "Item Description A", true));
		toDoList.add(new ToDoItem("Item Name B", "Item Description B", false));
		toDoList.add(new ToDoItem("Item Name C", "Item Description C", true));

		List<ToDoItem> savedToDoList = toDoItemRepository.saveAll(toDoList);

		User user = new User();
		user.setFirstName("Dummy");
		user.setLastName("User");
		user.setUsername("user");
		user.setPassword(passwordEncoder.encode("pass@123"));
		user.setToDoItems(savedToDoList);

		userService.addUser(user);
	}
}
