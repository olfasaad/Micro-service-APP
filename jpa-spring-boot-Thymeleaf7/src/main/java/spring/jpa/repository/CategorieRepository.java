package spring.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import spring.jpa.model.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
	
	
	
}
