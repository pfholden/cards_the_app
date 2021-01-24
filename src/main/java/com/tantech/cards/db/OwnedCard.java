/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.db;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author pfholden
 */
@Indexed
@Entity // This tells Hibernate to make a table out of this class
public class OwnedCard {   
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private Integer ownedId;
    
    @IndexedEmbedded
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="cardId")
    private Card cardMaster;
    
    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name="setId")
    private MTGSet setName;
    
    @Field
    private String cardLocation;
    private boolean foil;
    
    @Field
    private String cardCondition;
    
    @Field
    private Integer cardStars;
    
    @Field
    private String notes;
    
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date dateAdded = new Date();
    
    private Float pricePaid;

    public Float getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(Float pricePaid) {
        this.pricePaid = pricePaid;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getOwnedId() {
        return ownedId;
    }

    public void setOwnedId(Integer id) {
        this.ownedId = id;
    }

    public Card getCardMaster() {
        return cardMaster;
    }

    public void setCardMaster(Card card) {
        this.cardMaster = card;
    }

    public MTGSet getSetName() {
        return setName;
    }

    public void setSetName(MTGSet setName) {
        this.setName = setName;
    }
    
    

    public String getCardLocation() {
        return cardLocation;
    }

    public void setCardLocation(String cardLocation) {
        this.cardLocation = cardLocation;
    }

    public boolean isFoil() {
        return foil;
    }

    public void setFoil(boolean foil) {
        this.foil = foil;
    }

    public String getCardCondition() {
        return cardCondition;
    }

    public void setCardCondition(String cardCondition) {
        this.cardCondition = cardCondition;
    }

    public Integer getCardStars() {
        return cardStars;
    }

    public void setCardStars(Integer cardStars) {
        this.cardStars = cardStars;
    }
       
}
