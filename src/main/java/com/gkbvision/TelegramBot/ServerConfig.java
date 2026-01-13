package com.gkbvision.TelegramBot;

import java.util.HashMap;
import java.util.Map;

public class ServerConfig {

    public static final Map<String, LocalSqlServer> ServerMap = new HashMap<>();

    static {
        ServerMap.put("Mumbai", new LocalSqlServer("jdbc:sqlserver://10.208.183.20;databaseName=prime_003_new;encrypt=false;trustServerCertificate=true", "sa", "sachin@123"));
        ServerMap.put("Ahemdabad", new LocalSqlServer("jdbc:sqlserver://10.208.177.209\\primeserver;databaseName=prime_009;encrypt=false;trustServerCertificate=true","sa", ""));
        ServerMap.put("Aurangabad", new LocalSqlServer("jdbc:sqlserver://10.208.177.151\\primeserver;databaseName=prime_023;encrypt=false;trustServerCertificate=true","sa", ""));
        ServerMap.put("Nagpur", new LocalSqlServer("jdbc:sqlserver://10.208.177.24\\primeserver;databaseName=prime_013;encrypt=false;trustServerCertificate=true","sa", ""));
        ServerMap.put("Kolhapur", new LocalSqlServer("jdbc:sqlserver://10.208.183.20;databaseName=prime_020;encrypt=false;trustServerCertificate=true","sa", "sachin@123"));
        ServerMap.put("Chennai", new LocalSqlServer("jdbc:sqlserver://10.208.178.181\\primeserver;databaseName=prime_007;encrypt=false;trustServerCertificate=true","sa", ""));
        ServerMap.put("Madurai", new LocalSqlServer("jdbc:sqlserver://10.208.178.201\\primeserver;databaseName=prime_022;encrypt=false;trustServerCertificate=true","sa", ""));
        ServerMap.put("Kolkata", new LocalSqlServer("jdbc:sqlserver://10.208.179.148;databaseName=prime_005;encrypt=false;trustServerCertificate=true","sa", ""));
        ServerMap.put("Bangalore", new LocalSqlServer("jdbc:sqlserver://10.208.183.20;databaseName=prime_017;encrypt=false;trustServerCertificate=true","sa", "sachin@123"));
        ServerMap.put("Cochin", new LocalSqlServer("jdbc:sqlserver://10.208.179.91\\sqlexpress02;databaseName=prime_014;encrypt=false;trustServerCertificate=true","sa", ""));
        ServerMap.put("Delhi", new LocalSqlServer("jdbc:sqlserver://10.208.183.20;databaseName=prime_002;encrypt=false;trustServerCertificate=true","sa", "sachin@123"));
        ServerMap.put("Lucknow", new LocalSqlServer("jdbc:sqlserver://10.208.180.91\\primeserver;databaseName=prime_024;encrypt=false;trustServerCertificate=true","sa", ""));
        ServerMap.put("Hyderabad", new LocalSqlServer("jdbc:sqlserver://10.208.179.23\\primeserver;databaseName=prime_012;encrypt=false;trustServerCertificate=true","sa", ""));
        ServerMap.put("Jaipur", new LocalSqlServer("jdbc:sqlserver://10.208.180.151\\primeserver;databaseName=prime_027;encrypt=false;trustServerCertificate=true","sa", ""));
        ServerMap.put("Ludhiana", new LocalSqlServer("jdbc:sqlserver://10.208.180.22\\primeserver;databaseName=prime_026;encrypt=false;trustServerCertificate=true","sa", ""));
    }

    public static LocalSqlServer getServerForLabCode(String labcode) {
        String location = LocationResolver.getLocationFromLabCode(labcode);
        if (location == null) {
            System.err.println("⚠️ Unknown location for L_OrderNo: " + labcode);
            return null;
        }
        return ServerMap.get(location);
    }
}
