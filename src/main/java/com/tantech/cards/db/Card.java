/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.db;

/**
 * This file is part of mtgsdk.
 * https://github.com/MagicTheGathering/mtg-sdk-java
 * <p>
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT-license
 * <p>
 * Created by thechucklingatom on 2/16/2016.
 * <p>
 * Card class that is created from the JSON set representation.
 *
 * @author thechucklingatom
 * @author pfholden
 */

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

@Indexed
@Entity // This tells Hibernate to make a table out of this class

@Table( name    = "card",
        indexes = { @Index(name="cardid_idx", columnList = "cardId"),
                    @Index(name="lookup_idx", columnList = "cardId, setId")
   }
)
@JsonIgnoreProperties
@JsonRootName(value = "cards")
public class Card implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private Integer cardId;
    private String id;
    private String layout;
    
    @Fields ({@Field,
            @Field(name="sortName", analyze = Analyze.NO, store = Store.NO, index = org.hibernate.search.annotations.Index.NO)
            })
    @SortableField (forField = "sortName")
    private String name;
    
    @ElementCollection
    @CollectionTable(name = "other_names", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "other_names")
    private Set<String> names = new HashSet<>();
    
    @Field
    private String manaCost;
    private Integer cmc;
    
//  On simple collections, add @Field with a name. This name can then be used in the search query.
    @IndexedEmbedded
    @Field(name="colors")
    @ElementCollection
    @CollectionTable(name = "card_colors", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "color")
    private Set<String> colors = new HashSet<>();

//    @Field
    @IndexedEmbedded
    @ElementCollection
    @CollectionTable(name = "colorIdentities", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "colorIdentity")
    private Set<String> colorIdentity = new HashSet<>();
    
    @Field
    private String type;
    
//    @Field
    @IndexedEmbedded
    @ElementCollection
    @CollectionTable(name = "supertypes", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "supertype")
    private Set<String> supertypes = new HashSet<>();
    
//    @Field
    @IndexedEmbedded
    @ElementCollection
    @CollectionTable(name = "types", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "type")
    private Set<String> types = new HashSet<>();
    
//    @Field
    @IndexedEmbedded
    @ElementCollection
    @CollectionTable(name = "subtypes", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "subtype")
    private Set<String> subtypes = new HashSet<>();
    
    private String rarity;
    
    @Field
    private String text;
    private String originalText;
    private String flavor;
    private String artist;
    private String number;
    private Integer power;
    private Integer toughness;
    private String loyalty;
    private int multiverseid = -1;
//	private int[] variations;
    private String imageName;
    private String watermark;
    private String border;
    private boolean timeshifted;
    private int hand;
    private int life;
    private boolean reserved;
    private String releaseDate;
    private boolean starter;
    private String setShortName;
    
    @Field
    private String setName;

    
    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name="setId")
//    @JsonBackReference
    @JsonIdentityInfo(
      generator = ObjectIdGenerators.PropertyGenerator.class, 
      property = "setId")
    private MTGSet mtgSet;
    
//	private String[] printings;
    private String imageUrl;
