package xyz.hazardbot.constants;

import java.util.List;
import java.util.Map;

import xyz.hazardbot.utility.Util;

public class SpecialUserID {
    public static final String HZD_NICKY = "254820207829319681", SIRBLOBMAN = "252285975814864898",
            CONNOR = "303687748441604096";
    
    public static final List<String> getAllBotCreators() {
        List<String> list = Util.newList(HZD_NICKY, SIRBLOBMAN);
        return list;
    }
    
    public static final Map<String, String> getSpecialUserDescriptions() {
        Map<String, String> map = Util.newMap();
        map.put(HZD_NICKY, "Bot Creator");
        map.put(SIRBLOBMAN, "Bot Creator");
        map.put(CONNOR, "Friend of HZD Nicky");
        return map;
    }
}