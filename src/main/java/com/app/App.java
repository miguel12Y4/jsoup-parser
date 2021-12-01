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

        String inicio ="https://es.wikipedia.org/wiki/Algoritmo_de_Dijkstra";
        String meta ="https://commons.wikimedia.org/wiki/1964_New_York_World%27s_Fair";

        String doc = recorrer(inicio, meta);
        
        System.out.println(doc);

    }

    public static String recorrer(String inicio, String meta) throws IOException{
        return camino(inicio, meta, 0,0,0);
    }

    private static String camino (String url, String meta, int profundidad, int index, int total) throws IOException{
        
        //objetivo
        if(url.equals(meta)){
            System.out.println("Profundidad: "+profundidad);
            return url;
        }

        //condicion de borde (profundidad maxima de una ruta potencial)
        if(profundidad==6){
            return "";
        }

        //captura de un error al acceder a la url
        Document doc =null;

        try{
            doc = Jsoup.connect(url).get();
        }catch(Exception e){
            return "";
        }

        //obtener todos los enlaces de la pagina
        Elements links = doc.getElementsByTag("a");

        System.out.println("la profundidad es:"+profundidad+"."+index+"/"+total);

        int i=0;
        System.out.println(url);
        for (Element link : links) {
            String linkHref = link.attr("abs:href");
            String linksolo = link.attr("href");

            if(linksolo != null && !linksolo.equals("") && linksolo.charAt(0) != '#'){
                
                String ruta = camino(linkHref, meta, profundidad+1, i, links.size());
               
                if(ruta!="" && !ruta.contains(url)){
                    return url+"   "+ruta;
                }
            }
            i++;
        }
        return "";
    }
}
