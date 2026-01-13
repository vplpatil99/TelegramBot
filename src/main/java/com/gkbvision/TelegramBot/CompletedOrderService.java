package com.gkbvision.TelegramBot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompletedOrderService {
	
	
	public static String getCompletedOrderMessages(String labcode,String Rx_cust_acc) {
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
          
          

          StringBuilder result = new StringBuilder();
        



        String query ="select  ROW_NUMBER() OVER (ORDER BY om.orderEntryTime desc) AS RowNum,om.RegisterNo,om.l_orderno,case "
        		+ "when status='NC' and processed='N' then 'Order Entered' "
        		+ "when status='NC' and processed='Y' then 'In Process' "
        		+ "when status='C' and processed='Y' and  ChallanNo is  null  then 'Completed' "
        		+ "when status='C' and processed='Y' and ChallanNo is not null then 'Dispatched' "
        		+ "else '' end as status from ordermaster om inner join originalorder oo on om.g_orderno=oo.g_orderno inner join partydetails pd on om.Party_Code=pd.PARTY_CODE left join CancelledOrders co on om.g_orderno=co.g_orderno  where co.g_orderno is null and om.status='C' and challanno is null and pd.RX_CUST_ACC='"+ Rx_cust_acc +"' and  om.order_date>=CAST(GETDATE() - 1 AS date) order by om.orderEntryTime desc";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, Rx_cust_acc);

            try (ResultSet rs = stmt.executeQuery()) {
//            	if(rs.next())
            	result.append("<b>🔹 Recent Orders:</b>\n");
                while (rs.next()) {
                	String RowNum = rs.getString("RowNum");
                	String RegisterNo =  rs.getString("RegisterNo");
                    String l_orderno = rs.getString("l_orderno");
                    String status = rs.getString("status");

                    
                    result.append( RowNum+"./"+l_orderno+" — "+RegisterNo+" — "+status+"\n" );

                }
            }

        } catch (Exception e) {
            e.printStackTrace();

            
        }
        if(result.toString()=="<b>🔹 Recent Orders:</b>\n")
        {
        	result.append("Not yet Inititalized");
        }

        return result.toString();
    }

}
