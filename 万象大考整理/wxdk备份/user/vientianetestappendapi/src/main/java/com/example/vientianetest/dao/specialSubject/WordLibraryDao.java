package com.example.vientianetest.dao.specialSubject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WordLibraryDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    //获取字库
    public String getRandomWordLib(int len){
        String wordLibStr = jdbcOperations.queryForObject("select words from wxdk_wordlibrary limit 1 ",new HashMap<>(),String.class);
        Random random = new Random();
        int number = -1;
        String randomStr = "";
        for(int i=0;i<len; i++){
            number = random.nextInt(wordLibStr.length()-1);
            String word = String.valueOf(wordLibStr.charAt(number)); //效率最高的方法
            while(!randomStr.contains(word) && isChinese(word)){
                randomStr += word;
            }
        }
        return randomStr;
    }

    public static boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())
            flg = true;
        return flg;
    }
}
