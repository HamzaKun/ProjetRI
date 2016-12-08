import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;//Element;

import java.io.File;
import java.io.IOException;

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

    public String readQueries(File file) throws IOException {
        if (file == null){
            System.err.println("NULL Document");
            return null;
        }
        doc = Jsoup.parse(file, "UTF-8");
        //Element h2Element = doc.ge;
        Elements h2Tags = doc.getElementsByTag("h2");
        /**
         * To get the queries
         */
        for (Element tmp : h2Tags){
            System.out.println(tmp.ownText());
        }

        return doc.body().text();
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
            System.out.println(jsoup.readQueries(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
