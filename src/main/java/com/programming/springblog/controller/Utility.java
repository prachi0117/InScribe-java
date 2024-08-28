package com.programming.springblog.controller;
import org.jsoup.Jsoup;

public class Utility {

    public static String stripHtmlTags(String html) {
        return Jsoup.parse(html).text();
    }
    
}
