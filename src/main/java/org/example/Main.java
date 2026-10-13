package org.example;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException{
        // Verificam daca avem 2 sau 4 argumente, altfel returnam eroare
        if(args.length != 2 && args.length != 4) {
            System.out.println("Error: Wrong number of arguments");
            return;
        }

        // Initializam baza de date
        Database database = Database.getInstance();

        // Daca avem exact 2 argumente
        if(args.length == 2) {
            // Luam primul si al doilea argument
            String pathType = args[0];
            String fileLocation = args[1];
            // Deschidem fisierul de input si output
            File file = new File(fileLocation + ".in");
            File fileOut = new File(fileLocation + ".out");
            // In caz ca nu exista fisierul de output, il cream
            if(!fileOut.exists()) {
                fileOut.createNewFile();
            }
            PrintWriter printWriter = new PrintWriter(fileOut);
            // Daca pathType-ul este museums
            if(pathType.equals("museums")) {
                Scanner scanner = new Scanner(file);
                scanner.nextLine();
                // Adaugam muzeele in baza de date
                database.addMuseums(scanner, printWriter);
                scanner.close();
                // Daca pathType-ul este groups
            } else if(pathType.equals("groups")) {
                Scanner scanner = new Scanner(file);
                scanner.nextLine();
                try {
                    // Adaugam grupurile in baza de date
                    database.addGroups(scanner, printWriter);
                } catch (GuideExistsException | GroupNotExistsException | GuideTypeException e) {
                    printWriter.println(e.getMessage());
                }
                scanner.close();
            }
            printWriter.close();
        }



        // Daca avem exact 4 argumente
        if(args.length == 4) {
            // Luam primul, al doilea, al treilea si al patrulea argument
            String pathType = args[0];
            String fileLocationMuzeum = args[1];
            String fileLocationGroup = args[2];
            String fileLocationEvents = args[3];

            // Deschidem fisierele de input si output pentru muzee
            File fileMuzeum = new File(fileLocationMuzeum + ".in");
            File fileMuzeumOut = new File(fileLocationMuzeum + ".out");
            // In caz ca nu exista fisierul de output pentru muzee, il cream
            if (!fileMuzeumOut.exists()) {
                fileMuzeumOut.createNewFile();
            }
            PrintWriter printWriter = new PrintWriter(fileMuzeumOut);
            Scanner scanner = new Scanner(fileMuzeum);
            scanner.nextLine();
            // Adaugam muzeele in baza de date
            database.addMuseums(scanner, printWriter);
            scanner.close();
            printWriter.close();

            // Deschidem fisierele de input si output pentru grupuri
            File fileGroup = new File(fileLocationGroup + ".in");
            File fileGroupOut = new File(fileLocationGroup + ".out");
            // In caz ca nu exista fisierul de output pentru grupuri, il cream
            if (!fileGroupOut.exists()) {
                fileGroupOut.createNewFile();
            }
            printWriter = new PrintWriter(fileGroupOut);
            scanner = new Scanner(fileGroup);
            scanner.nextLine();
            try {
                // Adaugam grupurile in baza de date
                database.addGroups(scanner, printWriter);
            } catch (GuideExistsException | GroupNotExistsException | GuideTypeException e) {
                printWriter.println(e.getMessage());
            }
            scanner.close();
            printWriter.close();

            // Deschidem fisierele de input si output pentru evenimente
            File fileEvents = new File(fileLocationEvents + ".in");
            File fileEventsOut = new File(fileLocationEvents + ".out");
            // In caz ca nu exista fisierul de output pentru evenimente, il cream
            if (!fileEventsOut.exists()) {
                fileEventsOut.createNewFile();
            }
            printWriter = new PrintWriter(fileEventsOut);
            scanner = new Scanner(fileEvents);
            scanner.nextLine();
            try {
                // Adaugam evenimentele in baza de date
                database.eventsAdd(scanner, printWriter);
            } catch (Exception e) {
                printWriter.println(e.getMessage());
            }
            scanner.close();
            printWriter.close();

        }
    }
}
