package xyz.hazardbot.utility;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import xyz.hazardbot.HazardBot;
import xyz.hazardbot.constants.SpecialUserID;
import xyz.hazardbot.utility.yaml.file.YamlConfiguration;

public class UserUtil {
    public static YamlConfiguration loadUser(long serverID, long userID) {
        try {
            File file = new File("data/" + serverID + "/" + userID + ".yml");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            
            YamlConfiguration config = new YamlConfiguration();
            config.load(file);
            return config;
        } catch (Throwable ex) {
            String error = "Failed to load user data for '" + serverID + ":" + userID + "'.";
            Util.print(error);
            ex.printStackTrace();
            return new YamlConfiguration();
        }
    }
    
    public static void saveUser(long serverID, long userID, YamlConfiguration config) {
        try {
            File file = new File("data/" + serverID + "/" + userID + ".yml");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            
            config.save(file);
        } catch (Throwable ex) {
            String error = "Failed to save user data for '" + serverID + ":" + userID + "'.";
            Util.print(error);
            ex.printStackTrace();
        }
    }
    
    public static final int getBotCreatorLevel(long userID) {
        String idAsString = Long.toString(userID);
        List<String> ids = SpecialUserID.getAllBotCreators();
        return (ids.contains(idAsString) ? 10 : 0);
    }
    
    public static final int getAccessLevel(long serverID, long userID) {
        DiscordApi dapi = HazardBot.API;
        Optional<Server> oserver = dapi.getServerById(serverID);
        if (oserver.isPresent()) {
            Server server = oserver.get();
            User serverOwner = server.getOwner();
            long ownerID = serverOwner.getId();
            if (ownerID == userID) return 8;
            else {
                YamlConfiguration config = loadUser(serverID, userID);
                if (config.isSet("access level")) {
                    int accessLevel = config.getInt("access level");
                    return accessLevel;
                } else {
                    config.set("access level", 0);
                    saveUser(serverID, userID, config);
                    return 0;
                }
            }
        } else return 0;
    }
}