/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.db;

/**
 *
 * @author pfholden
 */
import java.util.Collection;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
//@Repository
//@Transactional
public interface CardRepository extends CrudRepository<Card, Integer> {
    
    public Set<Card> findByName(String name);
    
    public Card findByNameAndMtgSet(String name, MTGSet mtgSet);
    
    public List<Card> findByNameContaining(String name);
    
    public List<Card> findByNameContainingAndTextContaining(String name, String text);
    
    public List<Card> findByNameContainingAndTextContainingAndTypeContaining(String name, String text, String type);
    
    public List<Card> findByNameContainingAndTextContainingAndTypeContainingAndPowerBetweenAndToughnessBetweenAndCmcBetweenAndSetNameIn(
            String name, String text, String type, Integer powerMin, Integer powerMax, Integer toughnessMin, 
            Integer toughnessMax, Integer cmcMin, Integer cmcMax, Collection<String> setName);
    
    
    

}
