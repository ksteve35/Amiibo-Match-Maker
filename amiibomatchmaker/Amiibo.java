package amiibomatchmaker;

public class Amiibo implements Comparable<Amiibo> {
    
    private String name;
    private final String character;
    private int matchesNumber, totalWins, totalLosses;
    private final int ID;
    private double winRate;
    
    // Constructor
    public Amiibo(String n, String c, int w, int l) {
        name = new String(n);
        character = new String(c);
        matchesNumber = w + l;
        totalWins = w;
        totalLosses = l;
        winRate = calculateWinRate();
        ID = generateID(name);
    }
    
    // Copy Constructor
    public Amiibo(Amiibo a) {
        name = new String(a.name);
        character = new String(a.character);
        totalWins = a.totalWins;
        totalLosses = a.totalLosses;
        matchesNumber = totalWins + totalLosses;
        winRate = calculateWinRate();
        ID = generateID(name);
    }
    
    public String getName() {
        return name;
    }
    
    public int getID() {
        return ID;
    }
    
    private int generateID(String s) {
        int id = -1;
        switch (s) {
            case "Papa Smurf":
                id = 0;
                break;
            case "Hubris":
                id = 1;
                break;
            case "PhDeezNuts":
                id = 2;
                break;
            case "Red-Gojira":
                id = 3;
                break;
            case "Capitalism":
                id = 4;
                break;
            case "El Diablo":
                id = 5;
                break;
            case "B∞ty":
                id = 6;
                break;
            case "2Fit2Quit":
                id = 7;
                break;
            case "Nurse Puff":
                id = 8;
                break;
            case "Hank Pym":
                id = 9;
                break;
            case "PapíMars":
                id = 10;
                break;
            case "Legolas":
                id = 11;
                break;
            case "Lt. Pigeon":
                id = 12;
                break;
            case "Wawa Melon":
                id = 13;
                break;
            default:
                id = -1;
                break;
        }
        
        return id;
    }
    
    private double calculateWinRate() {
        
        /*
        winRate is doing (10000 * totalWins / matchesNumber) / 100 instead of
        (100 * totalWins / matchesNumber) because Math.floor changes the data otherwise.
        For example, if totalWins = 21 and matchesNumber = 26, the original method would
        make winRate = 80.0%, while this new version makes it correct at 80.76%.
        */
        
        return Math.floor(10000 * (double) totalWins / (double) matchesNumber) / 100;
    }

    @Override
    public int compareTo(Amiibo a) {
        if (winRate < a.winRate)
            return 1;
        else
            return -1;
    }
    
    public String printAmiiboData() {
        return "Name:\t\t" + name + "\nCharacter:\t" + character + "\n# Matches:\t"
                + matchesNumber + "\nWins:\t\t" + totalWins + "\nLosses:\t\t" + totalLosses
                + "\nWin Rate:\t" + winRate + "%\nID #:\t\t" + ID;
    }
    
}