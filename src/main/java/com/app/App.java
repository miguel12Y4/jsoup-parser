package com.app;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.*;

/**
 * Hello world!
 */
public final class App {
    
    public static void main(String[] args) throws IOException {

        String inicio ="https://es.wikipedia.org/wiki/Algoritmo_de_Dijkstra";
        String meta ="https://es.wikipedia.org/wiki/Teor%C3%ADa_de_grafos";

        String doc = recorrer(inicio, meta);
        
        System.out.println(doc);

    }

    public static String recorrer(String inicio, String meta) throws IOException{
        return camino(inicio, meta, 0,0,0, "");
    }

    private static String camino (String url, String meta, int profundidad, int index, int total, String title) throws IOException{
        
        //objetivo
        if(url.equals(meta)){
            System.out.println("Profundidad: "+profundidad);
            return url;
        }

        //condicion de borde (profundidad maxima de una ruta potencial)
        if(profundidad==3){
            return "";
        }

        //captura de un error al acceder a la url
        Document doc =null;

        try{
            doc = Jsoup.connect(url).get();
        }catch(Exception e){
            //si hubo un error en la conexion
            return "";
        }

        //obtener todos los enlaces de la pagina
        Elements links = doc.getElementsByTag("a");
        
        
        
        System.out.println("\nla profundidad es:"+profundidad+"."+index+"/"+total);
        String l = title+"\t\t\t"+url+"\n"+doc.title();

        System.out.println(l+"\n\n");
        
        //ordenar links encontrados para acotar el tiempo de busqueda
        
        //Elements pages = ordenar(links, meta);
        Elements pages = links;
        
        int i=0;
        for (Element link : pages) {
            String linkHref = link.attr("abs:href");
            String linksolo = link.attr("href");

            //verificar que el href sea valido y no apunte a si mismo
            if(linksolo != null && !linksolo.equals("") && linksolo.charAt(0) != '#' ){
                
                //busco la meta en la siguiente pagina (linkHref)
                String ruta = camino(linkHref, meta, profundidad+1, i, links.size(), l);
               
                //si llegó a la meta(ruta!=""), retorno el camino o ruta
                if(!ruta.equals("")){
                    return url+"   "+ruta;
                }
            }
            i++;
        }
        return "";
    }

    private static Elements ordenar(Elements links, String meta){
        Elements pages = new Elements();
        
        //ordenar estrategicamente los link de la pagina
        for (Element link : links) {
            String linkHref = link.attr("abs:href");
            
            
            boolean match= false;
            try{
                URL urlMeta = new URL(meta);
                URL urlPage = new URL(linkHref);
                
                match = urlMeta.getHost().equals(urlPage.getHost());
            }catch(Exception e) {
                match= false;
            }
            
            if(match){
                pages.add(0, link);
            }else{
                pages.add(pages.size(), link);
            }
        }
        return pages;

    }
}
