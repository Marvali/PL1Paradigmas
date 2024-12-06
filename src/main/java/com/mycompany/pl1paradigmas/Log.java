/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pl1paradigmas;

/**
 *
 * @author isaac
 */


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private final String logFilePath;

    public Log() {
        this.logFilePath = "log.txt"; // Default log file path
    }

    public Log(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public synchronized void escribirLog(String mensaje) {
        try (FileWriter fw = new FileWriter(logFilePath, true);
             PrintWriter pw = new PrintWriter(fw)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            pw.println(timestamp + " - " + mensaje);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}