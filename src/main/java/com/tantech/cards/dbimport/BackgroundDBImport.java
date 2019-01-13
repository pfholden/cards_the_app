/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.dbimport;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskExecutionException;

/**
 *
 * @author pfholden
 */



public class BackgroundDBImport extends Task<String> {
    
    private DbImportService dbImportService;
    
    @Override
    public String execute() throws TaskExecutionException {
        
        try {
            dbImportService.importDb();
        } catch (IOException ex) {
            Logger.getLogger(BackgroundDBImport.class.getName()).log(Level.SEVERE, null, ex);
        }
       

        // Return a simulated result value
        return "Done";
    }

    public BackgroundDBImport(DbImportService dbImportService) {
        this.dbImportService = dbImportService;
    }
}