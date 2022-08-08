package com.blogging;

import com.blogging.entities.Role;
import com.blogging.repositories.RoleRepo;
import com.blogging.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();
	}

	@Override
	public void run(String... args) {
//		System.out.println(this.passwordEncoder.encode("asd123"));

		try {
			Role role1 = new Role();
			role1.setId(AppConstants.ROLE_ADMIN);
			role1.setName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setId(AppConstants.ROLE_USER);
			role2.setName("ROLE_USER");

			List<Role> roles = List.of(role1, role2);
			List<Role> result = this.roleRepo.saveAll(roles);

			result.forEach(role -> System.out.println(role.getName()));

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
