package com.tantech.cards;

import com.tantech.cards.dbimport.DbImportService;
import com.tantech.cards.search.CardSearchService;
import com.tantech.cards.ui.CardsAppWindow;
import com.tantech.cards.ui.CardsSwingUI;
import static java.lang.System.exit;
import java.util.Scanner;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CardsApplication implements CommandLineRunner {
    
    @Autowired
    private CardSearchService cardSearchService;
    @Autowired
    private DbImportService dbImportService;
    
    @Autowired
    CardsSwingUI mainWindow;
//    CardsAppWindow mainWindow;
    

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(CardsApplication.class).headless(false).run(args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        
        if (args.length == 0){
        
//            DesktopApplicationContext.main(mainWindow, args);
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(CardsSwingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(CardsSwingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(CardsSwingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(CardsSwingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    mainWindow.updateOwnedTable("", "", "", "", "");
                    mainWindow.setTitle("Cards: The App");
                    mainWindow.addNameSearchSuggestions();
                    mainWindow.setVisible(true);
                }
            });
        }else {
           
            for (String arg: args){
                if(arg.compareTo("--commandline") == 0){
                    Scanner readInput = new Scanner(System.in);

                    boolean done = false;

                    while (!done){
                        System.out.println("Select: ");
                        System.out.println("  1. Search for cards");
                        System.out.println("  2. Search owned cards");
                        System.out.println("  3. ReIndex data");
                        System.out.println("  4. Import Owned cards");
                        System.out.println("  5. Import All cards");
                        System.out.println("  9 to Quit");
                        int n = readInput.nextInt();
                        switch(n){
                            case 1:
                                // Search
                                // Clear input line
                                String name = readInput.nextLine();
                                System.out.println("Enter search strings for the following prompts: ");
                                System.out.print("Name: ");
                                name = readInput.nextLine();
                                System.out.print("Text: ");
                                String text = readInput.nextLine();
                                System.out.print("Type: ");
                                String type = readInput.nextLine();
                                System.out.print("Colors: ");
                                String colors = readInput.nextLine();
                                System.out.print("Set Name: ");
                                String setName = readInput.nextLine();
                                System.out.println("");
                                cardSearchService.searchCards(name, text, type, colors, setName);
                                break;
                            case 2:
                                // Search owned cards
                                // Clear input line
                                String ownedName = readInput.nextLine();
                                System.out.println("Enter search strings for the following prompts: ");
                                System.out.print("Name: ");
                                ownedName = readInput.nextLine();
                                System.out.print("Text: ");
                                String ownedText = readInput.nextLine();
                                System.out.print("Type: ");
                                String ownedType = readInput.nextLine();
                                System.out.print("Colors: ");
                                String ownedColors = readInput.nextLine();
                                System.out.print("Set Name: ");
                                String ownedSetName = readInput.nextLine();
                                System.out.println("");
                                cardSearchService.searchOwnedCards(ownedName, ownedText, ownedType, ownedColors, ownedSetName);
                                break;
                            case 3:
                                System.out.println(cardSearchService.reloadIndex());
                                break;
                            case 4:
                                String fileName = readInput.nextLine();
                                System.out.print("Enter filename to import: ");
                                fileName = readInput.nextLine();
                                dbImportService.importOwnedCsv(fileName);
                                break;
                            case 5:
                                dbImportService.importDb();
                                break;
                            case 9:
                                done = true;
                            default:
                        }
                    }
                    exit(0);
                }
            }
        }
    }
}
