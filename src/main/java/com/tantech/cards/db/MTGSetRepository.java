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

import com.tantech.cards.db.MTGSet;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MTGSetRepository extends CrudRepository<MTGSet, Integer> {
    
    public MTGSet findByName(String Name);
    
}
