package org.ms.clientservice.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;




import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;

@Entity 
@Data //des annotation de lombok 
@NoArgsConstructor 
@AllArgsConstructor 
@ToString
public class Client {
 @Id //cle primaire id 
 @GeneratedValue(strategy = GenerationType.IDENTITY) //pour dire que il s'agit cle primaire generate auto 
 //@Temporal(TemporalType.DATE)
 private Long id;
 private String name;
 private String email;
 //private java.util.Date dateins;
 private double salary ;
 

}
