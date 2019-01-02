/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.dbimport;

import com.tantech.cards.db.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author pfholden
 */
public class GetAllCards {
     
    protected static String ENDPOINT = "https://api.magicthegathering.io/v1";
    protected static OkHttpClient CLIENT = new OkHttpClient();
    private static String DELIM_LINK = ",";
    private static String DELIM_LINK_PARAM = ";";
    private static final String RESOURCE_PATH = "cards";
        
//    public static List<Card> getAllCards() {
//		return getList(RESOURCE_PATH, "cards", Card.class);
//	}
        
    public static boolean getAllCards(MTGSetRepository setRepo, CardRepository cardRepo) throws IOException{
          
        String path = "cards";
        
        String url = String.format("%s/%s", ENDPOINT, path);
        Request request = new Request.Builder().url(url).build();
        Response response;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
//        Need to add retry if call to HTTP server times out.
        response = CLIENT.newCall(request).execute();

//        ArrayList<CardWrapper> newCardWrapper = new ArrayList<>();
        CardWrapper newCardWrapper;
        String linkHeader = response.headers().get("Link");
        if (linkHeader == null || linkHeader.isEmpty() || path.contains("page=")) {
            newCardWrapper = mapper.readValue(response.body().string(), CardWrapper.class);    
            newCardWrapper.addToDB(setRepo, cardRepo);
            return true;
        } else {

            int numberOfPages = 0;
            String[] linkStrings = linkHeader.split(DELIM_LINK);
            List<String[]> paramList = new ArrayList<>();
            for (String link : linkStrings) {
                    paramList.add(link.split(DELIM_LINK_PARAM));
            }
            for (String[] params : paramList) {
                    if (params[1].contains("last")) {
                            Matcher matcher = Pattern.compile("page=[0-9]+").matcher(params[0]);
                            numberOfPages = (matcher.find()) ? Integer.parseInt(matcher.group().substring(5)) : 0;
                    }
            }

            newCardWrapper = mapper.readValue(response.body().string(), CardWrapper.class);    
            newCardWrapper.addToDB(setRepo, cardRepo);
            System.out.println("Downloading "+numberOfPages+" pages");
            if (!url.contains("?")) {
                    url += "?";
            }

            for(int i = 1; i <= numberOfPages; i++){
                request = new Request.Builder().url(url + "&page=" + i).build();
                        
//        Need to add retry if call to HTTP server times out.
                response = CLIENT.newCall(request).execute();
                // @FIXME 
                System.out.println("Downloading page"+i+" and adding to DB");
                
//                    Add pages to DB
                newCardWrapper = mapper.readValue(response.body().string(), CardWrapper.class);    
                newCardWrapper.addToDB(setRepo, cardRepo);
                    
            }

            return true;
        }
    }            
}
