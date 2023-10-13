package org.ms.authentificationservice.filtres;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
 private AuthenticationManager authenticationManager;
 // injecter par constructeur un gestionnaire d'authentication
 public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
 this.authenticationManager = authenticationManager;
 }
 @Override
 //appelée suite à l'appel du path "/login"
 public Authentication attemptAuthentication(HttpServletRequest request, 
HttpServletResponse response) throws AuthenticationException {
 System.out.println("attemptAuthentication");
 //récupérer les paramètres "username" et "password" du path "/login"
 String username= request.getParameter("username");
 String password =request.getParameter("password");
 //Construire un objet "Authentification"
 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
 new UsernamePasswordAuthenticationToken(username, password);
 //lancer la procédure d'authentification 
 // (appeler la méthode "loadUserByUsername" de "UserDetailsService"
 return 
authenticationManager.authenticate(usernamePasswordAuthenticationToken);
 }
 //appelée en cas de l'existence de l'utilisateur dans la BD pour créer le JWT
 @Override
 protected void successfulAuthentication(HttpServletRequest request, 
HttpServletResponse response, FilterChain chain, Authentication authResult) throws 
IOException, ServletException {
 System.out.println("successfulAuthentication");
 
 //retourner l'utilisateur authentifié
 User user= (User)authResult.getPrincipal(); 
 
 //Choisir un algorithme de cryptage
 Algorithm algo = Algorithm.HMAC256("MaClé");
 //Construire le JWT
 String jwtAccessToken = JWT.create()
 .withSubject(user.getUsername()) // stocker le nom de l'utilisateur
 .withExpiresAt(new Date(System.currentTimeMillis()+5*60*2000)) 
 // date d'expiration après 5 minutes
.withIssuer(request.getRequestURL().toString()) 
 //url de la reuête d'origine
 //placer la liste des rôles associés à l'utilisateur courant
.withClaim("roles", user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList()))
 .sign(algo); //signer le JWT avec l'algorithme choisi
 //Envoyer le JWT dans l'entête de la réponse
 response.setHeader("Authorization",jwtAccessToken);
//Construire le refresh JWT
String jwtRefreshToken = JWT.create()
//stocker le nom de l'utilisateur
.withSubject(user.getUsername())
//date d'expiration après 1 heure 
.withExpiresAt(new Date(System.currentTimeMillis()+60*60*1000))
//url de la reuête d'origine
.withIssuer(request.getRequestURL().toString()) 
//signer le refresh JWT avec l'algorithme choisi
.sign(algo); 
//stocker les deux tokens dans un objet HashMap
Map<String,String> mapTokens = new HashMap<>();
mapTokens.put("access-token",jwtAccessToken);
mapTokens.put("refresh-token",jwtRefreshToken);
//Spécifier le format du contenu de la réponse
response.setContentType("application/json");
//place l'objet HashMap dans le corps de la réponse
new ObjectMapper().writeValue(response.getOutputStream(),mapTokens);

 
 }
}