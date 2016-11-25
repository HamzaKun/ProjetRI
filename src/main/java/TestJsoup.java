import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by BinaryTree on 2016/11/16.
 */
public class TestJsoup {
    Document doc;
    String title;
    public TestJsoup() throws IOException {

        doc =  Jsoup.connect("http://example.com/").get();
        title = doc.title();
    }
    public void Testfile() throws IOException {
        File file = new File("C:\\Users\\awang\\IdeaProjects\\ProjetRI\\src\\main\\resources\\corpus-utf8\\D1.html");
        doc = Jsoup.parse(file,"UTF-8");
        System.out.println(doc.getAllElements().size());
        System.out.println(doc.body().text());
//        for(int i = 0; i<doc.getAllElements().size();i++){
//            System.out.println(i);
//            System.out.println(doc.getAllElements().get(i).text());
//        }


    }

    public String ReadHtml(String url) throws IOException {
        File file = new File(url);
        if(file == null){
            System.out.println("Invalid Document Directory ");
            return null;
        }
        doc = Jsoup.parse(file,"UTF-8");
        return doc.body().text();
    }
    public String result(){
        return title;
    }
}
