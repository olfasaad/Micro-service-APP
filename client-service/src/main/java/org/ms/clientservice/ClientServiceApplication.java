package org.ms.clientservice;
import java.sql.Date;
//import java.text.SimpleDateFormat;

import org.ms.clientservice.entities.Client;
import org.ms.clientservice.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
 @SpringBootApplication
 public class ClientServiceApplication {
 public static void main(String[] args) {
 SpringApplication.run(ClientServiceApplication.class, args);
 }
 @Bean
 CommandLineRunner start(ClientRepository clientRepository, 
RepositoryRestConfiguration 
repositoryRestConfiguration)
 {
 
repositoryRestConfiguration.exposeIdsFor(Client.class);
 return args ->
 {
    //SimpleDateFormat sdf=newSimpleDateFormat("dd-MM-YYYY");
	//Date di=sdf.parse("28-02-2023");
//	Date dii=sdf.parse("28-02-2023");
	//Date di1=sdf.parse("28-02-2023");
			
 //Insérer trois clients de test dans la BD
 clientRepository.save(new Client(null,"Ali","ali.ms@gmail.com",2.0));
 clientRepository.save(new Client(null,"Mariem","Mariem.ms@gmail.com",2.2));
 clientRepository.save(new Client(null,"Mohamed","Mohamed.ms@gmail.com",2.2));
 clientRepository.save(new Client(null,"Mohamed","Mohamed.ms@mail.com",2.2));


 //Afficher les clients existants dans la BD
 for (Client client : clientRepository.findAll())
 {
 System.out.println(client.toString());
 }
 };
 }


 }