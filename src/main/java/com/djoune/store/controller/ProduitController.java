/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.djoune.store.controller;
import com.djoune.store.model.Produit;
import com.djoune.store.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author StHilaireDjoune
 */
@Controller
public class ProduitController {
    
    @Autowired
    private ProduitRepository produitRepository;

    // Afiche tout pwodwi yo sou paj dakey la
    @GetMapping("/")
    public String listeProduits(Model model) {
        List<Produit> produits = produitRepository.findAll();
        model.addAttribute("produits", produits);
        return "index";
    }

    // Afiche fòm pou ajoute yon nouvo pwodwi
    @GetMapping("/produits/nouveau")
    public String montreFormulaire(Model model) {
        model.addAttribute("produit", new Produit());
        return "produit";
    }

    // Sove pwodwi a apre itilizatè a fin klike sou bouton an
    @PostMapping("/produits/save")
    public String enregistrerProduit(@ModelAttribute("produit") Produit produit) {
        produitRepository.save(produit);
        return "redirect:/"; // Sa ap voye w tounen sou paj dakey la
    }
}

