package org.ms.authentificationservice.services;
import org.ms.authentificationservice.entities.Approle;
import org.ms.authentificationservice.entities.Appuser;
import java.util.List;
public interface UserService {
  Appuser addUser (Appuser appUser) ;
 Approle addRole (Approle appRole);
 
 void addRoleToUser(String username, String roleName);
 
 
 Appuser getUserByName(String username);
 List<Appuser> getAllUsers();
 
 /*
 Approle getUserByRole(String rolename);
 List<Approle> getAllRoles();*/
}