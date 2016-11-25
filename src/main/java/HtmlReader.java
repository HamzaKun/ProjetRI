import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinaryTree on 2016/11/25.
 */
public class HtmlReader {
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

        String words[]  = temp.split(" ");
        WordAttribute wordAttribute;
        for(String word:words){
            //if word already exist in vocabulary
            if(vocabulary.containsKey(word)){
                wordAttribute = vocabulary.get(word);
                //Add File name to word attribute
                if(!wordAttribute.getIndex().containsKey(fileName)){
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
}
