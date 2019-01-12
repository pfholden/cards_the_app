/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.ui;

import com.tantech.cards.db.Card;
import com.tantech.cards.search.CardSearchService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Alert;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentKeyListener;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Keyboard;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Span;
import org.apache.pivot.wtk.SuggestionPopup;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TableViewSelectionListener;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.TextInputContentListener;
import org.apache.pivot.wtk.Window;

/**
 *
 * @author pfholden
 */

public class AppWindowUI extends Window implements Bindable {
    @BXML private ImageView cardImageView;
    @BXML private Label cardText;
    @BXML private PushButton searchButton;
    @BXML private PushButton addButton;
    @BXML private TextInput nameSearch;
    @BXML private TextInput textSearch;
    @BXML private TextInput typeSearch;
    @BXML private TextInput colorsSearch;
    @BXML private TableView cardsTableView;
    
    private static AppWindowUI instance;
    private ImageView origImageView;
    private ArrayList<String> cardNames;
    private SuggestionPopup suggestionPopup = new SuggestionPopup();
    
    private CardSearchService cardSearchService;

    public CardSearchService getCardSearchService() {
        return cardSearchService;
    }

    public void setCardSearchService(CardSearchService cardSearchService) {
        this.cardSearchService = cardSearchService;
    }
    
    public void nameSearchSuggestionConfig(){
        // Prefill card name array for popup suggestions.
        cardNames = new ArrayList<String>();
        cardNames.setComparator(String.CASE_INSENSITIVE_ORDER);
        
        java.util.List<Card> cardsTempList = this.getCardSearchService().searchCards("", "", "", "", "");
        
        for(Card card:cardsTempList){
            cardNames.add(card.getName());
        }
    }
  
