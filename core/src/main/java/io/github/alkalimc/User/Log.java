package io.github.alkalimc.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//日志
public class Log {

    //日志分类器
    public static String logCategory(int category) {
        switch(category) {
            case 0:
                return "FATAL";
            case 1:
                return "ERROR";
            case 2:
                return "WARNING";
            case 3:
                return "INFO";
            case 4:
                return "DEBUG";
            default:
                return "日志分类器出现非法的信息等级掩码";
        }
    }

    //日志输出
    public static void logSuccessful(int category, int id, String account, String situation) {
        writeLogToFile(LocalDateTime.now() +"[" + logCategory(category) + "] 用户: " + id + " 用户名: " + account + " 成功" + situation);
    }
    public static void illegal(int category, int id, String account, String situation, String reason) {
        writeLogToFile(LocalDateTime.now() +"[" + logCategory(category) + "] 用户: " + id + " 用户名: " + account + " 在" + situation + "时被拒绝访问, 原因: " + reason);
    }


    // 写日志到文件，满意了吧
    public static void writeLogToFile(String log) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(formatter);
        String logFileName = "logs/log-" + date + ".log";

        File logDir = new File("logs");
        if (!logDir.exists()) {
            logDir.mkdir();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write(log);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}