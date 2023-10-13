package spring.jpa.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Produit {
	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 50)
    @NotNull  // interdire une valeur null
  	@Size(min=3, max=50)//spécifier la taille acceptée
	private String designation;

	@DecimalMin("0.1")  // pour spécifier une valeur minimale pour le prix
	private double prix;
	private int quantite;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat (pattern = "yyyy-MM-dd")
	java.util.Date dateAchat;

	@ManyToOne
	private Categorie categorie;
	
	private boolean actif;
	
	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public Produit(String designation, double prix, int quantite, Date dateAchat, Categorie categorie) {
		super();
		this.designation = designation;
		this.prix = prix;
		this.quantite = quantite;
		this.dateAchat = dateAchat;
		this.categorie = categorie;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public java.util.Date getDateAchat() {
		return dateAchat;
	}

	public void setDateAchat(java.util.Date dateAchat) {
		this.dateAchat = dateAchat;
	}

	public Produit(String designation, double prix, int quantite, Date dateAchat) {
		super();
		this.designation = designation;
		this.prix = prix;
		this.quantite = quantite;
		this.dateAchat = dateAchat;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Produit() {
		super();

	}

	public Produit(String designation, double prix, int quantite) {
		super();
		this.designation = designation;
		this.prix = prix;
		this.quantite = quantite;
	}

	@Override
	public String toString() {
		return "Produit [id=" + id + ", designation=" + designation + ", prix=" + prix + ", quantite=" + quantite
				+ ", dateAchat=" + dateAchat + ", categorie=" + categorie + "]";
	}

	public Produit(Long id, String designation, double prix, int quantite) {
		super();
		this.id = id;
		this.designation = designation;
		this.prix = prix;
		this.quantite = quantite;
	}
}