//	private Legality[] legalities;
//	private BigDecimal priceHigh;
//	private BigDecimal priceMid;
//	private BigDecimal priceLow;
//	private BigDecimal onlinePriceHigh;
//	private BigDecimal onlinePriceMid;
//	private BigDecimal onlinePriceLow;
//	private Ruling[] rulings;

    public Integer getCardId() {
            return cardId;
    }

    public void setCardId(Integer cardId) {
            this.cardId = cardId;
    }

    public String getId() {
            return id;
    }

    public void setId(String id) {
            this.id = id;
    }

    public String getLayout() {
            return layout;
    }

    public void setLayout(String layout) {
            this.layout = layout;
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public Set<String> getNames() {
            return names;
    }

    public void setNames(Set<String> names) {
            this.names = names;
    }

    public String getManaCost() {
            return manaCost;
    }

    public void setManaCost(String manaCost) {
            this.manaCost = manaCost;
    }

    public Integer getCmc() {
            return cmc;
    }

    public void setCmc(Integer cmc) {
            this.cmc = cmc;
    }
    
    public Set<String> getColors() {
        return colors;
    }

    public void setColors(Set<String> colors) {
        this.colors = colors;
    }


    public Set<String> getColorIdentity() {
            return colorIdentity;
    }

    public void setColorIdentity(Set<String> colorIdentity) {
            this.colorIdentity = colorIdentity;
    }

    public String getType() {
            return type;
    }

    public void setType(String type) {
            this.type = type;
    }

    public Set<String> getSupertypes() {
            return supertypes;
    }

    public void setSupertypes(Set<String> supertypes) {
            this.supertypes = supertypes;
    }

    public Set<String> getTypes() {
            return types;
    }

    public void setTypes(Set<String> types) {
            this.types = types;
    }

    public Set<String> getSubtypes() {
            return subtypes;
    }

    public void setSubtypes(Set<String> subtypes) {
            this.subtypes = subtypes;
    }

    public String getRarity() {
            return rarity;
    }

    public void setRarity(String rarity) {
            this.rarity = rarity;
    }

    public String getText() {
            return text;
    }

    public void setText(String text) {
            this.text = text;
}

    public String getFlavor() {
            return flavor;
    }

    public void setFlavor(String flavor) {
            this.flavor = flavor;
    }

    public String getArtist() {
            return artist;
    }

    public void setArtist(String artist) {
            this.artist = artist;
    }

    public String getNumber() {
            return number;
    }

    public void setNumber(String number) {
            this.number = number;
    }

    public Integer getPower() {
            return power;
    }

    public void setPower(Integer power) {
            this.power = power;
    }

    public Integer getToughness() {
            return toughness;
    }

    public void setToughness(Integer toughness) {
            this.toughness = toughness;
    }

    public String getLoyalty() {
            return loyalty;
    }

    public void setLoyalty(String loyalty) {
            this.loyalty = loyalty;
    }

    public int getMultiverseid() {
            return multiverseid;
    }

    public void setMultiverseid(int multiverseid) {
            this.multiverseid = multiverseid;
    }

//	public int[] getVariations() {
//		return variations;
//	}
//
//	public void setVariations(int[] variations) {
//		this.variations = variations;
//	}

    public String getImageName() {
            return imageName;
    }

    public void setImageName(String imageName) {
            this.imageName = imageName;
    }

    public String getWatermark() {
            return watermark;
    }

    public void setWatermark(String watermark) {
            this.watermark = watermark;
    }

    public String getBorder() {
            return border;
    }

    public void setBorder(String border) {
            this.border = border;
    }

    public boolean isTimeshifted() {
            return timeshifted;
    }

    public void setTimeshifted(boolean timeshifted) {
            this.timeshifted = timeshifted;
    }

    public int getHand() {
            return hand;
    }

    public void setHand(int hand) {
            this.hand = hand;
    }

    public int getLife() {
            return life;
    }

    public void setLife(int life) {
            this.life = life;
    }

    public boolean getReserved() {
            return reserved;
    }

    public void setReserved(boolean reserved) {
            this.reserved = reserved;
    }

    public String getReleaseDate() {
            return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
    }

    public boolean getStarter() {
            return starter;
    }

    public void setStarter(boolean starter) {
            this.starter = starter;
    }
//
//	/**
//	 * dirty compare to in order to start testing. Just comparing the
//	 * MultiverseId which should be unique.
//	 *
//	 * @param toCompare A {@link Card} object hopefully
//	 * @return true if the same setShortName, false if different.
//	 */
//	@Override
//	public boolean equals(Object toCompare) {
//		if (toCompare instanceof Card) {
//			Card cardCompare = (Card) toCompare;
//			return getMultiverseid() == cardCompare.getMultiverseid()
//					&& getName().equals(cardCompare.getName())
//					&& getCmc() == cardCompare.getCmc();
//		} else {
//			return false;
//		}
//	}
//
	/**
	 * Prints the Card name and multiverseId which should give enough info for
	 * debug testing.
	 *
	 * @return The cards name and Id
	 */
	@Override
	public String toString() {
		return "\nCard Name: " + getName() +
				"\nMultiverse Id: " + getMultiverseid() +
				"\nMana Cost: " + getManaCost();
	}

    public String getSet() {
            return setShortName;
    }

    public void setSet(String set) {
            this.setShortName = set;
    }
    
    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public MTGSet getMtgSet() {
            return mtgSet;
    }

    public void setMtgSet(MTGSet mtgSet) {
            this.mtgSet = mtgSet;
    }

//	public String[] getPrintings() {
//		return printings;
//	}
//
//	public void setPrintings(String[] printings) {
//		this.printings = printings;
//	}
//
    public String getOriginalText() {
            return originalText;
    }

    public void setOriginalText(String originalText) {
            this.originalText = originalText;
    }

    public String getImageUrl() {
            return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
    }
//
//	public Legality[] getLegalities() {
//		return legalities;
//	}
//
//	public void setLegalities(Legality[] legalities) {
//		this.legalities = legalities;
//	}
//
//	public BigDecimal getPriceHigh() {
//		return priceHigh;
//	}
//
//	public void setPriceHigh(BigDecimal priceHigh) {
//		this.priceHigh = priceHigh;
//	}
//
//	public BigDecimal getPriceMid() {
//		return priceMid;
//	}
//
//	public void setPriceMid(BigDecimal priceMid) {
//		this.priceMid = priceMid;
//	}
//
//	public BigDecimal getPriceLow() {
//		return priceLow;
//	}
//
//	public void setPriceLow(BigDecimal priceLow) {
//		this.priceLow = priceLow;
//	}
//
//	public BigDecimal getOnlinePriceHigh() {
//		return onlinePriceHigh;
//	}
//
//	public void setOnlinePriceHigh(BigDecimal onlinePriceHigh) {
//		this.onlinePriceHigh = onlinePriceHigh;
//	}
//
//	public BigDecimal getOnlinePriceMid() {
//		return onlinePriceMid;
//	}
//
//	public void setOnlinePriceMid(BigDecimal onlinePriceMid) {
//		this.onlinePriceMid = onlinePriceMid;
//	}
//
//	public BigDecimal getOnlinePriceLow() {
//		return onlinePriceLow;
//	}
//
//	public void setOnlinePriceLow(BigDecimal onlinePriceLow) {
//		this.onlinePriceLow = onlinePriceLow;
//	}

//  public Ruling[] getRulings() {
//    return rulings;
//  }
//
//  public void setRulings(Ruling[] rulings) {
//    this.rulings = rulings;
//  }
}