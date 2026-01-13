package com.gkbvision.TelegramBot;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageLogger {

    private static final String LOG_FILE_PATH = "messages_log.txt";

    public static synchronized void logMessage(long chatId, String userName, String messageText) {
        try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            writer.write("[" + timestamp + "] ChatID: " + chatId +
                    " | User: " + (userName != null ? userName : "Unknown") +
                    " | Message: " + messageText + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}

