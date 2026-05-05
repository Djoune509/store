/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.djoune.store.controller;

import com.djoune.store.model.Produit;
import com.djoune.store.repository.ProduitRepository; // Sipoze ou gen yon Repository
import com.djoune.store.service.MonCashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author StHilaireDjoune
 */
@Controller
public class PaiementController {
@Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private MonCashService moncashService;

    // 1. Afiche paj checkout la
    @GetMapping("/paiement/checkout/{id}")
    public String afficherCheckout(@PathVariable Long id, Model model) {
        Produit produit = produitRepository.findById(id).orElse(null);
        if (produit == null) {
            return "redirect:/";
        }
        model.addAttribute("produit", produit);
        return "checkout";
    }

    // 2. Traite enfòmasyon ki soti nan fòm nan
    @PostMapping("/paiement/traiter")
    public String traiterPaiement(
            @RequestParam("produitId") Long produitId,
            @RequestParam("email") String email,
            @RequestParam("adresse") String adresse,
            @RequestParam("methode") String methode,
            @RequestParam(value = "numeroClient", required = false) String numeroClient,
            @RequestParam(value = "referenceVirement", required = false) String referenceVirement) {

        System.out.println(">>> DEBUT TRAITEMENT PAIEMENT <<<");
        System.out.println("Kliyan: " + email);
        System.out.println("Methode: " + methode);

        // Debug pou wè sa kliyan an tape
        if (numeroClient != null && !numeroClient.isEmpty()) {
            System.out.println("Nimewo MonCash Kliyan: " + numeroClient);
        }
        if (referenceVirement != null && !referenceVirement.isEmpty()) {
            System.out.println("Referans Virement Kliyan: " + referenceVirement);
        }

        Produit produit = produitRepository.findById(produitId).orElse(null);
        
        if (produit == null) {
            System.out.println(">>> ERROR: Produit non trouvé!");
            return "redirect:/";
        }

        // KA 1: Peman otomatik via API MonCash (si w ap itilize API a toujou)
        if ("moncash".equals(methode)) {
            // Si w vle redireksyon otomatik, kite l konsa. 
            // Si se manyèl nèt ou vle, jis voye l sou /success.
            String urlRedirection = moncashService.genererLienPaiement(produitId, produit.getPrix());
            
            if (urlRedirection != null) {
                return "redirect:" + urlRedirection;
            } else {
                // Si API a gen pwoblèm, nou voye l nan success kòm peman manyèl
                return "redirect:/paiement/success";
            }
        } 

        // KA 2: Virement Bancaire oswa Livraison
        if ("virement".equals(methode) || "livraison".equals(methode)) {
            return "redirect:/paiement/success";
        }

        return "redirect:/paiement/success";
    }

 @GetMapping("/paiement/success")
public String success(Model model) {
    String transactionId = "DS-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    model.addAttribute("transId", transactionId);
    return "success";
  }
}