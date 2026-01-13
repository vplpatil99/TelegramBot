package com.gkbvision.TelegramBot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DispatchOrderService {



    /**
     * Fetch stage details for the given L_OrderNo
     * @param lOrderNo The L_OrderNo to search for
     * @return String message containing StageName and CompletionTime
     */
//    public List<String> getDispatchOrderMessages(String labcode,String Rx_cust_acc) {
//    	List<String> messages = new ArrayList<>();
//    	LocalSqlServer obj=ServerConfig.getServerForLabCode(labcode);
//    	if(obj==null) {
//    		return messages;
////    		return "No records found for Rx_cust_acc: " + Rx_cust_acc;
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
//        StringBuilder result = new StringBuilder();
//        
//
//
//
//        String query ="select top 10 ROW_NUMBER() OVER (ORDER BY om.orderEntryTime desc) AS RowNum,om.l_orderno,om.RegisterNo,om.order_no,cast(om.order_date as date) as order_date,case "
//        		+ "when status='NC' and processed='N' then 'Order Entered' "
//        		+ "when status='NC' and processed='Y' then 'In Process' "
//        		+ "when status='C' and processed='Y' and ChallanNo is not null then 'Ready for Delivery' "
//        		+ "else '' end as status,oo.lens_type,oo.coatcolor,oo.RSph,oo.RCyl,oo.RAxis,oo.RAddn,isnull(oo.Rqty,0) as Rqty,oo.LSph,oo.LCyl,oo.LAxis,oo.LAddn,isnull(oo.Lqty,0) as Lqty from ordermaster om inner join originalorder oo on om.g_orderno=oo.g_orderno inner join partydetails pd on om.Party_Code=pd.PARTY_CODE left join CancelledOrders co on om.g_orderno=co.g_orderno  where co.g_orderno is null and pd.RX_CUST_ACC='"+ Rx_cust_acc +"' and year(om.challanDate)=year(getdate()) and month(om.challanDate)=month(getdate()) order by om.orderEntryTime desc";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//             PreparedStatement stmt = conn.prepareStatement(query)) {
////            stmt.setString(1, Rx_cust_acc);
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                	String RowNum = rs.getString("RowNum");
//                    String l_orderno = rs.getString("l_orderno");
//                    String order_no = rs.getString("order_no");
//                    String RegisterNo = rs.getString("RegisterNo");
//                    String order_date = rs.getString("order_date");
//                    String status =rs.getString("status");
//                    
//                    String lens_type =rs.getString("lens_type");
//                    String coatcolor =rs.getString("coatcolor");
//                    String RSph =rs.getString("RSph");
//                    String RCyl =rs.getString("RCyl");
//                    String RAxis =rs.getString("RAxis");
//                    String RAddn =rs.getString("RAddn");
//                    int Rqty =rs.getInt("Rqty");
//                    
//                    String LSph =rs.getString("LSph");
//                    String LCyl =rs.getString("LCyl");
//                    String LAxis =rs.getString("LAxis");
//                    String LAddn =rs.getString("LAddn");
//                    int Lqty =rs.getInt("Lqty");
//                    
//                    result.append(RowNum+" Tracking No: /" + l_orderno + "\n🛒 Order No: "+order_no+" \n💬 Remarks: " + RegisterNo + "\n🕒 Status: "+status+" \n📅 Date: " + order_date + "\n\n" );
//                    result.append("👓 Power Details:\n");
//                    result.append("("+lens_type+"-"+coatcolor+")\n");
//                    if(Rqty>0) {
//                    result.append("R: S"+RSph+"|C"+RCyl+"|A"+RAxis+"|A"+RAddn+"\n");
//                    }
//                    if(Lqty>0) {
//                    result.append("L: S"+LSph+"|C"+LCyl+"|A"+LAxis+"|A"+LAddn+"\n");
//                    }
////                    result.append("______________________________");
//                    result.append("\n\n");
//                    messages.add(result.toString());
//                    result.setLength(0);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            
//        }
//
//
//        return messages;
//    }
	
	public static String getDispatchOrderMessages(String labcode,String Rx_cust_acc) {
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
        		+ "else '' end as status from ordermaster om inner join originalorder oo on om.g_orderno=oo.g_orderno inner join partydetails pd on om.Party_Code=pd.PARTY_CODE left join CancelledOrders co on om.g_orderno=co.g_orderno  where co.g_orderno is null and challanno is not null and pd.RX_CUST_ACC='"+ Rx_cust_acc +"' and  om.order_date>=CAST(GETDATE() - 1 AS date) order by om.orderEntryTime desc";

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