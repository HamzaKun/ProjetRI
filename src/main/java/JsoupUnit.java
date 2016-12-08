import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.tartarus.snowball.ext.FrenchStemmer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        Elements keyWords = doc.getElementsByTag("dd");
        for(int i = 0;i < keyWords.size();i += 2){
            //We stem the words before adding them to the query list
            FrenchStemmer frenchStemmer = new FrenchStemmer();
            String[] words = keyWords.get(i).text().split("[^\\p{L}\\d]+");
            String[] stemmedWords = new String[words.length];
            int j=0;
            for(String word : words) {
                frenchStemmer.setCurrent(word);
                frenchStemmer.stem();
                stemmedWords[j] = frenchStemmer.getCurrent();
                ++j;
            }
            keyList.add(stemmedWords);
        }

        return  keyList;
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
