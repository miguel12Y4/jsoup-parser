package com.app;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 */
public final class App {
    
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://es.wikipedia.org/wiki/Algoritmo_de_Dijkstra").get();
        String title = doc.title();
        Elements links = doc.getElementsByTag("a");
        
        System.out.println("link:\t"+doc.baseUri());
        System.out.println(title);
        
        System.out.println("");
        for (Element link : links) {
            String linkHref = link.attr("abs:href");

            if(linkHref != null && !linkHref.equals("") && linkHref.charAt(0) != '#'){
                System.out.println(linkHref);

            }

            System.out.println("");
        }

    }
}
