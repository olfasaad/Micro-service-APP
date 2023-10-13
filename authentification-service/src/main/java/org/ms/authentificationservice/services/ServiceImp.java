package org.ms.authentificationservice.services;

import java.util.List;

import javax.transaction.Transactional;

import org.ms.authentificationservice.entities.Approle;
import org.ms.authentificationservice.entities.Appuser;
import org.ms.authentificationservice.repositories.AppRoleRepository;
import org.ms.authentificationservice.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@Transactional
public class ServiceImp implements UserService{
	 @Autowired
	    private AppUserRepository userRepository;

	    @Autowired
	    private AppRoleRepository roleRepository;
	   
	    @Autowired
	    private PasswordEncoder passwordEncoder; 
	    
	@Override
	public Appuser addUser(Appuser appUser) {
		// TODO Auto-generated method stub
		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		return userRepository.save(appUser);
	}

	@Override
	public Approle addRole(Approle appRole) {
		// TODO Auto-generated method stub
		return roleRepository.save(appRole) ;
		
		
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		// TODO Auto-generated method stub
		Appuser appuser =userRepository.findByUsername(username);
		Approle approle=roleRepository.findByRoleName(roleName);
		appuser.getRoles().add(approle) ;
	}

	@Override
	public Appuser getUserByName(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public List<Appuser> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
	/*@Override
	public List<Approle> getAllRoles() {
		// TODO Auto-generated method stub
		return AppRoleRepository.findByRoleName(roleName);
	}*/

}
