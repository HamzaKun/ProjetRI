import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
    public String ReadHtml(String url) throws IOException {
        File file = new File(url);
        if(file == null){
            System.out.println("Invalid Document Directory ");
            return null;
        }
        return ReadHtml(file);
    }
}
