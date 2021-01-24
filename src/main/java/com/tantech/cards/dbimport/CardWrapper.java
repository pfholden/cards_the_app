/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.dbimport;

import com.tantech.cards.db.*;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 *
 * @author pfholden
 */

@JsonRootName(value = "cards")
public class CardWrapper {
    
    private Card cards[];

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public void addToDB (MTGSetRepository setRepo, CardRepository cardRepo){
        Integer i=1;
    
         for (Card importCard:cards){
//            System.out.println(i++);

//            if (!importCard.getPower().isEmpty()) importCard.setPowerInt(Integer.parseInt(importCard.getPower()));
//            if (!importCard.getToughness().isEmpty()) importCard.setToughnessInt(Integer.parseInt(importCard.getToughness()));
            
            MTGSet setRead = setRepo.findByName(importCard.getSetName());

            if ( setRead == null){
                setRead = new MTGSet();
                setRead.setName(importCard.getSetName());
                setRead.setCode(importCard.getSetCode());
                setRepo.save(setRead);
                importCard.setMtgSet(setRead);
                cardRepo.save(importCard);
            } else if (cardRepo.findByNameAndMtgSet(importCard.getName(), setRead) == null){
                importCard.setMtgSet(setRead);
                cardRepo.save(importCard);  
            }   
        }
    }
}
