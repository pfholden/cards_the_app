/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.utils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author pfholden
 */
public class MtgSymbolConvert {
    static Map<String, String> manaMap = new HashMap<>();
	static {
		manaMap.put("{T}", "icons/tap.gif");
		manaMap.put("{Q}", "icons/untap.gif");
		manaMap.put("{U}", "icons/mana/Symbol_U_mana.gif");
		manaMap.put("{W}", "icons/mana/Symbol_W_mana.gif");
		manaMap.put("{B}", "icons/mana/Symbol_B_mana.gif");
		manaMap.put("{G}", "icons/mana/Symbol_G_mana.gif");
		manaMap.put("{R}", "icons/mana/Symbol_R_mana.gif");
		manaMap.put("{C}", "icons/mana/Symbol_C_mana.png");
		manaMap.put("{S}", "icons/mana/Symbol_snow_mana.gif");
		manaMap.put("{X}", "icons/mana/Symbol_X_mana.gif");
		manaMap.put("{Y}", "icons/mana/Symbol_Y_mana.gif");
		manaMap.put("{Z}", "icons/mana/Symbol_Z_mana.gif");
		manaMap.put("{0.5}", "icons/mana/Symbol_500_mana.gif");
		manaMap.put("{05}", "icons/mana/Symbol_500_mana.gif");
		manaMap.put("{500}", "icons/mana/Symbol_500_mana.gif");
		manaMap.put("{R/W}", "icons/mana/Symbol_RW_mana.gif");
		manaMap.put("{R/G}", "icons/mana/Symbol_RG_mana.gif");
		manaMap.put("{B/R}", "icons/mana/Symbol_BR_mana.gif");
		manaMap.put("{B/G}", "icons/mana/Symbol_BG_mana.gif");
		manaMap.put("{G/U}", "icons/mana/Symbol_GU_mana.gif");
		manaMap.put("{G/W}", "icons/mana/Symbol_GW_mana.gif");
		manaMap.put("{W/U}", "icons/mana/Symbol_WU_mana.gif");
		manaMap.put("{W/B}", "icons/mana/Symbol_WB_mana.gif");
		manaMap.put("{U/B}", "icons/mana/Symbol_UB_mana.gif");
		manaMap.put("{U/R}", "icons/mana/Symbol_UR_mana.gif");
		manaMap.put("{0}", "icons/mana/Symbol_0_mana.gif");
		manaMap.put("{1}", "icons/mana/Symbol_1_mana.gif");
		manaMap.put("{2}", "icons/mana/Symbol_2_mana.gif");
		manaMap.put("{3}", "icons/mana/Symbol_3_mana.gif");
		manaMap.put("{4}", "icons/mana/Symbol_4_mana.gif");
		manaMap.put("{5}", "icons/mana/Symbol_5_mana.gif");
		manaMap.put("{6}", "icons/mana/Symbol_6_mana.gif");
		manaMap.put("{7}", "icons/mana/Symbol_7_mana.gif");
		manaMap.put("{8}", "icons/mana/Symbol_8_mana.gif");
		manaMap.put("{9}", "icons/mana/Symbol_9_mana.gif");
		manaMap.put("{10}", "icons/mana/Symbol_10_mana.gif");
		manaMap.put("{11}", "icons/mana/Symbol_11_mana.gif");
		manaMap.put("{12}", "icons/mana/Symbol_12_mana.gif");
		manaMap.put("{14}", "icons/mana/Symbol_14_mana.gif");
		manaMap.put("{13}", "icons/mana/Symbol_13_mana.gif");
		manaMap.put("{15}", "icons/mana/Symbol_15_mana.gif");
		manaMap.put("{16}", "icons/mana/Symbol_16_mana.gif");
		manaMap.put("{1000000}", "icons/mana/Symbol_1000000_mana.gif");
		manaMap.put("{2/W}", "icons/mana/Symbol_2W_mana.gif");
		manaMap.put("{2/U}", "icons/mana/Symbol_2U_mana.gif");
		manaMap.put("{2/B}", "icons/mana/Symbol_2B_mana.gif");
		manaMap.put("{2/G}", "icons/mana/Symbol_2G_mana.gif");
		manaMap.put("{2/R}", "icons/mana/Symbol_2R_mana.gif");
		manaMap.put("{UP}", "icons/mana/Symbol_UP_mana.gif");
		manaMap.put("{WP}", "icons/mana/Symbol_WP_mana.gif");
		manaMap.put("{BP}", "icons/mana/Symbol_BP_mana.gif");
		manaMap.put("{GP}", "icons/mana/Symbol_GP_mana.gif");
		manaMap.put("{RP}", "icons/mana/Symbol_RP_mana.gif");
	}
        
        /**
         * Method for parsing out the mana tags in text fields. For example,
         * "{T}: Add any color mana."
         * @param inputText Text possibly containing {*} symbol
         * @return Map containing keys of image* or string* with mana symbols 
         * mapped with image* and the other text as string*.
         */
        
        public static Map<String, String> parseManaSymbols(String inputText) {
            System.out.println("In parse. Passed string: "+inputText);
            
            Map<String, String> convertedText = new LinkedHashMap<>();
            int stringNum = 0;
            int imgNum = 0;
            int beginInd = 0;
            int currentChar=inputText.indexOf("{");
            
            if (currentChar > -1 ){
                while(currentChar < inputText.length()){
                    if(inputText.charAt(currentChar) == '{'){
                        if (currentChar > 0)
                            convertedText.put("string"+stringNum++, inputText.substring(beginInd, currentChar).trim());
                        //Multiple symbols may be together
                        for (Iterator<String> iterator = manaMap.keySet().iterator(); iterator.hasNext();) {
                            String sym = iterator.next();
                            if(inputText.startsWith(sym, currentChar)){
                                //Found match. Break string and add to map with image tag
                                //TODO: figure out how to add sequential image tags to one string.
                                convertedText.put("image"+imgNum++, inputText.substring(currentChar, currentChar+sym.length()));
                                currentChar = currentChar+sym.length();   
                                inputText = inputText.substring(currentChar).trim();  
                                currentChar=0;
                            }
                        }

                    }else currentChar++;

                }
            }else currentChar=inputText.length();
            if (currentChar > 0)
                convertedText.put("string"+stringNum++, inputText);
            return convertedText;
        }    
}
