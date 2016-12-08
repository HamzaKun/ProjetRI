import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;//Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by BinaryTree on 2016/11/16.
 */
public class JsoupUnit {
    Document doc;

    public String ReadHtml(File file) throws IOException {
        if(file == null){
            System.out.println("NULL Document");
            return null;
        }
        doc = Jsoup.parse(file,"UTF-8");
        return doc.body().text();
    }

    public List<String[]> readQueries(File file) throws IOException {
        List<String[]> keyList = new ArrayList<String[]>();
        if (file == null){
            System.err.println("NULL Document");
            return null;
        }
        doc = Jsoup.parse(file, "UTF-8");
        //Element h2Element = doc.ge;
        Elements h2Tags = doc.getElementsByTag("h2");
        Elements keyWords = doc.getElementsByTag("dd");
        for(int i = 0;i < keyWords.size();i += 2){
            //System.out.println(keyWords.get(i).text());

            keyList.add(keyWords.get(i).text().split("\\P{L}+"));
        }

        return  keyList;
/*        *//**
         * To get the queries
         *//*
        for (Element tmp : h2Tags){
            //System.out.println(tmp.ownText());
        }*/

        //return doc.body().text();
    }
    public String ReadHtml(String url) throws IOException {
        File file = new File(url);
        if(file == null){
            System.out.println("Invalid Document Directory ");
            return null;
        }
        return ReadHtml(file);
    }

    public static void main(String[] args){
        JsoupUnit jsoup = new JsoupUnit();
        try {
            File file = new File("target/classes/requetes.html");
            List<String[]> list = jsoup.readQueries(file);
            for (String[] keywords:list) {
                for (String word : keywords) {
                    System.out.println(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
