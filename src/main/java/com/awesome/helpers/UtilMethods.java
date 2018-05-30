package com.awesome.helpers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class UtilMethods {

    public static Map<String,String> getLinks(String url){
        Map<String,String> linksInUrl = new HashMap<String, String>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("article ul li a[abs:href]");
            for(Element link : links){
                linksInUrl.put(link.text(),link.attr("href"));
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }
        return linksInUrl;
    }
}
