import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.tartarus.snowball.ext.FrenchStemmer;
import sparql.KnowledgeBase;

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

    /**
     * Read content from requetes.html
     * @param file
     * @param stemmed TRUE : return stemmed words, False : return un-stemmed queries
     * @return
     * @throws IOException
     */
    public List<String[]> readQueries(File file, boolean stemmed) throws IOException {
        List<String[]> keyList = new ArrayList<String[]>();
        if (file == null){
            System.err.println("NULL Document");
            return null;
        }
        doc = Jsoup.parse(file, "UTF-8");
        Elements keyWords = doc.getElementsByTag("dd");
        for(int i = 0;i < keyWords.size();i += 2){
            //We stem the words before adding them to the query list
            String[] words = keyWords.get(i).text().split("[^\\p{L}\\d]+");
            if (stemmed) {
                FrenchStemmer frenchStemmer = new FrenchStemmer();
                String[] stemmedWords = new String[words.length];
                int j=0;
                for(String word : words) {
                    frenchStemmer.setCurrent(word);
                    frenchStemmer.stem();
                    stemmedWords[j] = frenchStemmer.getCurrent();
                    ++j;
                }
                keyList.add(stemmedWords);
            } else {
                keyList.add(words);
            }
        }

        return  keyList;
    }

    /**
     * Gives the relation between the query words.
     * @param file
     * @return
     * @throws IOException
     */
    public List<String[]> readQueriesRelations(File file) throws IOException {
        List<String[]> keyList = new ArrayList<String[]>();
        if (file == null){
            System.err.println("NULL Document");
            return null;
        }
        doc = Jsoup.parse(file, "UTF-8");
        Elements keyWords = doc.getElementsByTag("dd");
        KnowledgeBase knowlegdeBase = new KnowledgeBase();
        for(int i = 0;i < keyWords.size();i += 2){
            String[] words = keyWords.get(i).text().split(", ");
            List<String> relation = new ArrayList<String>();
            for(int k=0; k< words.length-1 ; k++) {
                relation.add(words[k]);
                ArrayList<String> results = (ArrayList<String>) knowlegdeBase.findRelation(words[k], words[k+1]);
                relation.addAll(results);
                if ( k == words.length - 2)
                    relation.add(words[k+1]);
            }
            String[] arrRel = new String[relation.size()];
            arrRel = relation.toArray(arrRel);
            keyList.add(arrRel);
        }

        return  keyList;
    }

    public static void main(String[] args) {
        try {
            List<String[]> list = new JsoupUnit().readQueriesRelations(new File("target/classes/requetes_2.html"));
            new JsoupUnit().readQueryRI(new File("target/classes/requetes_2.html"));
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

            String temp = res.toString().replace("[","");
            String[] result = temp.split("[^\\p{L}\\d]+");

            keyList.add(result);
        }
        return  keyList;
    }


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
