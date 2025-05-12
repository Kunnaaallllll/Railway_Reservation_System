package com.example.user_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.user_service.model.Role;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;

@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner{
     
	@Autowired
	UserRepository userRepository;
	public static void main(String[] args) {
		
		SpringApplication.run(UserServiceApplication.class, args);
	}
	public void run(String... args) {//String... args can accept multiple Strings of fixed size
		User adminAcc = userRepository.findByRole(Role.ADMIN);
		if(adminAcc==null) {
			User user=new User();
			user.setGmail("admin@gmail");
			user.setName("Kunal");
			user.setUsername("kunal1");
			user.setAge(22);
			user.setGender("Male");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("kunal"));
			userRepository.save(user);
		}
	}
}
