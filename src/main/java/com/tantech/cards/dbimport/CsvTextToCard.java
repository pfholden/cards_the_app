/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.dbimport;

import com.tantech.cards.db.Card;
import org.springframework.beans.factory.support.AbstractBeanFactory;

/**
 *
 * @author pfholden
 */
public class CsvTextToCard  {
//    @Override
    public Object convertToRead(String value) {
        System.out.println("Value passed: "+value);
        Card c = new Card();
        c.setName(value);
        return c;
    }

//    @Override
    public String convertToWrite(Object value) {
        Card c = (Card) value;
        return String.format("%s", c.getName());
    }
}
