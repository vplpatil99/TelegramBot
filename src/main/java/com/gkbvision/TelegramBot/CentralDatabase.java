package com.gkbvision.TelegramBot;

import java.sql.*;

public class CentralDatabase {

    private static final String URL = "jdbc:sqlserver://10.208.183.20;databaseName=db_mumbai175;encrypt=false;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "sachin@123";

    // ✅ Get Connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    // ✅ Insert Data
    public static void insertData(String RX_CUST_ACC,long chatID) {
        String query = "INSERT INTO Telegrambot (RX_CUST_ACC,city_id,chatID,UpdateDateTime) select RX_CUST_ACC,RIGHT('000' + CAST(city_id AS VARCHAR(3)), 3) as city_id,? chatID,CURRENT_TIMESTAMP as UpdateDateTime  from partydetails  where rx_cust_acc=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
        	
        	ps.setLong(1, chatID);
            ps.setString(2, RX_CUST_ACC);
            
             ps.executeUpdate();
//            System.out.println(rows + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Update Data
    public static void updateData(long chatID, String RX_CUST_ACC) {
        String query = "UPDATE Telegrambot SET rx_cust_acc=? WHERE chatID = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, RX_CUST_ACC);
        	ps.setLong(2, chatID);


            ps.executeUpdate();
//            System.out.println(rows + " row(s) updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // ✅ Read Data
    public static boolean isCustomerCodeValid(String RX_CUST_ACC) {
        String query = "SELECT * FROM partydetails where rx_cust_acc='"+ RX_CUST_ACC +"'"; // adjust as needed
        boolean result=false;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if(rs.next()) {
            	result=true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    
    // ✅ Read Data
    public static boolean doesMappingExist(long chatid) {
        String query = "SELECT * FROM Telegrambot where chatid="+ chatid +""; // adjust as needed
        boolean result=false;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if(rs.next()) {

            	result=true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    // ✅ Read Data
    public static String getCustomerCode(long chatid) {
    	String rx_cust_acc = "";
        String query = "SELECT * FROM Telegrambot where chatid="+ chatid +""; // adjust as needed
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if(rs.next()) {
               rx_cust_acc = rs.getString("rx_cust_acc");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rx_cust_acc;
    }
    
    public static boolean deleteCustomerByChatId(long chatid) {
        String query = "DELETE FROM Telegrambot WHERE chatid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, chatid);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // return true if deleted

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static String getLabCode(long chatid) {
    	String LabCode = "";
        String query = "SELECT * FROM Telegrambot where chatid="+ chatid +""; // adjust as needed
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if(rs.next()) {
            	LabCode = rs.getString("city_id");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return LabCode;
    }
    public static String getPartyName(long chatid) {
    	String PartyName = "";
        String query = "SELECT party_name FROM Telegrambot a inner join partydetails b on a.rx_cust_acc=b.rx_cust_acc  where chatid="+ chatid +""; // adjust as needed
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if(rs.next()) {
            	PartyName = rs.getString("party_name");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PartyName;
    }
    
    public static String getProfileMessage(long chatid) {
    	String profileMessage = "";
//        String query = "SELECT party_name FROM Telegrambot a inner join partydetails b on a.rx_cust_acc=b.rx_cust_acc  where chatid="+ chatid +""; // adjust as needed
        
     // Example: get connection & execute query
        String query = "SELECT pd.RX_CUST_ACC AS customerId, " +
                       "pd.party_name AS Store, isnull(pd.mobilemailaddress,0) AS mobile, " +
                       "ISNULL(pd.email,'') AS email, pd.ADDRESS, " +
                       "MName AS SalesCordinator, mp.MobileNo AS salesCordinatorContact " +
                       "FROM partydetails pd " +
                       "INNER JOIN M_MarketingPersonnel mp ON MarketingPerson = MName " +
                       "INNER JOIN Telegrambot tb ON tb.rx_cust_acc = pd.rx_cust_acc " +
                       "WHERE chatid = " + chatid;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if(rs.next()) {
                String customerId = rs.getString("customerId");
                String store = rs.getString("Store");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                String address = rs.getString("ADDRESS");
                String salesCordinator = rs.getString("SalesCordinator");
                String salesContact = rs.getString("salesCordinatorContact");
                
                // ✅ Build formatted profile message

               return profileMessage =
                	    "<b>👤 Your Profile Details</b>\n\n" +
                	    "🧾 <b>Customer ID:</b> <code>" + customerId + "</code>\n" +
                	    "🏬 <b>Store:</b> " + store + "\n" +
                	    "📞 <b>Mobile:</b> <a href='tel:" + mobile + "'>" + mobile + "</a>\n" +
                	    "📧 <b>Email:</b><a href='mailto:" + email + "'>" + email + "</a>\n" +
                	    "🏠 <b>Address:</b> " + address + "\n" +
                	    "🤝 <b>Sales Coordinator:</b> " + salesCordinator + "\n" +
                	    "📱 <b>Contact:</b> <a href='tel:" + salesContact + "'>" + salesContact + "</a>\n\n" +
                	    "💙 Thank you for being a valued customer!";

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profileMessage;
    }
    public static boolean validateUser(String username, String password) {
	    String query = "SELECT PasswordHash FROM TelegrambotUsers WHERE Username = ?";
	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setString(1, username);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            String storedHash = rs.getString("PasswordHash");
	            return storedHash.equals(password);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
    public static void updatePassword(String username, String newPassword) {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE TelegrambotUsers SET PasswordHash = ? WHERE Username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
