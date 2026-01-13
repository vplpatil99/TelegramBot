package com.gkbvision.TelegramBot;

import java.util.HashMap;
import java.util.Map;

public class LocationResolver {

    private static final Map<String, String> CodeToLocation = new HashMap<>();

    static {
        CodeToLocation.put("002", "Delhi");
        CodeToLocation.put("003", "Mumbai");
        CodeToLocation.put("005", "Kolkata");
        CodeToLocation.put("007", "Chennai");
        CodeToLocation.put("009", "Ahemdabad");
        CodeToLocation.put("012", "Hyderabad");
        CodeToLocation.put("013", "Nagpur");
        CodeToLocation.put("014", "Cochin");
        CodeToLocation.put("017", "Bangalore");
        CodeToLocation.put("020", "Kolhapur");
        CodeToLocation.put("022", "Madurai");
        CodeToLocation.put("023", "Aurangabad");
        CodeToLocation.put("024", "Lucknow");
        CodeToLocation.put("026", "Ludhiana");
        CodeToLocation.put("027", "Jaipur");
    }

    public static String getLocationFromLabCode(String labcode) {
        if (labcode == null )
            return null;
//        String prefix = labcode.substring(0, 3);
        return CodeToLocation.get(labcode);
    }
}

