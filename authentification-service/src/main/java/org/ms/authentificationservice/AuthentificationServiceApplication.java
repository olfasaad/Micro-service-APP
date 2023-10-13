package org.ms.authentificationservice;


import org.ms.authentificationservice.entities.Approle;
import org.ms.authentificationservice.entities.Appuser;
import org.ms.authentificationservice.services.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthentificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthentificationServiceApplication.class, args);
		
	}
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	@Bean
	 CommandLineRunner start(UserService  userservice, 
	RepositoryRestConfiguration 
	repositoryRestConfiguration)
	 {
	 
	repositoryRestConfiguration.exposeIdsFor(Appuser.class);
	
	 return args ->
	 {

		
	userservice.addUser(new Appuser(null,"user1","123",null));
	userservice.addUser(new Appuser(null,"user2","145",null));
	userservice.addRole(new Approle(null,"user"));
	userservice.addRole(new Approle(null,"Admin"));
	userservice.addRoleToUser("user1","user");
	userservice.addRoleToUser("user2","admin");
	userservice.addRoleToUser("user2","user,admin");
	 
	 };
	 
	 }
}