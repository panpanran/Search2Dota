package EnumData;

/**
 * Created by panpanr on 7/16/2015.
 */
public class EnumLobbyType {
    public String LobbyType;
    public EnumLobbyType(String value) {
        switch (value) {
            case "-1":
                LobbyType = "Invalid";
                break;
            case "0":
                LobbyType = "Public matchmaking";
                break;
            case "1":
                LobbyType = "Practice";
                break;
            case "2":
                LobbyType = "Tournament";
                break;
            case "3":
                LobbyType = "Tutorial";
                break;
            case "4":
                LobbyType = "Co-op with bots";
                break;
            case "5":
                LobbyType = "Team match";
                break;
            case "6":
                LobbyType = "Solo Queue";
                break;
            case "7":
                LobbyType = "Ranked";
                break;
            case "8":
                LobbyType = "Solo Mid 1vs1";
                break;
        }
    }
}
