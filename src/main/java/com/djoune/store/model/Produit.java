/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.djoune.store.model;
import jakarta.persistence.*;

/**
 *
 * @author StHilaireDjoune
 */
@Entity
@Table(name = "produits")
public class Produit {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private Double prix;
    private String categorie;
    private String size;
    private String couleur;
    private String imageNom;

    // 1. Constructor vid (OBLIGATWA pou JPA)
    public Produit() {
    }

    // 2. Getters ak Setters (OBLIGATWA pou Spring ka li done yo)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getCouleur() { return couleur; }
    public void setCouleur(String couleur) { this.couleur = couleur; }

    public String getImageNom() { return imageNom; }
    public void setImageNom(String imageNom) { this.imageNom = imageNom; }
}