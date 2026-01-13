package com.gkbvision.TelegramBot;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StageService {



    /**
     * Fetch stage details for the given L_OrderNo
     * @param lOrderNo The L_OrderNo to search for
     * @return String message containing StageName and CompletionTime
     */
    public String getStageMessages(String lOrderNo) {
//    	  new ServerConfig();
    	String labcode = lOrderNo.substring(0, 3);
    	LocalSqlServer obj=ServerConfig.getServerForLabCode(labcode);
    	if(obj==null) {
    		return "No records found for L_OrderNo: " + lOrderNo;
    	}
    	
        // ✅ Update your database connection info
    	
          String DB_URL = obj.getJdbcUrl();
          String DB_USER = obj.getUser();
          String DB_PASS = obj.getPassword();
          
          
    	
        StringBuilder result = new StringBuilder();
        

        
        String query = "with obj as(SELECT case when sc.StageName like 'Order Entered%' then 'Order Entered' "
        		+ "when sc.StageName like 'Blank Removed%' or sc.StageName like 'Lens Removed%' then 'In Process' "
        		+ "when sc.StageName like 'Checking%'  and om.Status='C'  then 'Completed' "
        		+ "when sc.StageName like 'Delivered%'  and om.ChallanNo is not null then 'Dispatched' "
        		+ "else sc.StageName end as StageName, FORMAT(CompletionTime, 'dd-MM-yyyy HH:mm') as dateTime ,CompletionTime "
        		+ "FROM ordermaster om "
        		+ "INNER JOIN StagesCompleted sc ON om.G_orderNo = sc.G_orderno "
        		+ "WHERE om.L_OrderNo =? "
        		+ "and (sc.StageName like 'Order Entered%' or sc.StageName like 'Blank Removed%' or sc.StageName like 'Lens Removed%' or sc.StageName like 'Delivered%' or sc.StageName like 'Checking%' ) "
        		+ ") SELECT StageName,max(datetime) as datetime,MAX(CompletionTime) AS CompletionTime FROM obj where StageName is not null and StageName not like 'Checking%' GROUP BY StageName order by CompletionTime desc";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, lOrderNo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String stageName = rs.getString("StageName");
                    String completionTime = rs.getString("dateTime");

                    result.append(stageName)
                          .append(" : ").append(completionTime)
                          .append("\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching data: " + e.getMessage();
        }

        if (result.length() == 0) {
            return "No records found for Tracking no " + lOrderNo;
        }
        
        result.insert(0,"📊 Status Summery\n");
//        System.out.println("Hi");
        result.insert(0,getOrderDetailsMessages(lOrderNo));

        return result.toString();
    }
  public String getOrderDetailsMessages(String lorderno) {
	String messages = "";
	String labcode = lorderno.substring(0, 3);
	// System.out.print(labcode);
	LocalSqlServer obj=ServerConfig.getServerForLabCode(labcode);
	if(obj==null) {
		return messages;
//		return "No records found for Rx_cust_acc: " + Rx_cust_acc;
	}
//	System.out.print("connection is opened");
	
    // ✅ Update your database connection info
	
      String DB_URL = obj.getJdbcUrl();
      String DB_USER = obj.getUser();
      String DB_PASS = obj.getPassword();
      
      

    StringBuilder result = new StringBuilder();
    



    // String query ="select om.l_orderno,om.RegisterNo,om.order_no,cast(om.order_date as date) as order_date,case "
    // 		+ "when status='NC' and processed='N' then 'Order Entered' "
    // 		+ "when status='NC' and processed='Y' then 'In Process' "
    // 		+ "when status='C' and processed='Y' and ChallanNo is null then 'Completed' "
    // 		+ "when status='C' and processed='Y' and ChallanNo is not null then 'Dispatched' "
    // 		+ "else '' end as status,oo.lens_type,oo.coatcolor,oo.RSph,oo.RCyl,oo.RAxis,oo.RAddn,isnull(oo.Rqty,0) as Rqty,oo.LSph,oo.LCyl,oo.LAxis,oo.LAddn,isnull(oo.Lqty,0) as Lqty from ordermaster om inner join originalorder oo on om.g_orderno=oo.g_orderno inner join partydetails pd on om.Party_Code=pd.PARTY_CODE left join CancelledOrders co on om.g_orderno=co.g_orderno  where co.g_orderno is null and om.l_orderNo='"+ lorderno +"'";


    String query ="select StageName,CompletionTime from ordermaster om inner join stagescompleted sc on om.G_orderNo=sc.G_orderno where om.l_orderNo='"+ lorderno +"' order by Id desc";
    result.append("Status of Tracking No : /" + lorderno + "\n\n");
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         PreparedStatement stmt = conn.prepareStatement(query)) {
//        stmt.setString(1, Rx_cust_acc);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
//            	String RowNum = rs.getString("RowNum");
//                 String l_orderno = rs.getString("l_orderno");
//                 String order_no = rs.getString("order_no");
//                 String RegisterNo = rs.getString("RegisterNo");
//                 String order_date = rs.getString("order_date");
//                 String status =rs.getString("status");



                String StageName = rs.getString("StageName");
                String CompletionTime =rs.getString("CompletionTime");
                
//                 String lens_type =rs.getString("lens_type");
//                 String coatcolor =rs.getString("coatcolor");
//                 String RSph =rs.getString("RSph");
//                 String RCyl =rs.getString("RCyl");
//                 String RAxis =rs.getString("RAxis");
//                 String RAddn =rs.getString("RAddn");
//                 int Rqty =rs.getInt("Rqty");
                
//                 String LSph =rs.getString("LSph");
//                 String LCyl =rs.getString("LCyl");
//                 String LAxis =rs.getString("LAxis");
//                 String LAddn =rs.getString("LAddn");
//                 int Lqty =rs.getInt("Lqty");
                
//                 result.append(" Tracking No: /" + l_orderno + "\n🛒 Order No: "+order_no+" \n💬 Remarks: " + RegisterNo + "\n🕒 Status: "+status+" \n📅 Date: " + order_date + "\n\n" );
//                 result.append("👓 Power Details:\n");
//                 result.append("("+lens_type+"-"+coatcolor+")\n");
//                 if(Rqty>0) {
//                 result.append("R: S"+RSph+"|C"+RCyl+"|A"+RAxis+"|A"+RAddn+"\n");
//                 }
//                 if(Lqty>0) {
//                 result.append("L: S"+LSph+"|C"+LCyl+"|A"+LAxis+"|A"+LAddn+"\n");
//                 }
// //                result.append("______________________________");
//                 result.append("\n");
//                messages.add(result.toString());
//                result.setLength(0);



                
                result.append("StageName: " + StageName + "\n");
                result.append("CompletionTime: " + CompletionTime + "\n");

                result.append("\n");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();

        
    }


    return result.toString();
}


}
