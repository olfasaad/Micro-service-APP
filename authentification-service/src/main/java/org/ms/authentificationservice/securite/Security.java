package org.ms.authentificationservice.securite;
import java.util.ArrayList;
import java.util.Collection;

import org.ms.authentificationservice.entities.Appuser;
import org.ms.authentificationservice.filtres.JwtAuthenticationFilter;
import org.ms.authentificationservice.filtres.JwtAuthorizationFilter;
import org.ms.authentificationservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class Security extends WebSecurityConfigurerAdapter{
	@Autowired 
	private UserService userService;
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception 
	{
	 return super.authenticationManagerBean();
	}

 @Override
 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	// utiliser les données de la BD
	 auth.userDetailsService(new UserDetailsService() {
	
 @Override
//Cette méthode est appeléé suite à la validation du formulaire d'authentification
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
// récupérer la valeur de "username" pour récupérer un objet AppUser de la BD
Appuser appUser = userService.getUserByName(username);

//construire une collection des rôles (permissions) selon le format de SpringSecurity
Collection<GrantedAuthority> permissions = new ArrayList<>();
//parcourir la liste des rôles de l'utilisateur pour remplir la collection des permissions
appUser.getRoles().forEach(r->{
permissions.add(new SimpleGrantedAuthority(r.getRoleName()));
});
//retourner un objet "User" de Spring Framework qui contient: "username", "password" et les permissions
return new User(appUser.getUsername(), appUser.getPassword(),permissions);
}
});
}

 //Définir les règles d'accès
 protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable();
 http.headers().frameOptions().disable();
	 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
// http.authorizeRequests().anyRequest().permitAll(); 
	 http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
	 http.authorizeRequests().antMatchers(HttpMethod.POST,
			 "/users/**").hasAuthority("ADMIN");
			  http.authorizeRequests().antMatchers(HttpMethod.GET, 
			 "/users/**").hasAuthority("USER");
			  http.authorizeRequests().antMatchers("/refreshToken/**").permitAll();

	// http.formLogin();
//obliger l’authentification
 http.authorizeRequests().anyRequest().authenticated();
 //Ajouter le filtre
 http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
 http.addFilterBefore(new JwtAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);

 }
}