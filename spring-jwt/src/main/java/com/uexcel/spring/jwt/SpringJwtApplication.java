package com.uexcel.spring.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.uexcel.spring.jwt.entity.Role;
import com.uexcel.spring.jwt.entity.User;
import com.uexcel.spring.jwt.repository.UserRepository;

@SpringBootApplication
public class SpringJwtApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringJwtApplication.class, args);
	}

	public void run(String... args) {

		User adminAccout = userRepository.findByRole(Role.ADMIN);
		if (adminAccout == null) {
			User user = new User();
			user.setEmail("uexcel@gmail.com");
			user.setRole(Role.ADMIN);
			user.setFirstName("Uexcel");
			user.setLastName("Excel");
			user.setPassword(new BCryptPasswordEncoder().encode("excel247"));
			userRepository.save(user);
		}
	}

}
