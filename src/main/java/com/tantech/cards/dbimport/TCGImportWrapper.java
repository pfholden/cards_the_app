/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.dbimport;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvNumber;

/**
 *
 * @author pfholden
 */
public class TCGImportWrapper {
 
    @CsvBindByName(column = "Name")
    private String name;
    
    @CsvBindByName(column = "Simple Name")
    private String simpleName;
    
    @CsvBindByName(column = "Quantity")
    private Integer quantity;
    
    @CsvBindByName(column = "Set")
    private String setName;
    
    @CsvBindByName(column = "Set Code")
    private String setCode;
    
    @CsvBindByName(column = "Card Number")
    private String cardNumber;
    
    @CsvBindByName(column = "Condition")
    private String cardCondition;
    
    @CsvBindByName(column = "Printing")
    private String printing;
    
    @CsvBindByName(column = "Price Each")
    @CsvNumber("$#.##")
    private Float pricePaid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }
       
    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCondition() {
        return cardCondition;
    }

    public void setCardCondition(String cardCondition) {
        this.cardCondition = cardCondition;
    }

    public String getPrinting() {
        return printing;
    }

    public void setPrinting(String printing) {
        this.printing = printing;
    }

    public Float getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(Float pricePaid) {
        this.pricePaid = pricePaid;
    }    
}
