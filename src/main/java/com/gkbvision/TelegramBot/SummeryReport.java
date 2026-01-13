package com.gkbvision.TelegramBot;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SummeryReport {



    /**
     * Fetch stage details for the given L_OrderNo
     * @param lOrderNo The L_OrderNo to search for
     * @return String message containing StageName and CompletionTime
     */
    public static String getSummeryReportMessages(String labcode,String Rx_cust_acc) {
//    	String messages = new ArrayList<>();
    	LocalSqlServer obj=ServerConfig.getServerForLabCode(labcode);
    	if(obj==null) {
//    		return messages;
    		return "No records found for Rx_cust_acc: " + Rx_cust_acc;
    	}
    	
        // ✅ Update your database connection info
    	
          String DB_URL = obj.getJdbcUrl();
          String DB_USER = obj.getUser();
          String DB_PASS = obj.getPassword();
          
          

          String todaySummaryMessage="";
        



        String query ="select pd.PARTY_NAME,pd.RX_CUST_ACC,count(om.l_orderNo) as totalOrder,"
        		+ "sum(case when status='NC' and processed='Y' then 1 else 0 end) as InProcess,"
        		+ "sum(case when status='C' and processed='Y' and ChallanNo is null then 1 else 0 end) as Completed,"
        		+ "sum(case when status='C' and processed='Y' and ChallanNo is not null then 1 else 0 end) as Dispatched"
        		+ " from ordermaster om inner join originalorder oo on om.g_orderno=oo.g_orderno inner join partydetails pd on om.Party_Code=pd.PARTY_CODE left join CancelledOrders co on om.g_orderno=co.g_orderno  where co.g_orderno is null and pd.RX_CUST_ACC='"+ Rx_cust_acc +"' and  om.order_date>=CAST(GETDATE() - 1 AS date) "
        		+ " group by pd.PARTY_NAME,pd.RX_CUST_ACC";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, Rx_cust_acc);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	String PARTY_NAME = rs.getString("PARTY_NAME");
                    String RX_CUST_ACC = rs.getString("RX_CUST_ACC");
//                    String order_date = rs.getString("order_date");
                    String totalOrder = rs.getString("totalOrder");
                    String InProcess = rs.getString("InProcess");
                    String Completed = rs.getString("Completed");
                    String Dispatched =rs.getString("Dispatched");

                    
                    todaySummaryMessage =
                    	    "<b>🗓️ Summary Since Yesterday</b>\n\n" +
                    	    "🏬 <b>Store:</b> "+PARTY_NAME+" (Code: <code>"+RX_CUST_ACC+"</code>)\n\n" +
                    	    "🧾  <b>Total Orders:</b> "+totalOrder+"\n" +
                    	    "🔧 <b>In Process:</b> "+InProcess+"\n" +
                    	    "✅ <b>Completed:</b> "+Completed+"\n"+
                    	    "📦 <b>Dispatched:</b> "+Dispatched;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();

            
        }
        if(todaySummaryMessage=="")
        {
        	todaySummaryMessage="Not yet Inititalized";
        }

        return todaySummaryMessage;
    }
    
    
//    public static String getTodayOrderSummeryMessages(String labcode,String Rx_cust_acc) {
////    	String messages = new ArrayList<>();
//    	LocalSqlServer obj=ServerConfig.getServerForLabCode(labcode);
//    	if(obj==null) {
////    		return messages;
//    		return "No records found for Rx_cust_acc: " + Rx_cust_acc;
//    	}
//    	
//        // ✅ Update your database connection info
//    	
//          String DB_URL = obj.getJdbcUrl();
//          String DB_USER = obj.getUser();
//          String DB_PASS = obj.getPassword();
//          
//          
//
//          StringBuilder result = new StringBuilder();
//        
//
//
//
//        String query ="select  ROW_NUMBER() OVER (ORDER BY om.orderEntryTime desc) AS RowNum,om.RegisterNo,om.l_orderno,case "
//        		+ "when status='NC' and processed='N' then 'Order Entered' "
//        		+ "when status='NC' and processed='Y' then 'In Process' "
//        		+ "when status='C' and processed='Y' and ChallanNo is not null then 'Dispatched' "
//        		+ "else '' end as status from ordermaster om inner join originalorder oo on om.g_orderno=oo.g_orderno inner join partydetails pd on om.Party_Code=pd.PARTY_CODE left join CancelledOrders co on om.g_orderno=co.g_orderno  where co.g_orderno is null and pd.RX_CUST_ACC='"+ Rx_cust_acc +"' and  om.order_date=CAST(GETDATE() - 1 AS date) order by om.orderEntryTime desc";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//             PreparedStatement stmt = conn.prepareStatement(query)) {
////            stmt.setString(1, Rx_cust_acc);
//
//            try (ResultSet rs = stmt.executeQuery()) {
////            	if(rs.next())
//            	result.append("<b>🔹 Recent Orders:</b>\n");
//                while (rs.next()) {
//                	String RowNum = rs.getString("RowNum");
//                	String RegisterNo =  rs.getString("RegisterNo");
//                    String l_orderno = rs.getString("l_orderno");
//                    String status = rs.getString("status");
//
//                    
//                    result.append( RowNum+"./"+l_orderno+" — "+RegisterNo+" — "+status+"\n" );
//
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            
//        }
//        if(result.toString()=="<b>🔹 Recent Orders:</b>\n")
//        {
//        	result.append("Not yet Inititalized");
//        }
//
//        return result.toString();
//    }

}