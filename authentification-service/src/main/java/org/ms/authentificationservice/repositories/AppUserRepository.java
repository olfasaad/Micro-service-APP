package org.ms.authentificationservice.repositories;

import java.awt.print.Pageable;

import org.ms.authentificationservice.entities.Appuser;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Repository;
@Repository
public interface AppUserRepository  extends JpaRepository<Appuser, Long>  {

	
	//  findByUsername(String username);

	Appuser findByUsername(String username);


	
}
