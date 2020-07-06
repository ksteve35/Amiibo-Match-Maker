package amiibomatchmaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AmiiboMatchMaker {
    
    public static ArrayList<Amiibo> arr;
    public static int[][] spreadsheetData, editedSpreadsheetData;

    public static void main(String[] args) {
        
        arr = init();
        spreadsheetData = importSpreadsheetData();
        editedSpreadsheetData = importSpreadsheetData();
        Scanner scan = new Scanner(System.in);
        
        while (true) {
            try {
                System.out.print("How many matches would you like to generate?\t\t");
                int howManyMatchesToGenerate = scan.nextInt();
                if (howManyMatchesToGenerate > 0) {
                    generateMatch(howManyMatchesToGenerate);
                    break;
                } else {
                    throw new Exception("Number of matches to generate was less than one (1).");
                }
            } catch (Exception e) {
                System.out.println("\nError. Please make sure you only input a number.");
            }
        }
        
        scan.close();
        System.out.println("\n\nThank you for using Amiibo Match Maker!");
        
        
        /*Collections.sort(arr);
        printAllAmiibosData(arr);
        ArrayList<Amiibo> sorted = init();
        Collections.sort(sorted);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        printAllAmiibosData(sorted);*/
        
        
    }
    
    public static ArrayList<Amiibo> init() {
        Amiibo[] amiibos = {new Amiibo("Papa Smurf", "Ganondorf", 22, 5),
                            new Amiibo("Hubris", "Pit", 6, 2),
                            new Amiibo("PhDeezNuts", "Dr. Mario", 14, 7),
                            new Amiibo("Red-Gojira", "Bowser", 14, 9),
                            new Amiibo("Capitalism", "Wario", 6, 4),
                            new Amiibo("El Diablo", "Little Mac", 8, 7),
                            new Amiibo("B∞ty", "Zero Suit Samus", 8, 9),
                            new Amiibo("2Fit2Quit", "Wii Fit Trainer", 11, 15),
                            new Amiibo("Nurse Puff", "Jigglypuff", 8, 11),
                            new Amiibo("Hank Pym", "Olimar", 13, 20),
                            new Amiibo("PapíMars", "Mario", 8, 16),
                            new Amiibo("Legolas", "Toon Link", 10, 24),
                            new Amiibo("Lt. Pigeon", "Captain Falcon", 25, 3),
                            new Amiibo("Wawa Melon", "Yoshi", 13, 11)};
        ArrayList<Amiibo> temp = new ArrayList<Amiibo>();
        for (Amiibo amiibo : amiibos) {
            temp.add(new Amiibo(amiibo));
        }
        return temp;
    }
    
    public static void printAllAmiibosData(List<Amiibo> a) {
        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i).printAmiiboData() + "\n~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
    }
    
    public static int[][] importSpreadsheetData() {
        
        /*
        This method takes a simple copy-and-paste from the monthly Google
        Spreadsheet to import each of the amiibo'dataRaw matchup dataRaw.
        NULL String values are converted into -2.
        */
        
        String dataRaw = "NULL	0	0	0	0	1	0	0	0	0	0	0	0	0\n" +
                        "0	NULL	0	0	1	0	0	0	0	0	0	1	0	0\n" +
                        "0	0	NULL	0	0	0	0	0	0	0	0	0	0	0\n" +
                        "1	0	0	NULL	0	0	0	0	0	0	0	1	1	0\n" +
                        "0	0	0	0	NULL	0	0	0	0	0	0	0	0	0\n" +
                        "0	0	0	0	0	NULL	0	0	0	0	0	0	0	0\n" +
                        "0	0	0	0	0	0	NULL	1	1	0	0	0	0	0\n" +
                        "0	0	0	0	0	0	0	NULL	0	0	1	0	1	0\n" +
                        "0	0	0	0	0	0	0	0	NULL	0	0	0	0	0\n" +
                        "0	0	1	0	0	0	0	0	0	NULL	0	0	0	0\n" +
                        "0	0	0	0	1	0	0	0	0	0	NULL	1	0	0\n" +
                        "0	0	0	0	0	0	0	0	0	0	0	NULL	0	0\n" +
                        "1	1	1	0	1	1	1	0	1	2	2	1	NULL	1\n" +
                        "0	0	0	0	0	0	0	0	0	0	0	0	0	NULL";
        
        //dataRaw = dataRaw.replaceAll("[\\s]", " ");
        String[] rows = dataRaw.split("\n");
        int[][] data = new int[arr.size()][arr.size()];
        
        for (int i = 0; i < data.length; i++) {
            String[] cells = rows[i].replaceAll("[\\s]", " ").split(" ");
            for (int j = 0; j < rows.length; j++) {
                if (cells[j].equals("NULL"))
                    data[i][j] = -1;
                else
                    data[i][j] = Integer.valueOf(cells[j]);
            }
        }
        
        return data;
    }

    public static void generateMatch(int n) {
        
        System.out.println("\nGenerating " + n + " matches...");
        double[] chances = new double[arr.size()];
        Random rand = new Random();
        
        for (int i = 0; i < n; i++) {
            chances[i] = generateChance(i, rand);
        }
    }

    public static double generateChance(int ID, Random rand) {
        
        /*
        ID Values
        0 = Papa Smurf, 1 = Hubris, 2 = PhDeezNuts, 3 = Red-Gojira,
        4 = Capitalism, 5 = El Diablo, 6 = B∞ty, 7 = 2Fit2Quit,
        8 = Nurse Puff, 9 = Hank Pym, 10 = PapíMars, 11 = Legolas,
        12 = Lt. Pigeon, 13 = Wawa Melon
        */
        
        double retVal = 0.0;
        
        
        return retVal;
    }
    
}