/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.ui;

import com.tantech.cards.db.Card;
import com.tantech.cards.db.OwnedCard;
import com.tantech.cards.dbimport.BackgroundDBImport;
import com.tantech.cards.dbimport.DbImportService;
import com.tantech.cards.search.CardSearchService;
import java.io.IOException;
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
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Alert;
import org.apache.pivot.wtk.ApplicationContext;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonStateListener;
import org.apache.pivot.wtk.Checkbox;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentKeyListener;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Keyboard;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.Span;
import org.apache.pivot.wtk.SuggestionPopup;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TableViewSelectionListener;
import org.apache.pivot.wtk.TaskAdapter;
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
    @BXML private PushButton clearButton;
    @BXML private PushButton addButton;
    @BXML private TextInput nameSearch;
    @BXML private TextInput textSearch;
    @BXML private TextInput typeSearch;
    @BXML private TextInput colorsSearch;
    @BXML private Checkbox owned;
    @BXML private TableView cardsTableView;
    @BXML private TableView ownedTableView;
    @BXML private TabPane tabPane;
    @BXML private FileBrowserSheet fileBrowserSheet;
    
    private static AppWindowUI instance;
    private ImageView origImageView;
    private ArrayList<String> cardNames;
    private SuggestionPopup suggestionPopup = new SuggestionPopup();
    
    private CardSearchService cardSearchService;
    private DbImportService dbImportService;

    // Getters and Setters
    
    public CardSearchService getCardSearchService() {
        return cardSearchService;
    }

    public void setCardSearchService(CardSearchService cardSearchService) {
        this.cardSearchService = cardSearchService;
    }

    public DbImportService getDbImportService() {
        return dbImportService;
    }

    public void setDbImportService(DbImportService dbImportService) {
        this.dbImportService = dbImportService;
    }
    
    // Methods
    
    public void nameSearchSuggestionConfig(){
        // Prefill card name array for popup suggestions.
        cardNames = new ArrayList<String>();
        cardNames.setComparator(String.CASE_INSENSITIVE_ORDER);
        
        java.util.List<String> cardsTempList = this.getCardSearchService().searchNames();
        
        for(String name:cardsTempList){
            cardNames.add(name);
        }
    }
    
    private void displayCardsFound(int number){
        Prompt prompt = new Prompt();
        prompt.setMessage("Cards found: "+number);
        prompt.open(instance.getWindow());
        ApplicationContext.scheduleCallback(new Runnable() {
            @Override
            public void run() {
                prompt.close();                    
            }
        }, 2000);
    }
    
    private void displaySingleCard(Card card) {
        String cardTextDisplay = null;
        if(card.getName() != null) cardTextDisplay = card.getName();
        if(card.getText() != null) cardTextDisplay = cardTextDisplay+"\n\n"+card.getText();
        if (card.getFlavor() != null ) cardTextDisplay = cardTextDisplay+"\n\n"+card.getFlavor();
        cardText.setText(cardTextDisplay);
        if (card.getImageUrl() != null){
            URL imageUrl = null;
            try {
                imageUrl = new URL(card.getImageUrl());
            } catch (MalformedURLException ex) {
                Logger.getLogger(AppWindowUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            cardImageView.setImage(imageUrl);
        } else {
            cardImageView.clearImage();
        }
    }
  
     // Action invoked to run search queary
    private Action searchAction = new Action(false) {
        @Override
        @SuppressWarnings("unchecked")
        public void perform(Component source) {
            
            
            String nameSearchStr = nameSearch.getText();
            String textSearchStr = textSearch.getText();
            String typeSearchStr = typeSearch.getText();
            String colorsSearchStr = colorsSearch.getText();
            
            nameSearchStr=nameSearchStr.replace(" ", "+");
            
            if (owned.getState() == Button.State.SELECTED){
                java.util.List<OwnedCard> cardsTempList = cardSearchService.searchOwnedCards(nameSearchStr, textSearchStr, typeSearchStr, 
                        colorsSearchStr, "");
                List<Card> cards = new ArrayList<>();  
                for(OwnedCard ownedCard:cardsTempList){
                    cards.add(ownedCard.getCardMaster());
                }
                ownedTableView.setTableData(cards);
                if (cards.getLength() == 1){
                    displaySingleCard(cards.get(0));
                }else{
                    cardImageView.clearImage();
                    cardText.setText("");
                }
                displayCardsFound(cards.getLength());
                tabPane.setSelectedIndex(1);
            }else{
                java.util.List<Card> cardsTempList = cardSearchService.searchCards(nameSearchStr, textSearchStr, typeSearchStr, 
                        colorsSearchStr, "");
                List<Card> cards = new ArrayList<>();  
                for(Card card:cardsTempList){
                    cards.add(card);
                }
                cardsTableView.setTableData(cards);
                if (cards.getLength() == 1){
                    displaySingleCard(cards.get(0));
                }else{
                    cardImageView.clearImage();
                    cardText.setText("");
                }
                displayCardsFound(cards.getLength());
                tabPane.setSelectedIndex(0);
            }
            
            
            
//            ArrayList<String> options = new ArrayList<String>();
//            options.add("OK");
//            options.add("Cancel");
//            Prompt prompt = new Prompt(MessageType.QUESTION, "Please select your favorite icon:", options);
//            prompt.setOptions(options);
//            prompt.setSelectedOptionIndex(0);
//            prompt.getDecorators().update(0, new ReflectionDecorator());
//            prompt.open(instance.getWindow());
            
            
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
            ApplicationContext.scheduleCallback(new Runnable() {
                @Override
                public void run() {
                    cardAdded.close();                    
                }
            }, 2000);
            
        }
    };
    
    private Action clearAction = new Action() {
        @Override
        @SuppressWarnings("unchecked")
        public void perform(Component source) {
            // Clear search fields
            nameSearch.setText("");
            textSearch.setText("");
            typeSearch.setText("");
            colorsSearch.setText("");
            owned.setSelected(false);           
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
                        displaySingleCard(card);
                        addAction.setEnabled(true);
                    } 
                
                }
            };
        });
        
        ownedTableView.getTableViewSelectionListeners().add(new TableViewSelectionListener.Adapter() {
            Card card = null;
            
            @Override
            public void selectedRangesChanged(TableView tableView, Sequence<Span> previousSelectedRanges) {
                int firstSelectedIndex = ownedTableView.getFirstSelectedIndex();
                if (firstSelectedIndex != -1) {
                    int lastSelectedIndex = ownedTableView.getLastSelectedIndex();

                    if (firstSelectedIndex == lastSelectedIndex) {
                        List<Card> tableData = (List<Card>)ownedTableView.getTableData();
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
        
        ButtonStateListener checkboxStateListener = new ButtonStateListener() {
            @Override
            public void stateChanged(Button button, Button.State previousState) {
                 searchAction.setEnabled(owned.isSelected());
            }
        };

        owned.getButtonStateListeners().add(checkboxStateListener);
        searchButton.setAction(searchAction);
        clearButton.setAction(clearAction);
        addButton.setAction(addAction);
    }

    public AppWindowUI() {
        instance = this;
        
        Action.getNamedActions().put("fileOpen", new Action() {
            @Override
            public void perform(Component source) {
                fileBrowserSheet.setMode(FileBrowserSheet.Mode.OPEN);
                fileBrowserSheet.open(instance.getWindow(), new SheetCloseListener() {
                    @Override
                    public void sheetClosed(Sheet sheet) {
                        if (sheet.getResult()) {
                            System.out.println("File: "+fileBrowserSheet.getSelectedFile().getAbsolutePath());
                            String csvFile = fileBrowserSheet.getSelectedFile().getAbsolutePath();
                            try {
                                dbImportService.importOwnedCsv(csvFile);
                            } catch (IOException ex) {
                                Logger.getLogger(AppWindowUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        Alert.alert(MessageType.INFO, "Imported:"+fileBrowserSheet.getSelectedFile().getName()
                                , instance.getWindow());
                        } else {
                            Alert.alert(MessageType.INFO, "You didn't select anything.", instance.getWindow());
                        }
                    }
                });
                
            }
        });
        
        Action.getNamedActions().put("reIndex", new Action() {
            @Override
            public void perform(Component source) {
                try {
                    cardSearchService.reloadIndex();
                } catch (InterruptedException ex) {
                    Logger.getLogger(AppWindowUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        Action.getNamedActions().put("refreshDB", new Action() {
            @Override
            public void perform(Component source) {
                //Syncrhonously
//                try {
//                    dbImportService.importDb();
//                } catch (IOException ex) {
//                    Logger.getLogger(AppWindowUI.class.getName()).log(Level.SEVERE, null, ex);
//                }
                // Async
                BackgroundDBImport bgImportTask = new BackgroundDBImport(dbImportService);
                                
                TaskListener<String> taskListener = new TaskListener<String>() {
                    @Override
                    public void taskExecuted(Task<String> task) {
                        Alert cardAdded = new Alert();
                        cardAdded.setMessage("DB refreshed");
                        cardAdded.open( instance.getWindow());
                        ApplicationContext.scheduleCallback(new Runnable() {
                            @Override
                            public void run() {
                                cardAdded.close();                    
                            }
                        }, 3000);
                    }

                    @Override
                    public void executeFailed(Task<String> task) {
                        Alert cardAdded = new Alert();
                        cardAdded.setMessage("DB refresh failed");
                        cardAdded.open( instance.getWindow());
                        ApplicationContext.scheduleCallback(new Runnable() {
                            @Override
                            public void run() {
                                cardAdded.close();                    
                            }
                        }, 3000);
                    }
                };
                bgImportTask.execute(new TaskAdapter<String>(taskListener));
            }
        });
    }    
}
 