     // Action invoked to run search queary
    private Action searchAction = new Action(false) {
        @Override
        @SuppressWarnings("unchecked")
        public void perform(Component source) {
            
            
            List<Card> cards = new ArrayList<Card>();   
            java.util.List<Card> cardsTempList;
            String nameSearchStr = nameSearch.getText();
            String textSearchStr = textSearch.getText();
            String typeSearchStr = typeSearch.getText();
            String colorsSearchStr = colorsSearch.getText();
            
            nameSearchStr=nameSearchStr.replace(" ", "+");
            
//            String searchStr = null;
//            boolean first = false;
//            
//            if (nameSearchStr != null ) {
//                first = true;
//                searchStr = "name="+nameSearchStr;
//            }
//            if (textSearchStr != null ) {                
//                if (first){
//                    searchStr = searchStr + "&" + "text=" + textSearchStr;
//                } else {
//                    first = true;
//                    searchStr = "text=" + textSearchStr;
//                }
//            }
            
//            cardsTempList = CardAPI.getAllCards(Arrays.asList(searchStr));
//                System.out.print(cardsTempList.toString());
            cardsTempList = cardSearchService.searchCards(nameSearchStr, textSearchStr, typeSearchStr, 
                    colorsSearchStr, "");
            for(Card card:cardsTempList){
                cards.add(card);
            }
            cardsTableView.setTableData(cards);
            nameSearch.setText("");
            textSearch.setText("");
            typeSearch.setText("");
            colorsSearch.setText("");
            
            if (cards.getLength() == 1){
                URL imageUrl;
                if (cards.get(0).getImageUrl() != "-1"){
                    try {
                        imageUrl = new URL(cards.get(0).getImageUrl());
                        cardImageView.setImage(imageUrl);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(AppWindowUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                cardText.setText(cards.get(0).getText());
            }else{
                cardImageView.clearImage();
                cardText.setText("");
            }
            
//            ArrayList<String> options = new ArrayList<String>();
//            options.add("OK");
//            options.add("Cancel");
//            Prompt prompt = new Prompt(MessageType.QUESTION, "Please select your favorite icon:", options);
//            prompt.setOptions(options);
//            prompt.setSelectedOptionIndex(0);
//            prompt.getDecorators().update(0, new ReflectionDecorator());
//            prompt.open(instance.getWindow());
            
            Prompt.prompt(MessageType.INFO, "Cards found: "+cards.getLength(), instance.getWindow());
        }
    };
    private Action addAction = new Action(false) {
        @Override
        @SuppressWarnings("unchecked")
        public void perform(Component source) {
            // Add card to owned table
            Alert cardAdded = new Alert();
            cardAdded.setMessage("Card added");
            cardAdded.open( instance.getWindow());
//            try { 
//                Thread.sleep(2000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(CAppWindowUI.class.getName()).log(Level.SEVERE, null, ex);
//            }
            cardAdded.close();
        }
    };
 
    
    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
        
        cardsTableView.getTableViewSelectionListeners().add(new TableViewSelectionListener.Adapter() {
            Card card = null;
            
            @Override
            public void selectedRangesChanged(TableView tableView, Sequence<Span> previousSelectedRanges) {
                int firstSelectedIndex = cardsTableView.getFirstSelectedIndex();
                if (firstSelectedIndex != -1) {
                    int lastSelectedIndex = cardsTableView.getLastSelectedIndex();

                    if (firstSelectedIndex == lastSelectedIndex) {
                        List<Card> tableData = (List<Card>)cardsTableView.getTableData();
                        card = tableData.get(firstSelectedIndex);
                        try {
                        String cardTextDisplay = null;
                        if(card.getName() != null) cardTextDisplay = card.getName();
                        if(card.getText() != null) cardTextDisplay = cardTextDisplay+"\n\n"+card.getText();
                        if (card.getFlavor() != null ) cardTextDisplay = cardTextDisplay+"\n\n"+card.getFlavor();
                        cardText.setText(cardTextDisplay);
                        if (card.getImageUrl() != null){
                            URL imageUrl = new URL(card.getImageUrl());
                            cardImageView.setImage(imageUrl);
                        } else {
                            cardImageView.clearImage();
                        }
                        addAction.setEnabled(true);
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(AppWindowUI.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
                }
            };
        });
        
        // Add name search input event handlers
        nameSearch.getTextInputContentListeners().add(new TextInputContentListener.Adapter() {
            @Override
            public void textChanged(TextInput textInput) {
                searchAction.setEnabled(textInput.getCharacterCount() > 0);
            }
            
            @Override
            public void textInserted(TextInput textInput, int index, int count) {
                String text = textInput.getText();
                ArrayList<String> suggestions = new ArrayList<String>();

                if (text.length() > 2){
                    for (String cardName : cardNames) {
                        if (cardName.toUpperCase().startsWith(text.toUpperCase())) {
                            suggestions.add(cardName);
                        }
                    }

                    if (suggestions.getLength() > 0) {
                        suggestionPopup.setSuggestionData(suggestions);
                        suggestionPopup.open(textInput);
                    }
                }
            }

            @Override
            public void textRemoved(TextInput textInput, int index, int count) {
                suggestionPopup.close();
            }
        });
        
        nameSearch.getComponentKeyListeners().add(new ComponentKeyListener.Adapter() {
            @Override
            public boolean keyPressed(Component component, int keyCode, Keyboard.KeyLocation keyLocation) {
                if (keyCode == Keyboard.KeyCode.ENTER) {
                    if (searchAction.isEnabled()) {
                        searchAction.perform(component);
                    }
                }
                return false;
            }
        });
        
        // Add text search input event handlers
        textSearch.getTextInputContentListeners().add(new TextInputContentListener.Adapter() {
            @Override
            public void textChanged(TextInput textInput) {
                searchAction.setEnabled(textInput.getCharacterCount() > 0);
            }
        });
        
        textSearch.getComponentKeyListeners().add(new ComponentKeyListener.Adapter() {
            @Override
            public boolean keyPressed(Component component, int keyCode, Keyboard.KeyLocation keyLocation) {
                if (keyCode == Keyboard.KeyCode.ENTER) {
                    if (searchAction.isEnabled()) {
                        searchAction.perform(component);
                    }
                }
                return false;
            }
        });
        
        // Add type search input event handlers
        typeSearch.getTextInputContentListeners().add(new TextInputContentListener.Adapter() {
            @Override
            public void textChanged(TextInput typeInput) {
                searchAction.setEnabled(typeInput.getCharacterCount() > 0);
            }
        });
        
        typeSearch.getComponentKeyListeners().add(new ComponentKeyListener.Adapter() {
            @Override
            public boolean keyPressed(Component component, int keyCode, Keyboard.KeyLocation keyLocation) {
                if (keyCode == Keyboard.KeyCode.ENTER) {
                    if (searchAction.isEnabled()) {
                        searchAction.perform(component);
                    }
                }
                return false;
            }
        });
        
        // Add colors search input event handlers
        colorsSearch.getTextInputContentListeners().add(new TextInputContentListener.Adapter() {
            @Override
            public void textChanged(TextInput colorsInput) {
                searchAction.setEnabled(colorsInput.getCharacterCount() > 0);
            }
        });
        
        colorsSearch.getComponentKeyListeners().add(new ComponentKeyListener.Adapter() {
            @Override
            public boolean keyPressed(Component component, int keyCode, Keyboard.KeyLocation keyLocation) {
                if (keyCode == Keyboard.KeyCode.ENTER) {
                    if (searchAction.isEnabled()) {
                        searchAction.perform(component);
                    }
                }
                return false;
            }
        });
        
        searchButton.setAction(searchAction);
        addButton.setAction(addAction);
    }

    public AppWindowUI() {
        instance = this;
        
        Action.getNamedActions().put("reIndex", new Action() {
            @Override
            public void perform(Component source) {
                try {
                    // Needs to be started on a background thread
                    cardSearchService.reloadIndex();
                } catch (InterruptedException ex) {
                    Logger.getLogger(AppWindowUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }
    
}
 
