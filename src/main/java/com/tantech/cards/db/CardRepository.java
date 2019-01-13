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
import org.springframework.data.repository.CrudRepository;

import java.util.Set;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
//@Repository
//@Transactional
public interface CardRepository extends CrudRepository<Card, Integer> {
    
    public Set<Card> findByName(String name);
    
    public Card findByNameAndMtgSet(String name, MTGSet mtgSet);
    
    public Card findByNameAndSetName(String name, String setName);
    
    public Card findByNameAndSetCode(String name, String setCode);
    
    public Card findBySetCodeAndNumber(String setCode, String cardNumber);
    
    @Query("select distinct u.name from Card u ")
    List<String> findByAsArrayAndSort(Sort sort);
}
