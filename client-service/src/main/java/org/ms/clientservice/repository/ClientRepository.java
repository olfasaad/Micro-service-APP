package org.ms.clientservice.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.ms.clientservice.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ClientRepository extends JpaRepository<Client, Long> { //JpaRepository peret de communiquer avec bd 
	//@RestResource (path="/byEmail") 
	//Page<Client>  findByNameContains(@Param("mc") String email , Pageable pageable);// methode pour personnaliser recherche 
	
	 //@RestResource (path="/byName")http://localhost:8081/clients/search/byName?mc=Ali
	//Page<Client> findByNameContains(@Param("mc") String name , Pageable pageable);
	//Page <client> findAllByDateins After(java.util.Date d1,pageable pageable);
    @PersistenceContext
	@RestResource (path="/bySalary")
     Page<Client>  findBySalaryGreaterThan(@Param("mc") double salary , Pageable pageable);
   // @RestResource (path="/byGmail")
    //http://localhost:8081/clients/search/byGmail
   // @Query(value="select c from Client c where c.email Like '%gmail%'" )
   // @Query(value="select c from Client c where c.email Like :x" )
    
    //public Page<Client> listeClientGmail(Pageable p);
	//public List<Client> listeClientGmail(Pageable p);
  //  public Page<Client> listeClientGmail(Pageable p,@Param("x")String m);
 //   @RestResource (path="/byGmail")
   //public Page <Client>findByEmailEndingWith( String m ,Pageable p);
	
}
