package com.tantech.cards;

import com.tantech.cards.dbimport.DbImportService;
import com.tantech.cards.search.CardSearchService;
import com.tantech.cards.ui.CardsAppWindow;
import static java.lang.System.exit;
import java.util.Scanner;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
//@ComponentScan({"com.tantech.cards","com.tantech.cards.ui","com.tantech.cards.search","com.tantech.cards.db","com.tantech.cards.db"})
//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
//@Component
public class CardsApplication implements CommandLineRunner {
    
    @Autowired
    private CardSearchService cardSearchService;
    @Autowired
    private DbImportService dbImportService;
    
    @Autowired
    CardsAppWindow mainWindow;
    

    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(CardsApplication.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);
        ConfigurableApplicationContext context = new SpringApplicationBuilder(CardsApplication.class).headless(false).run(args);
////        DesktopApplicationContext.main(CardsAppWindow.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        
        if (args[0].compareTo("--commandline") != 0){
        
            DesktopApplicationContext.main(mainWindow, args);
        }else{
        
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
    //                    cardSearchService.searchCards(name, text, type, colors, setName);
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
