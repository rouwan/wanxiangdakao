package com.example.vientianetest.service;

import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MobileLogService {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void writeLog(String content){
        Date now = new Date();
        String fileName = formatter.format(now).substring(0,10)+".log";
        FileWriter writer = null;
        try{
            File file = new File("./mobileLogs/"+fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            writer = new FileWriter("./mobileLogs/"+fileName, true);
            writer.write(formatter.format(now) +": " + content + "\r\n");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
