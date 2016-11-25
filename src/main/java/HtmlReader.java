import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinaryTree on 2016/11/25.
 */
public class HtmlReader {
    private JsoupUnit jsoup;
    public HtmlReader(){
        try {
            jsoup = new JsoupUnit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * */
    public Map<String,WordAttribute> read(String filePath) throws IOException {
        File file = new File(filePath);
        String fileName = file.getName();
        String temp = jsoup.ReadHtml(file);
        Map vocabulary = new HashMap<String,WordAttribute>();
        String words[]  = temp.split(" ");
        WordAttribute wordAttribute;
        for(String word:words){
            //if word already exist in vocabulary
            if(vocabulary.containsKey(word)){
                wordAttribute = (WordAttribute) vocabulary.get(word);
                //Add File name to word attribut
                if(!wordAttribute.getIndex().containsKey(fileName)){
                    wordAttribute.addPath(filePath);
                }else {
                    wordAttribute.increaseFrequencyInPath(fileName);
                }
            }else {
                wordAttribute = new WordAttribute();
                wordAttribute.addPath(file.getPath());
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
