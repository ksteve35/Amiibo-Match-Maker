package amiibomatchmaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AmiiboMatchMaker {
    
    public static ArrayList<Amiibo> arr;
    public static int[][] spreadsheetData;

    public static void main(String[] args) {
        
        arr = init();
        spreadsheetData = importSpreadsheetData();
        Scanner scan = new Scanner(System.in);
        
        // For loop loads previous match data into each Amiibo.
        for (int i = 0; i < arr.size(); i++) {
            Amiibo a = arr.get(i);
            a.setNumberOfMatchesAgainstAmiibo(loadPastMatches(a));
            a.setMatchesTotal(a.getNumberOfMatchesAgainstAmiiboSum());
        }
        
        while (true) {
            try {
                clearCMD();
                System.out.print("Welcome to the Amiibo Match Maker!\n\nHow many matches would you like to generate?\n\n");
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
    }
    
    public static ArrayList<Amiibo> init() {
        Amiibo[] amiibos = {new Amiibo("Papa Smurf", "Ganondorf"),
                            new Amiibo("Hubris", "Pit"),
                            new Amiibo("PhDeezNuts", "Dr. Mario"),
                            new Amiibo("Red-Gojira", "Bowser"),
                            new Amiibo("Capitalism", "Wario"),
                            new Amiibo("El Diablo", "Little Mac"),
                            new Amiibo("B∞ty", "Zero Suit Samus"),
                            new Amiibo("2Fit2Quit", "Wii Fit Trainer"),
                            new Amiibo("Nurse Puff", "Jigglypuff"),
                            new Amiibo("Hank Pym", "Olimar"),
                            new Amiibo("PapíMars", "Mario"),
                            new Amiibo("Legolas", "Toon Link"),
                            new Amiibo("Lt. Pigeon", "Captain Falcon"),
                            new Amiibo("Wawa Melon", "Yoshi"),
                            new Amiibo("Daddy", "Snake")};
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
        NULL String values are converted into -1.
        */
        
        String dataRaw = "NULL	0	0	0	0	3	0	0	1	0	1	0	0	1	1\n" +
                        "0	NULL	0	0	1	0	0	0	0	0	0	1	0	0	1\n" +
                        "1	0	NULL	0	0	0	1	0	0	1	1	0	1	1	1\n" +
                        "1	0	0	NULL	0	0	0	0	1	0	0	1	1	1	1\n" +
                        "0	0	0	0	NULL	0	0	0	0	0	1	0	0	0	0\n" +
                        "0	1	0	0	0	NULL	0	0	0	0	1	0	0	0	0\n" +
                        "0	0	0	0	0	0	NULL	1	1	0	1	0	0	0	1\n" +
                        "0	0	0	0	1	0	0	NULL	1	0	2	0	1	1	1\n" +
                        "0	0	0	0	0	0	0	0	NULL	0	0	0	1	0	0\n" +
                        "1	0	1	0	1	0	0	0	0	NULL	2	0	0	1	0\n" +
                        "0	1	0	1	1	0	1	0	1	0	NULL	1	1	0	1\n" +
                        "0	0	0	0	0	0	0	0	0	0	1	NULL	0	0	1\n" +
                        "1	3	1	0	3	1	1	0	1	2	2	1	NULL	2	1\n" +
                        "0	1	1	0	1	2	1	1	1	0	1	1	0	NULL	1\n" +
                        "0	0	0	0	1	1	0	0	1	1	0	0	0	0	NULL";
        
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
    
    public static int[] loadPastMatches(Amiibo amiibo) {
        
        int[] retVal = new int[arr.size()];
        final int ID = amiibo.getID();
        
        for (int j = 0; j < arr.size(); j++) {
            if (ID == j) {
                retVal[j] = -1;
            } else {
                retVal[j] += (spreadsheetData[ID][j] != -1 ? spreadsheetData[ID][j] : 0);
                retVal[j] += (spreadsheetData[j][ID] != -1 ? spreadsheetData[j][ID] : 0);
            }
        }
        
        return retVal;
    }

    public static void generateMatch(int n) {
        
        System.out.println("\nGenerating " + n + " matches...\n\n");
        
        /*
        For loop generates n number of matches.
        */
        
        for (int x = 0; x < n; x++) {
        
            int[] chances = new int[arr.size()];
            Random rand = new Random();
            Amiibo fighter1, fighter2;

            // For loop gets the average of all Amiibo's matchesNumbers
            double average = 0.0;
            for (int i = 0; i < arr.size(); i++) {
                average += (double) arr.get(i).getMatchesNumber();
            }
            average = average / (double) arr.size();

            /*
            For loop generates the initial chances for each Amiibo to be chosen.
            */

            for (int i = 0; i < arr.size(); i++) {
                chances[i] = generateFirstFighterChance(average, i, rand);
            }

            fighter1 = generateFirstFighter(arr.size(), chances, rand);
            
            for (int i = 0; i < arr.size(); i++) {
                chances[i] = generateSecondFighterChance(fighter1, i, rand);
            }
            
            fighter2 = generateSecondFighter(fighter1, arr.size(), chances, rand);
            System.out.println(fighter1.getName() + " vs " + fighter2.getName() + "\n");
        
        }
        
    }

    public static int generateFirstFighterChance(double average, int ID, Random rand) {
        
        /*
        ID Values
        0 = Papa Smurf, 1 = Hubris, 2 = PhDeezNuts, 3 = Red-Gojira,
        4 = Capitalism, 5 = El Diablo, 6 = B∞ty, 7 = 2Fit2Quit,
        8 = Nurse Puff, 9 = Hank Pym, 10 = PapíMars, 11 = Legolas,
        12 = Lt. Pigeon, 13 = Wawa Melon
        */
        
        double retVal = 0.5;
        int matchesNumber = arr.get(ID).getMatchesNumber();
        
        /*
        If the Amiibo's matchNumber is doubly larger or smaller than average,
        then divide or multiply retVal by 2 respectively. Otherwise, keep
        retVal at 0.5.
        */
        
        if (matchesNumber > average) {
            if (matchesNumber / 2 > average)
                retVal /= 2.0;
        } else {
            if (matchesNumber * 2 <= average)
                retVal *= 1.5;
        }
        
        // Adds or subtracts a random amount to retVal to spice up the chances.
        if (rand.nextBoolean()) {
            retVal += rand.nextDouble();
        } else {
            retVal -= rand.nextDouble();
        }
        
        // Makes sure retVal stays between 0.0 and 1.0.
        if (retVal > 1.0)
            retVal = 1.0;
        else if (retVal < 0.0)
            retVal = 0.0;
        
        return (int) Math.ceil(retVal * 100);
    }
    
    public static int generateSecondFighterChance(Amiibo fighter1, int ID, Random rand) {
        
        /*
        ID Values
        0 = Papa Smurf, 1 = Hubris, 2 = PhDeezNuts, 3 = Red-Gojira,
        4 = Capitalism, 5 = El Diablo, 6 = B∞ty, 7 = 2Fit2Quit,
        8 = Nurse Puff, 9 = Hank Pym, 10 = PapíMars, 11 = Legolas,
        12 = Lt. Pigeon, 13 = Wawa Melon
        */
        
        int[] previousMatches = fighter1.getNumberOfMatchesAgainstAmiibo();
        
        if (fighter1.getID() == ID)
            return 0;
        
        double retVal = 0.5;
        
        /*
        If the Amiibo's matchNumber is doubly larger or smaller than average,
        then divide or multiply retVal by 2 respectively. Otherwise, keep
        retVal at 0.5.
        */
        
        double average = 0.0;
        for (int i = 0; i < previousMatches.length; i++) {
            average += previousMatches[i];
        }
        average /= previousMatches.length;
        
        int freq = arr.get(fighter1.getID()).getNumberOfMatchesAgainstAmiibo()[ID];
        if (freq > average) {
            if (freq / 2 > average)
                retVal /= 2.0;
        } else {
            if (freq * 2 <= average)
                retVal *= 1.5;
        }
        
        // Adds or subtracts a random amount to retVal to spice up the chances.
        if (rand.nextBoolean()) {
            retVal += rand.nextDouble();
        } else {
            retVal -= rand.nextDouble();
        }
        
        // Makes sure retVal stays between 0.0 and 1.0.
        if (retVal > 1.0)
            retVal = 1.0;
        if (retVal < 0.0)
            retVal = 0.0;
        
        return (int) Math.ceil(retVal * 100);
    }
    
    public static Amiibo generateFirstFighter(int n, int[] chances, Random rand) {
        /*
        The following code adds up all of the chances, multiplies them by 100,
        and gets the highest integer value from each product. Then, the second
        for loop runs through and subtracts each Amiibo's chance until the sum
        equals the random chosen number (decision). If sum is less than or equal
        to decision, it chooses the fighter that made that conditional statement
        true, gets that Amiibo's name from arr, and sets fighter1 to that name.
        */
        
        Amiibo retVal = arr.get(0);
        int decision, originalSum, sum = 0;
        
        for (int i = 0; i < n; i++) {
            sum += chances[i];
        }
        originalSum = sum;
        
        try {
            // Try block runs if sum > 0
            decision = rand.nextInt(sum);

            if (originalSum - (originalSum - chances[chances.length - 1]) >= decision) {
                retVal = arr.get(chances.length - 1);
                return retVal;
            }

            for (int i = 0; i < n; i++) {
                if (sum <= decision) {
                    if (i == 0) {
                        retVal = arr.get(i);
                        break;
                    } else {
                        retVal = arr.get(i - 1);
                        break;
                    }
                } else {
                    sum -= chances[i];
                }
            }
        } catch (IllegalArgumentException e) {
            // Catch block only runs if sum = 0
            retVal = arr.get(rand.nextInt(arr.size() - 1));
        }
        
        
        return retVal;
    }
    
    public static Amiibo generateSecondFighter(Amiibo firstFighter, int n, int[] chances, Random rand) {
        
        Amiibo retVal = arr.get(0);
        int decision, originalSum, sum = 0;
        
        for (int i = 0; i < n; i++) {
            sum += chances[i];
        }
        originalSum = sum;
        decision = rand.nextInt(sum);

        if (originalSum - (originalSum - chances[chances.length - 1]) >= decision) {
            retVal = arr.get(chances.length - 1);
            return retVal;
        }

        for (int i = 0; i < n; i++) {
            if (sum <= decision) {
                if (i == 0) {
                    retVal = arr.get(i);
                    break;
                } else {
                    retVal = arr.get(i - 1);
                    break;
                }
            } else {
                sum -= chances[i];
            }
        }
        
        return retVal;
    }
    
    public static void clearCMD() {
        
        /*
        Simple helper method for clearing the Command Prompt if the game
        is being played there. Currently only works for Windows.
        */
        
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            Logger.getLogger(AmiiboMatchMaker.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}