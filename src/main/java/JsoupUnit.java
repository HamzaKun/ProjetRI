import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.tartarus.snowball.ext.FrenchStemmer;
import sparql.KnowlegdeBase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by BinaryTree on 2016/11/16.
 */
public class JsoupUnit {
    Document doc;

    /**Read html content from File
     * */
    public String ReadHtml(File file) throws IOException {
        if(file == null){
            System.out.println("NULL Document");
            return null;
        }
        doc = Jsoup.parse(file,"UTF-8");
        return doc.body().text();
    }

    /**Read content from requetes.html
     * */
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

    public List<String[]> readQueriesRelations(File file) throws IOException {
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
            String[] words = keyWords.get(i).text().split(", ");
            KnowlegdeBase knowlegdeBase = new KnowlegdeBase();
            List<String> relation = new ArrayList<String>();
            for(int k=0; k< words.length-1 ; k++) {
                ArrayList<String> rel = knowlegdeBase.findRelation(words[k], words[k+1])
                String[] arrRel = new String[rel.size()];
                arrRel = rel.toArray(arrRel);
            }
            keyList.add(stemmedWords);
        }

        return  keyList;
    }

    public static void main(String[] args) {
        try {
            List<String[]> list = new JsoupUnit().readQueriesRelations(new File("target/classes/requetes.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**Read content from requetes.html
     * for knowledge base. using different split method and don't stem words
     * */

    public List<String[]> readQueryRI(File file) throws IOException {
        List<String[]> keyList = new ArrayList<String[]>();
        if (file == null){
            System.err.println("NULL Document");
            return null;
        }
        doc = Jsoup.parse(file, "UTF-8");
        Elements keyWords = doc.getElementsByTag("dd");
        for(int i = 0;i < keyWords.size();i += 2){
            ArrayList<String> res = new ArrayList<String>();
            //Split word by ", "
            String[] words = keyWords.get(i).text().split(", ");
            //add Synonyms for each words
            KnowledgeBase knowledgeBase = new KnowledgeBase();
            for(String word:words){
                ArrayList<String> synonyms = knowledgeBase.findSynonym(word);
                if(synonyms.size() == 0){
                    res.add(word);
                }else{
                    res.addAll(synonyms);
                }
            }
            String[] result = res.toArray(new String[res.size()]);
            keyList.add(result);
        }

        return  keyList;
    }

/*    public static void main(String[] args) throws IOException {
        new JsoupUnit().readQueryRI(new File("target/classes/requetes.html"));
    }*/



    /**Read html content from File path string
     * */
    public String ReadHtml(String url) throws IOException {
        File file = new File(url);
        if(file == null){
            System.out.println("Invalid Document Directory ");
            return null;
        }
        return ReadHtml(file);
    }

}
