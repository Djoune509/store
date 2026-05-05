/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.djoune.store.repository;
import com.djoune.store.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author StHilaireDjoune
 */@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    // Spring Boot ap jere tout metòd tankou save(), findAll(), delete() pou nou
}

