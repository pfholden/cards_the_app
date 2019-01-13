/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.ui;


import com.tantech.cards.dbimport.DbImportService;
import com.tantech.cards.search.CardSearchService;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Display;
import org.springframework.beans.factory.annotation.Autowired;
 


/**
 *
 * @author pfholden
 */


@org.springframework.stereotype.Component
public class CardsAppWindow implements Application {
    
    @Autowired
    private CardSearchService cardSearchService;
    
    @Autowired
    private DbImportService dbImportService;
    
    private AppWindowUI window = null;
    
    
    @Override
    public void startup(Display display, Map<String, String> properties) throws Exception {
        
        //The casted class has to match what is defined at the top of the bxml file.
        BXMLSerializer bxmlSerializer = new BXMLSerializer();
        window = (AppWindowUI)bxmlSerializer.readObject(AppWindowUI.class, "main_window.bxml");
        
        window.setCardSearchService(cardSearchService);
        window.setDbImportService(dbImportService);
        window.nameSearchSuggestionConfig();
        
        window.open(display);
    }
 
    @Override
    public boolean shutdown(boolean optional) {
        if (window != null) {
            window.close();
        }
 
        return false;
    }
 
    @Override
    public void suspend() {
    }
 
    @Override
    public void resume() {
    }
}
