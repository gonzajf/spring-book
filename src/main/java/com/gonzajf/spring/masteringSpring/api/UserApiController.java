package com.gonzajf.spring.masteringSpring.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gonzajf.spring.masteringSpring.exception.EntityNotFoundException;
import com.gonzajf.spring.masteringSpring.model.User;
import com.gonzajf.spring.masteringSpring.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserApiController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		
		HttpStatus status = HttpStatus.OK;
		
		if(!userRepository.exists(user.getEmail())) {
			status = HttpStatus.CREATED;
		}
		User saved = userRepository.save(user);
		return new ResponseEntity<>(saved, status);
	}
	
	@PutMapping("/users/{email}")
	public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) throws EntityNotFoundException {
		User saved = userRepository.update(email,user);
		return new ResponseEntity<User>(saved, HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{email}")
	public ResponseEntity<User> deleteUser(@PathVariable String email) throws EntityNotFoundException {
		userRepository.delete(email);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}