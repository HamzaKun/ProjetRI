import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;
import org.tartarus.snowball.ext.FrenchStemmer;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Created by BinaryTree on 2016/11/25.
 */
public class HtmlReader {
    private final String[] stopwords ={"le", "la", "est","La","Le","Les"};
    private JsoupUnit jsoup;
    private Map<String,WordAttribute> vocabulary;
    public HtmlReader(){
        jsoup = new JsoupUnit();
    }
    /**
     *
     * */

    public Map<String,WordAttribute> read(String filePath) throws IOException {
        return this.read(null,filePath);
    }

    public Map<String,WordAttribute> read(Map<String,WordAttribute> map,String filePath) throws IOException {
        File file = new File(filePath);
        String fileName = file.getName();
        String temp = jsoup.ReadHtml(file);
        if(map == null){
            vocabulary = new HashMap<String,WordAttribute>();
        }else {
            vocabulary = map;
        }
        /**
        *\p{L} is the Unicode property for a letter in any language.

        *\P{L} is the negation of \p{L}, means it will match everything that is not a letter. (I understood that is what you want.)
        *http://stackoverflow.com/questions/17061050/using-regex-to-clean-up-the-string
        * */
        List<String> words = new LinkedList<String>(Arrays.asList(temp.split("\\P{L}+")));
        words.removeAll(Arrays.asList(stopwords));

        FrenchStemmer frenchStemmer = new FrenchStemmer();
        WordAttribute wordAttribute;
        for(String word:words){
            //
            frenchStemmer.setCurrent(word);
            frenchStemmer.stem();
            word = frenchStemmer.getCurrent();
            //if word already exist in vocabulary
            if(vocabulary.containsKey(word)){
                /*
                To iterate & check the stemmed words, not the others
                !!
                NB: If we want to add a regex verification
                 We should add it here !!!!!!!
                 */
                wordAttribute = vocabulary.get(word);
                //Add File name to word attribute
                if( (vocabulary.size() == 0) || !wordAttribute.getIndex().containsKey(fileName)){
                    wordAttribute.addPath(fileName);
                }else {
                    wordAttribute.increaseFrequencyInPath(fileName);
                }
                wordAttribute.increaseFrequencyInTotal();
            }else {
                wordAttribute = new WordAttribute();
                wordAttribute.addPath(fileName);
                vocabulary.put(word,wordAttribute);
            }
        }
        return vocabulary;
    }
    //create a new instance to use
    public static HtmlReader newInstance() {
        HtmlReader temp = new HtmlReader();
        return temp;
    }

    private String cleanWords(String words){

        // TODO: 2016/12/7 add clean words method using lucene analyser
        return "";

    }
}
