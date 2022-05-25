package com.deloitte.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.deloitte.demo.entities.ToDoItem;
import com.deloitte.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

/**
 * Unit test cases for ToDo application
 * 
 * @author Sagar Parmar
 *
 */
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TodoAppApplicationTests {

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private UserService userService;

	@Test
	@Order(1)
	void contextLoads() {
	}

	/**
	 * Validate that method throws exception for invalid user login
	 * 
	 * @throws IOException
	 */
	@Test
	@Order(2)
	public void invalidLogin() throws IOException {
		// Fetch response
		String username = "dummyUser";
		UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
				() -> userService.getUserByUsername(username, false),
				"Expected exception of class: " + UsernameNotFoundException.class.getName());

		// Validate exception message
		String exceptionMessage = "User with username '" + username + "' not found";
		assertTrue((exception.getMessage() != null && exception.getMessage().equalsIgnoreCase(exceptionMessage)),
				"Invalid exception message");
	}

	/**
	 * Validate master data configuration and fetch ToDo list saved against
	 * logged-in user
	 * 
	 * @throws IOException
	 */
	@Test
	@Order(3)
	public void fetchAndCheckMasterData() throws IOException {
		// Fetch response
		ResponseEntity<String> result = template.withBasicAuth("user", "pass@123").getForEntity("/getToDoList",
				String.class);

		// Perform validations
		if (result.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			fail("Invalid credentials configured");
		}

		assertEquals(HttpStatus.OK, result.getStatusCode(),
				"Request failed with error code: " + result.getStatusCode());
		assertTrue(result.hasBody(), "Request body is empty or missing");

		List<ToDoItem> savedToDoItems = jsonArrayToList(result.getBody(), ToDoItem.class);
		assertTrue(savedToDoItems.size() == 3, "Invalid master data configuration");
	}

	/**
	 * Validate that application is able to saved updated ToDo list against
	 * logged-in user
	 * 
	 * @throws IOException
	 */
	@Test
	@Order(4)
	public void updateToDoList() throws IOException {
		List<ToDoItem> toDoList = new ArrayList<>();
		toDoList.add(new ToDoItem("Task A", "Task Description A", true));
		toDoList.add(new ToDoItem("Task B", "Task Description B", false));

		String toDoJsonList = new ObjectMapper().writeValueAsString(toDoList);

		// Create HTTP Entity
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(toDoJsonList, headers);

		// Fetch Response
		ResponseEntity<String> result = template.withBasicAuth("user", "pass@123").postForEntity("/saveToDoList",
				entity, String.class);

		// Perform validations
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(result.hasBody());

		List<ToDoItem> savedToDoItems = jsonArrayToList(result.getBody(), ToDoItem.class);
		assertTrue(savedToDoItems.size() == 2);
		assertTrue(savedToDoItems.stream().anyMatch(item -> item.getName().equalsIgnoreCase("Task B")));
	}

	/**
	 * Helper method to type class JSON array to ArrayList of targeted class
	 * 
	 * @param <T>
	 * @param json
	 * @param elementClass
	 * @return ArrayList of targeted class
	 * @throws IOException
	 */
	private <T> ArrayList<T> jsonArrayToList(String json, Class<T> targetClass) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, targetClass);
		return objectMapper.readValue(json, listType);
	}
}
