package com.gkbvision.TelegramBot;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MyTelegramBot extends TelegramLongPollingBot {

    // === Replace with your bot token and username ===
    private static final String BOT_TOKEN = "8223851470:AAGUSdhKcFt8cZCOGPiiYyQ5ObhHW39ur60";
    private static final String BOT_USERNAME = "GKBVisionBot";
 // Temporary login state tracking
    private final Map<Long, String> loginStep = new HashMap<>();
    private final Map<Long, String> tempUsername = new HashMap<>();
    private final Map<Long, Boolean> loggedInUsers = new HashMap<>();
    private final Map<Long, String> tempPassword = new HashMap<>();



    
    String welcomeMessage =
    	    "👋 *Welcome to GKB Vision Bot!*\n\n" +
    	    "Your smart assistant for lens order tracking and updates from *GKB Vision Pvt. Ltd.* 🏢\n\n" +
    	    "Start by choosing *Login* below or enter your Tracking No to check your order status. 🔍";



    
//    String helpMessage =
//    	    "📘 *GKB Vision Bot - Help Guide*\n\n" +
//    	    "Here’s how you can use this bot to manage and track your lens orders:\n\n" +
//    	    "🔹 *🔑 Login* — Link your Telegram account with your store/customer code.\n" +
//    	    "🔹 *🚪 Logout* — Unlink your account safely from the system.\n" +
//    	    "🔹 *👤 My Profile* — View your store name, contact info, and sales coordinator.\n" +
//    	    "🔹 *🧾 My Orders* — View all orders placed today.\n" +
//    	    "🔹 *📦 Dispatched* — See dispatched or delivered orders.\n" +
//    	    "🔹 *🚚 Track Order* — Enter your 9-digit Tracking No (e.g. `003224105`) to get real-time status.\n\n" +
//    	    "💬 *Tip:* You can type `/start` anytime to reopen the main menu.\n" +
//    	    "⚙️ Need help? Contact your GKB Vision Sales Coordinator.";
    String helpMessage =
    	    "📘 <b>Help & Usage Guide — GKB Vision Bot</b>\n\n" +
    	    "Welcome to <b>GKB Vision Bot</b> — your 24×7 digital assistant for tracking and managing your lens orders from <b>GKB Prime Pvt. Ltd.</b> 👓\n\n" +

    	    "Here’s what you can do:\n\n" +
    	    "🔑 <b>Login</b> — Link your store using your <b>Oracle Customer Code</b> to access personalized services.\n" +
    	    "🚪 <b>Logout</b> — Safely unlink your Telegram account anytime.\n\n" +

    	    "📦 <b>Track Order</b> — Enter your <b>9-digit Tracking Number</b> (e.g., <code>003224105</code>) to instantly know the current order status.\n\n" +

    	    "🧾 <b>My Orders</b> — View your recent orders. You can choose:\n" +
    	    " • <b>🗓️ Today Summary</b> — Quick overview of today’s order statuses.\n" +
    	    " • <b>🏭 In-Process</b> — Orders currently in production.\n" +
    	    " • <b>✅ Completed</b> — Orders ready for delivery.\n" +
    	    " • <b>🚚 Dispatched</b> — Orders already shipped to your store.\n\n" +

    	    "👤 <b>My Profile</b> — View your store details, contact info, and assigned Sales Coordinator.\n" +
    	    " • <b>🔐 Change Password</b> — Update your login password.\n\n" +

    	    "💬 <b>Help</b> — View this help menu anytime.\n\n";
	String updateCustomerCodeMessage = 
    		"🔔 Please update your Customer Code\n\n" +
    		"To continue, please send your Customer Code (e.g., x-xxx-xxx ) so we can link your Telegram account with your GKB Vision records.\n\n" +
    		"Once updated, you’ll be able to:\n" +
    		"• Track orders linked to your account\n" +
    		"• Get real-time status updates\n" +
    		"• Receive notifications directly here\n\n" +
    		"Please enter your Customer Code now ⬇️";


    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    
    @Override
    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            long chatId = update.getMessage().getChatId();
//            String messageText = update.getMessage().getText();
//            User user = update.getMessage().getFrom();
//            // Log every received message
//            MessageLogger.logMessage(chatId, user.getFirstName()+" "+user.getLastName(), messageText);
//            System.out.println("Received message from "+ user.getFirstName()+" "+user.getLastName() +" with Chatid : "+chatId +" as " + messageText);
//
//            try {
//
//                if (update.hasMessage() && update.getMessage().hasText()) {
//                    handleMessage(update);
//                } else if (update.hasCallbackQuery()) {
//                    handleCallback(update);
//                }
//	          
//	      } catch (Exception e) {
//	          e.printStackTrace();
//	      }
//        }

    	    try {
    	        if (update.hasMessage() && update.getMessage().hasText()) {
    	            long chatId = update.getMessage().getChatId();
    	            String messageText = update.getMessage().getText();
    	            User user = update.getMessage().getFrom();

    	            // Log every received message
    	            MessageLogger.logMessage(chatId, user.getFirstName() + " " + user.getLastName(), messageText);
    	            System.out.println("Received message from " + user.getFirstName() + " " + user.getLastName() +
    	                    " with Chatid : " + chatId + " as " + messageText);

    	            handleMessage(update); // ✅ handle user messages here
    	        } 
    	        else if (update.hasCallbackQuery()) {
    	            // ✅ handle inline button clicks here
    	            handleCallback(update);
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
    }
    private void sendMainMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if(CentralDatabase.doesMappingExist(chatId)) {
        message.setText(welcomeMessage+"👋\n\nChoose an option below:");
        }else {
        	message.setText("👋\n\nChoose an option below:");
        }
        List<KeyboardRow> keyboard;


        keyboard=keyboardButtonLogin(CentralDatabase.doesMappingExist(chatId));


        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(keyboard);
        markup.setResizeKeyboard(true);

        message.setReplyMarkup(markup);
        
        
        


        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private  List<KeyboardRow> keyboardButtonLogin(boolean isLoggedIn)
    {
    	String LoginLogOutButtonName;
    	if(isLoggedIn) {
    		LoginLogOutButtonName="🚪 LogOut";
    	}else {
    		LoginLogOutButtonName="🔐 LogIn";
    	}
        // Create buttons
        KeyboardButton btnTrack = new KeyboardButton("🚚 Track Order");
        KeyboardButton btnProfile = new KeyboardButton("👤 My Profile");
        KeyboardButton btnMyOrders = new KeyboardButton("🧾 My Orders");
//        KeyboardButton btnDispatched = new KeyboardButton("📦 Dispatched");
        KeyboardButton btnHelp = new KeyboardButton("❓ Help");
//        KeyboardButton btnSummery = new KeyboardButton("🗓️ Yesterday's Summary Report");
        KeyboardButton btnLoginLogOut = new KeyboardButton(LoginLogOutButtonName);
        
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
//        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        
        row1.add(btnTrack);
        if(isLoggedIn) {
        row2.add(btnProfile);
        	
        	
        row2.add(btnMyOrders);
//        row2.add(btnDispatched);
        
//        row3.add(btnSummery);
        
        row4.add(btnLoginLogOut);
        row4.add(btnHelp);
        }else 
        {
        	row1.add(btnLoginLogOut);	
        }
        
        
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
//        keyboard.add(row3);
        keyboard.add(row4);
        return keyboard;
    }
    
    

    private void handleMessage(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

     // ====== Handle login flow ======
        if (loginStep.containsKey(chatId)) {
            String step = loginStep.get(chatId);

            if (step.equals("username")) {
                tempUsername.put(chatId, messageText);
                loginStep.put(chatId, "password");
                sendText(chatId, "🔒 Please enter your password:");
                return;
            }

            if (step.equals("password")) {
                String username = tempUsername.get(chatId);
                String password = messageText;

//                if (credentials.containsKey(username) && credentials.get(username).equals(password)) {
                if(CentralDatabase.validateUser(username,password)) {
                    loggedInUsers.put(chatId, true);
                    loginStep.remove(chatId);
                    tempUsername.remove(chatId);

                    // (Optional) Link to your CentralDatabase here if needed
                     CentralDatabase.insertData(username, chatId);
                     String party_name=CentralDatabase.getPartyName(chatId);
                    sendText(chatId, "✅ Login successful! Welcome, " + party_name);
                    sendMainMenu(chatId);
                } else {
                    sendText(chatId, "❌ Invalid username or password. Please type 🔐 LogIn to try again.");
                    loginStep.remove(chatId);
                    tempUsername.remove(chatId);
                }
                return;
            }
            if (step.equals("old_password")) {
                tempUsername.put(chatId, CentralDatabase.getCustomerCode(chatId)); // Get logged-in username
                tempPassword.put(chatId, messageText); // store old password temporarily
                loginStep.put(chatId, "new_password");
                sendText(chatId, "Please enter your *new password* 🔒:");
                return;
            }

            if (step.equals("new_password")) {
                String username = tempUsername.get(chatId);
                String oldPass = tempPassword.get(chatId);
                String newPass = messageText;

                if (CentralDatabase.validateUser(username, oldPass)) {
                    CentralDatabase.updatePassword(username, newPass);
                    sendText(chatId, "✅ Password updated successfully!");
                } else {
                    sendText(chatId, "❌ Incorrect old password. Try again.");
                }

                loginStep.remove(chatId);
                tempUsername.remove(chatId);
                tempPassword.remove(chatId);
                return;
            }

        }

        
        
        // ✅ Handle 9-digit order numbers first (regex-based)
        if (messageText.matches("/\\d{9}")) {
            StageService service = new StageService();
            String stageMessage = service.getStageMessages(messageText.substring(1)); // remove '/'
            sendText(chatId, stageMessage);
            return;
        } 
        else if (messageText.matches("\\d{9}")) {
            StageService service = new StageService();
            String stageMessage = service.getStageMessages(messageText);
            sendText(chatId, stageMessage);
            return;
        }
//        else if (messageText.toUpperCase().matches("^[A-Z0-9]+-[A-Z]{2}-[A-Z0-9]+P$")) {
//        	if(CentralDatabase.isCustomerCodeValid(messageText.toUpperCase()) && CentralDatabase.doesMappingExist(chatId))
//	       	 {
//	       		CentralDatabase.updateData(chatId,messageText.toUpperCase());
//	       		String partyName=CentralDatabase.getPartyName(chatId);
//	       		sendText(chatId, "✅ Customer Code Updated \n\n"+partyName);
//	       		sendMainMenu(chatId);
//	
//	       	 }
//       	
//        	if(CentralDatabase.isCustomerCodeValid(messageText.toUpperCase())&&CentralDatabase.doesMappingExist(chatId)==false)
//          	 {
//	       		CentralDatabase.insertData(messageText.toUpperCase(),chatId);
//	       		String partyName=CentralDatabase.getPartyName(chatId);
//	       		sendText(chatId, "✅ Customer Code Updated\n\n"+partyName);
//	       		sendMainMenu(chatId);
//          	 }else {
//          		sendText(chatId, "Invalid Customer Code\n\n");
//          	 }
//        	return;
//       }

        switch (messageText) {
            case "/start":
                sendMainMenu(chatId);
                break;
            case "🚚 Track Order":
                sendText(chatId, "Please enter your 9-digit Tracking No 🔢");
                break;
//            case "👤 My Profile":
//            	
//            	String myprofile=CentralDatabase.getProfileMessage(chatId);
//                sendText(chatId, myprofile);
//                break;
//            case "👤 My Profile":
//                if (!loggedInUsers.getOrDefault(chatId, false)) {
//                    sendText(chatId, "🔐 Please log in first using '🔐 LogIn'.");
//                    break;
//                }
//                String myprofile = CentralDatabase.getProfileMessage(chatId);
//                sendText(chatId, myprofile);
//                break;
            case "👤 My Profile":
                if (!CentralDatabase.doesMappingExist(chatId)) {
                    sendText(chatId, "🔐 Please log in first using '🔐 LogIn'.");
                    break;
                }
                String myprofile = CentralDatabase.getProfileMessage(chatId);
//                sendText(chatId, myprofile);
                sendProfileWithChangePassword(chatId, myprofile);
                break;
            case "🧾 My Orders":
            	myOrders(chatId);
                break;
//            case "🗓️ Yesterday's Summary Report":
////            	dispatchedOrders(chatId);
//            	todaysTotalSummery(chatId);
//                break;
            case "🚪 LogOut":
                loggedInUsers.remove(chatId);
                CentralDatabase.deleteCustomerByChatId(chatId); // existing logout
                sendText(chatId, "You have been successfully logged out.");
                sendMainMenu(chatId);
                break;
//            case "🚪 LogOut":
//            	if(CentralDatabase.deleteCustomerByChatId(chatId))
//                sendText(chatId, "You Are Suceessfully LogedOut");
//            	sendMainMenu(chatId);
//                break;
//            case "🔐 LogIn":
//                sendText(chatId, updateCustomerCodeMessage);
//                break;
            case "🔐 LogIn":
                if (loggedInUsers.getOrDefault(chatId, false)) {
                    sendText(chatId, "✅ You are already logged in!");
                    break;
                }
                loginStep.put(chatId, "username");
                sendText(chatId, "👤 Please enter your username:");
                break;
            case "❓ Help":
                sendText(chatId, helpMessage);
                break;

            default:
                sendText(chatId, "Unknown command. Please select from the menu.");
                break;
        }
    }
    private void sendText(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setParseMode("HTML"); // <-- Enable HTML

        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private void handleCallback(Update update) {
//        String data = update.getCallbackQuery().getData();
//        long chatId = update.getCallbackQuery().getMessage().getChatId();
//
//        if (data.equals("check_another")) {
//            sendText(chatId, "Please enter another 9-digit L_OrderNo 🔢");
//        } else if (data.equals("contact_support")) {
//            sendText(chatId, "📞 You can contact our support at support@gkboptical.com");
//        }
//    }
    
    private void handleCallback(Update update) {
        String data = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        switch (data) {
            case "check_another":
                sendText(chatId, "Please enter another 9-digit L_OrderNo 🔢");
                break;

            case "contact_support":
                sendText(chatId, "📞 You can contact our support at support@gkboptical.com");
                break;

            case "change_password":
                loginStep.put(chatId, "old_password");
                sendText(chatId, "🔑 Please enter your *old password*:");
                break;
            case "dispatched_orders":
            	dispatchedOrders(chatId);
                break;
            case "inProcess_orders":
            	pendingOrders(chatId);
            break;
            case "completed_orders":
            	CompletedOrders(chatId);
            break;
        }
    }

    

	public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyTelegramBot());
            System.out.println("✅ Bot started successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    
	private void dispatchedOrders(long chatId) {
	
		String rx_cust_acc=CentralDatabase.getCustomerCode(chatId);
		String labcode=CentralDatabase.getLabCode(chatId);
		if(CentralDatabase.doesMappingExist(chatId))
		{
//			DispatchOrderService service=new DispatchOrderService();
			String dispatchOrderMessages=DispatchOrderService.getDispatchOrderMessages(labcode, rx_cust_acc);
			sendInChunks(chatId, dispatchOrderMessages, 20);
//			String partyName=CentralDatabase.getPartyName(chatId);
			
			
//			if (dispatchOrderMessages.size() > 0) {
//				sendText(chatId,"📦 Your recent "+dispatchOrderMessages.size()+" Orders that are Ready For Delivery, " + partyName);
//				for (String msg : dispatchOrderMessages) {
//				    sendText(chatId, msg);
//				}
//			}else {
//				sendText(chatId,"✅To access your data please update customer code");
//			}
	
	     
		}else {
			sendText(chatId,"✅To access your data please update customer code");
		}
	
	 }
	
	
	private void pendingOrders(long chatId) {
		
		String rx_cust_acc=CentralDatabase.getCustomerCode(chatId);
		String labcode=CentralDatabase.getLabCode(chatId);
		if(CentralDatabase.doesMappingExist(chatId))
		{
//			DispatchOrderService service=new DispatchOrderService();
			String pendingOrderMessages=PendingOrderService.getPendingOrderMessages(labcode, rx_cust_acc);
			sendInChunks(chatId, pendingOrderMessages, 20);
//			String partyName=CentralDatabase.getPartyName(chatId);
			
			
//			if (dispatchOrderMessages.size() > 0) {
//				sendText(chatId,"📦 Your recent "+dispatchOrderMessages.size()+" Orders that are Ready For Delivery, " + partyName);
//				for (String msg : dispatchOrderMessages) {
//				    sendText(chatId, msg);
//				}
//			}else {
//				sendText(chatId,"✅To access your data please update customer code");
//			}
	
	     
		}else {
			sendText(chatId,"✅To access your data please update customer code");
		}
	
	 }
	
	
private void CompletedOrders(long chatId) {
		
		String rx_cust_acc=CentralDatabase.getCustomerCode(chatId);
		String labcode=CentralDatabase.getLabCode(chatId);
		if(CentralDatabase.doesMappingExist(chatId))
		{
//			DispatchOrderService service=new DispatchOrderService();
			String completedOrderMessages=CompletedOrderService.getCompletedOrderMessages(labcode, rx_cust_acc);
			sendInChunks(chatId, completedOrderMessages, 20);
//			String partyName=CentralDatabase.getPartyName(chatId);
			
			
//			if (dispatchOrderMessages.size() > 0) {
//				sendText(chatId,"📦 Your recent "+dispatchOrderMessages.size()+" Orders that are Ready For Delivery, " + partyName);
//				for (String msg : dispatchOrderMessages) {
//				    sendText(chatId, msg);
//				}
//			}else {
//				sendText(chatId,"✅To access your data please update customer code");
//			}
	
	     
		}else {
			sendText(chatId,"✅To access your data please update customer code");
		}
	
	 }
	private void sendProfileWithChangePassword(long chatId, String profileText) {
	    SendMessage message = new SendMessage();
	    message.setChatId(chatId);
	    message.setText(profileText);

	    InlineKeyboardButton changePasswordButton = new InlineKeyboardButton("🔒 Change Password");
	    changePasswordButton.setCallbackData("change_password");

	    List<InlineKeyboardButton> row = new ArrayList<>();
	    row.add(changePasswordButton);

	    List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
	    keyboard.add(row);

	    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
	    markup.setKeyboard(keyboard);
	    message.setReplyMarkup(markup);
	    message.setParseMode("HTML"); // <-- Enable HTML

	    try {
	        execute(message);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	
	private void todaysTotalSummery(long chatId) {
		
		String rx_cust_acc=CentralDatabase.getCustomerCode(chatId);
		String labcode=CentralDatabase.getLabCode(chatId);
		if(CentralDatabase.doesMappingExist(chatId))
		{
//			TodaySummeryReport service=new TodaySummeryReport();
			String totaySummeryReportMessages=SummeryReport.getSummeryReportMessages(labcode, rx_cust_acc);
//			String totaySummeryOrdersMessages=TodaySummeryReport.getTodayOrderSummeryMessages(labcode, rx_cust_acc);
//			String partyName=CentralDatabase.getPartyName(chatId);
			
			
//			if (dispatchOrderMessages.size() > 0) {
//				sendText(chatId,"📦 Your recent "+dispatchOrderMessages.size()+" Orders that are Ready For Delivery, " + partyName);
//				for (String msg : dispatchOrderMessages) {
				    sendText(chatId, totaySummeryReportMessages);
//				    sendText(chatId, totaySummeryOrdersMessages);
//				    sendInChunks(chatId, totaySummeryOrdersMessages, 20);
				}
//			}else {
//				sendText(chatId,"✅To access your data please update customer code");
//			}
	
	     
//		}else {
//			sendText(chatId,"✅To access your data please update customer code");
//		}
//	
	 }
	
	private void sendInChunks(long chatId, String message, int linesPerChunk) {
	    if (message == null || message.trim().isEmpty()) {
	        return;
	    }

	    // Split message by newlines
	    String[] lines = message.split("\n");
	    StringBuilder chunk = new StringBuilder();

	    for (int i = 0; i < lines.length; i++) {
	        chunk.append(lines[i]).append("\n");

	        // Send after every 20 lines or at the end
	        if ((i + 1) % linesPerChunk == 0 || i == lines.length - 1) {
	            sendText(chatId, chunk.toString().trim());
	            chunk.setLength(0); // reset for next batch
	        }
	    }
	}
	

	
//	public void  myOrders(long chatId) {
//		String rx_cust_acc=CentralDatabase.getCustomerCode(chatId);
//		String labcode=CentralDatabase.getLabCode(chatId);
//		if(CentralDatabase.doesMappingExist(chatId))
//		{
//			PendingOrderService service=new PendingOrderService();
//			List<String> PendingOrderMessage=service.getPendingOrderMessages(labcode, rx_cust_acc);
//			String partyName=CentralDatabase.getPartyName(chatId);
//			
//			
//			if (PendingOrderMessage.size() > 0) {
//				sendText(chatId,"📦 Your Recent "+PendingOrderMessage.size()+" Orders, " + partyName );
//				for (String msg : PendingOrderMessage) {
//				    sendText(chatId, msg);
//				}
//			}else {
//				sendText(chatId,"✅To access your data please update customer code");
//			}
//   
//		}else {
//			sendText(chatId,"✅To access your data please update customer code");
//		}
//	}
	public void  myOrders(long chatId) {
		String rx_cust_acc=CentralDatabase.getCustomerCode(chatId);
		String labcode=CentralDatabase.getLabCode(chatId);
		if(CentralDatabase.doesMappingExist(chatId))
		{
//			PendingOrderService service=new PendingOrderService();
//			List<String> PendingOrderMessage=service.getPendingOrderMessages(labcode, rx_cust_acc);
//			String partyName=CentralDatabase.getPartyName(chatId);
			
			String totaySummeryReportMessages=SummeryReport.getSummeryReportMessages(labcode, rx_cust_acc);
			SendMessage message = new SendMessage();
		    message.setChatId(chatId);
		    message.setText(totaySummeryReportMessages);

		    InlineKeyboardButton dispatchedOrdersButton = new InlineKeyboardButton("📦 Dispatched");
		    dispatchedOrdersButton.setCallbackData("dispatched_orders");
		    
		    InlineKeyboardButton inProcessOrdersButton = new InlineKeyboardButton("🔧 InProcess");
		    inProcessOrdersButton.setCallbackData("inProcess_orders");
		    
		    InlineKeyboardButton completedOrdersButton = new InlineKeyboardButton("✅ completed");
		    completedOrdersButton.setCallbackData("completed_orders");

		    List<InlineKeyboardButton> row = new ArrayList<>();
		    
		    row.add(inProcessOrdersButton);
		    row.add(completedOrdersButton);
		    row.add(dispatchedOrdersButton);

		    List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
		    keyboard.add(row);

		    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
		    markup.setKeyboard(keyboard);
		    message.setReplyMarkup(markup);
		    message.setParseMode("HTML"); // <-- Enable HTML
		    
		    try {
		        execute(message);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		    
//			if (PendingOrderMessage.size() > 0) {
//				sendText(chatId,"📦 Your Recent "+PendingOrderMessage.size()+" Orders, " + partyName );
//				for (String msg : PendingOrderMessage) {
//				    sendText(chatId, msg);
//				}
//			}else {
//				sendText(chatId,"✅To access your data please update customer code");
//			}
   
		}else {
			sendText(chatId,"✅To access your data please update customer code");
		}
	}
}
