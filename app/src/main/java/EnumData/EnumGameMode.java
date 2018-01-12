package EnumData;

/**
 * Created by panpanr on 7/16/2015.
 */
public class EnumGameMode {
    public String GameMode;
    public EnumGameMode(String value) {
        switch (value) {
            case "0":
                GameMode = "Unknown";
                break;
            case "1":
                GameMode = "All Pick";
                break;
            case "2":
                GameMode = "Captains Mode";
                break;
            case "3":
                GameMode = "Random Draft";
                break;
            case "4":
                GameMode = "Single Draft";
                break;
            case "5":
                GameMode = "All Random";
                break;
            case "7":
                GameMode = "The Diretide";
                break;
            case "8":
                GameMode = "Reverse Captains Mode";
                break;
            case "9":
                GameMode = "Greeviling";
                break;
            case "10":
                GameMode = "Greeviling";
                break;
            case "11":
                GameMode = "Mid Only";
                break;
            case "12":
                GameMode = "Least Played";
                break;
            case "13":
                GameMode = "New Player Pool";
                break;
            case "14":
                GameMode = "Compendium Matchmaking";
                break;
            case "15":
                GameMode = "Custom";
                break;
            case "16":
                GameMode = "Captains Draft";
                break;
            case "17":
                GameMode = "Balanced Draft";
                break;
            case "18":
                GameMode = "Ability Draft";
                break;
            case "20":
                GameMode = "All Random Death Match";
                break;
            case "21":
                GameMode = "1vs1 Solo Mid";
                break;
        }
    }
}
