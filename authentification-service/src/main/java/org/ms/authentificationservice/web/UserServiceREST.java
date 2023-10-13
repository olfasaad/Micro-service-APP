package org.ms.authentificationservice.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.ms.authentificationservice.entities.Appuser;
import org.ms.authentificationservice.entities.Approle;
import org.ms.authentificationservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
@RestController
public class UserServiceREST {
	public final String PREFIXE_JWT="Bearer";//jwt.io
	public final String CLE_SIGNATURE = "Maclé";
	@Autowired	
     private UserService userServcie;
	@GetMapping(path="/users")
	public List<Appuser>listAllUsers() {
		return userServcie.getAllUsers();
	}
	@GetMapping(path="/users1")
	public Approle addUser(@RequestBody Approle approle) {
		return userServcie.addRole(approle);
	}
	
	@PostMapping(path="/users")
	public Appuser addUser(@RequestBody Appuser appUser) {
		return userServcie.addUser(appUser);
	}
	@PostMapping(path="/role")
	public Approle  addRole(@RequestBody Approle approle) {
		return userServcie.addRole(approle);
	}
	
	
	@PostMapping(path="/addRoleToUser")
	public void addRoleToUser (@RequestBody UserRoleData userRoleData)
	{
		userServcie.addRoleToUser(userRoleData.getUsername(), userRoleData.getRoleName());
	}
	@GetMapping(path="/refreshToken")
	public void refreshToken (HttpServletRequest request , HttpServletResponse 
	response)
	{
	 // récupérer le header "Authorization" (refresh-token)
	 String refreshToken = request.getHeader("Authorization");
	 // vérifier l'état du header
	 if (refreshToken!=null && refreshToken.startsWith(PREFIXE_JWT))
	 {
	 try {
	 //récupérer la valeur du refresh-token
	 String jwtRefresh = refreshToken.substring(PREFIXE_JWT.length());
	 //Préparer une instance du même algorithme de cryptage (HMAC256)
	 Algorithm algo = Algorithm.HMAC256(CLE_SIGNATURE);
	 // vérifier la validité du JWT par la vérification de sa signature
	 JWTVerifier jwtVerifier = JWT.require(algo).build();
	 //décoder le refresh-JWT
	 DecodedJWT decodedJWT =jwtVerifier.verify(jwtRefresh);
	 //récupérer la valeur de "username"
	 String username = decodedJWT.getSubject();

	//Recharger l'utilisateur à partir de la BD
	 Appuser user = userServcie.getUserByName(username);
	 //Construire le access JWT
	 String jwtAccessToken = JWT.create()
	 // stocker le nom de l'utilisateur
	 .withSubject(user.getUsername())
	 // date d'expiration après 1 minute
	.withExpiresAt(new Date(System.currentTimeMillis()+1*60*1000))
	 //url de la reuête d'origine
	.withIssuer(request.getRequestURL().toString())
	//placer la liste des rôles associés à l'utilisateur courant
	.withClaim("roles", user.getRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()))
	 //signer le access JWT avec l'algorithme choisi
	 .sign(algo);
	 //stocker les deux tokens dans un objet HashMap
	 Map<String,String> mapTokens = new HashMap<>();
	 mapTokens.put("access-token",jwtAccessToken);
	 mapTokens.put("refresh-token",jwtRefresh);
	 //Spécifier le format du contenu de la réponse
	 response.setContentType("application/json");
	 //place l'objet HashMap dans le corps de la réponse
	 new ObjectMapper().writeValue(response.getOutputStream(),mapTokens);
	 } catch (Exception e) {
	 new RuntimeException(e);
	 }
	 }
	 else
	 {
	 new RuntimeException("Refresh Token non disponible..");
	 }
	}
}
// objet pour regrouper (Role ,user )/envoyer les donnes en objet  
@Data
class UserRoleData{
	private String username;
	private String roleName;
}