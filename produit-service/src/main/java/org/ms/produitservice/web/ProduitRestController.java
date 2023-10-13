package org.ms.produitservice.web;
import java.util.List;

import org.ms.produitservice.entities.Produit;
import org.ms.produitservice.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Hashtable;
import java.util.Map;
import java.util.Hashtable;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
@RefreshScope
@RestController
public class ProduitRestController {
	 /*@Autowired
	 private ProduitRepository produitRepository;
	  @GetMapping
	    public List <Produit> getProducts() {
	        return produitRepository.findAll();
	    }
	@GetMapping("/config")
	 public Map<String,Object> config () {
	 Map<String,Object> params = new Hashtable<>();
	 params.put("threadName", Thread.currentThread().toString());
	 return params;
	 }*/
	@Value("${globalParam}")
	 private int globalParam;
	 @Value("${monParam}")
	 private int monParam;
	 @Value("${email}")
	 private String email;
	 
	 @GetMapping("config")
	 public Map<String,Object> config () {
	 Map<String,Object> params = new Hashtable<>();
	 params.put("globalParam", globalParam);
	 params.put("monParam", monParam);
	 params.put("email", email);
	 params.put("threadName", Thread.currentThread().toString());
	 return params;}
}