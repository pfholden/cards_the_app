package com.tantech.cards;

import com.tantech.cards.search.CardSearchService;
import static java.lang.System.exit;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CardsApplication implements CommandLineRunner {
    
    @Autowired
    private CardSearchService cardSearchService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CardsApplication.class);
//        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
    @Override
    public void run(String... args) throws Exception {
        
        Scanner readInput = new Scanner(System.in);
        
        boolean done = false;
        
        while (!done){
            System.out.println("Select: ");
            System.out.println("  1. Search for cards");
            System.out.println("  2. ReIndex data");
            System.out.println("  3. Quit");
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
                    cardSearchService.getluceneCards(name, text, type, colors, setName);
                    break;
                case 2:
                    System.out.println(cardSearchService.reloadIndex());
                    break;
                case 3:
                    done = true;
                default:
            }
        }
        

               
// Below is to show how to use the Autowired service above.
//        if (args.length > 0) {
//            System.out.println(helloService.getMessage(args[0].toString()));
//        } else {
//            System.out.println(helloService.getMessage());
//        }

        exit(0);
    }
}
