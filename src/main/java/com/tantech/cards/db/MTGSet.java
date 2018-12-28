/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.db;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author pfholden
 */

@Entity // This tells Hibernate to make a table out of this class
public class MTGSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer setId;
    private String name;
    private String code;
    private String gathererCode;
    private String oldCode;
    private String magicCardsInfoCode;
    private String releaseDate;
    private String border;
    private String type;
    private String block;
    private boolean onlineOnly;
//    private List<Object> booster;
//    private List<Card> cards;
    
    // mappedBy needs to be set to the field name from the other side of the relationship.
    @OneToMany(mappedBy="setName", cascade = CascadeType.ALL)
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class, 
        property = "cardId")
    private Set<Card> setCards;

    public Set<Card> getSetCards() {
        return setCards;
    }

    public void setSetCards(Set<Card> setCards) {
        this.setCards = setCards;
    }
    
    public Integer getSetId() {
            return setId;
    }

    public void setSetId(Integer setId) {
            this.setId = setId;
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public String getCode() {
            return code;
    }

    public void setCode(String code) {
            this.code = code;
    }

    public String getGatherercode() {
            return gathererCode;
    }

    public void setGatherercode(String gatherercode) {
            this.gathererCode = gatherercode;
    }

    public String getOldCode() {
            return oldCode;
    }

    public void setOldCode(String oldCode) {
            this.oldCode = oldCode;
    }

    public String getMagicCardsInfoCode() {
            return magicCardsInfoCode;
    }

    public void setMagicCardsInfoCode(String magicCardsInfoCode) {
            this.magicCardsInfoCode = magicCardsInfoCode;
    }

    public String getReleaseDate() {
            return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
    }

    public String getBorder() {
            return border;
    }

    public void setBorder(String border) {
            this.border = border;
    }

    public String getType() {
            return type;
    }

    public void setType(String type) {
            this.type = type;
    }

    public String getBlock() {
            return block;
    }

    public void setBlock(String block) {
            this.block = block;
    }

    public boolean getOnlineOnly() {
            return onlineOnly;
    }

    public void setOnlineOnly(boolean onlineOnly) {
            this.onlineOnly = onlineOnly;
    }

//    public List<Object> getBooster() {
//            return booster;
//    }
//
//    public void setBooster(List<Object> booster) {
//            this.booster = booster;
//    }
//
//    public List<Card> getCards() {
//            return cards;
//    }
//
//    public void setCards(List<Card> cards) {
//            this.cards = cards;
//    }

    /**
     * dirty compare to in order to start testing. Just comparing the setGathererCode
     * which should be unique. May change to just the code.
     * @param toCompare A {@link MtgSet} object hopefully
     * @return true if the same set, false if different.
     */
//	@Override
//	public boolean equals(Object toCompare){
//		if(toCompare instanceof MtgSet){
//			MtgSet cardCompare = (MtgSet)toCompare;
//			return getGatherercode().equalsIgnoreCase(cardCompare.getGatherercode());
//		}else{
//			return false;
//		}
//	}    
}
