/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.ui;


import com.tantech.cards.search.CardSearchService;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
 


/**
 *
 * @author pfholden
// */


//@SpringBootApplication
//@ComponentScan({"com.tantech.cards","com.tantech.cards.ui","com.tantech.cards.search","com.tantech.cards.db","com.tantech.cards.db"})

@org.springframework.stereotype.Component
public class CardsAppWindow implements Application {
    
    private ConfigurableApplicationContext springContext;
    
    @Autowired
    private CardSearchService cardSearchService;
    
    private AppWindowUI window = null;
//    private ImageView menuImageView;
    
    
    @Override
    public void startup(Display display, Map<String, String> properties) throws Exception {
       
//        springContext = SpringApplication.run(CardsAppWindow.class);
        
        //The casted class has to match what is defined at the top of the bxml file.
        BXMLSerializer bxmlSerializer = new BXMLSerializer();
        window = (AppWindowUI)bxmlSerializer.readObject(AppWindowUI.class, "main_window.bxml");
        
        window.setCardSearchService(cardSearchService);
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
    
//    public static void main(String[] args) {
// 
//        DesktopApplicationContext.main(CardsAppWindow.class, args);
//    }
}
