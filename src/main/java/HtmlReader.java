
import org.tartarus.snowball.ext.FrenchStemmer;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by BinaryTree on 2016/11/25.
 */
public class HtmlReader {
    //copied from lucene FrenchAnalyzer.FRENCH_STOP_WORDS
    public static final String[] FRENCH_STOP_WORDS = new String[]{"a", "afin", "ai", "ainsi", "après", "attendu", "au", "aujourd", "auquel", "aussi", "autre", "autres", "aux", "auxquelles", "auxquels", "avait", "avant", "avec", "avoir", "c", "car", "ce", "ceci", "cela", "celle", "celles", "celui", "cependant", "certain", "certaine", "certaines", "certains", "ces", "cet", "cette", "ceux", "chez", "ci", "combien", "comme", "comment", "concernant", "contre", "d", "dans", "de", "debout", "dedans", "dehors", "delà", "depuis", "derrière", "des", "désormais", "desquelles", "desquels", "dessous", "dessus", "devant", "devers", "devra", "divers", "diverse", "diverses", "doit", "donc", "dont", "du", "duquel", "durant", "dès", "elle", "elles", "en", "entre", "environ", "est", "et", "etc", "etre", "eu", "eux", "excepté", "hormis", "hors", "hélas", "hui", "il", "ils", "j", "je", "jusqu", "jusque", "l", "la", "laquelle", "le", "lequel", "les", "lesquelles", "lesquels", "leur", "leurs", "lorsque", "lui", "là", "ma", "mais", "malgré", "me", "merci", "mes", "mien", "mienne", "miennes", "miens", "moi", "moins", "mon", "moyennant", "même", "mêmes", "n", "ne", "ni", "non", "nos", "notre", "nous", "néanmoins", "nôtre", "nôtres", "on", "ont", "ou", "outre", "où", "par", "parmi", "partant", "pas", "passé", "pendant", "plein", "plus", "plusieurs", "pour", "pourquoi", "proche", "près", "puisque", "qu", "quand", "que", "quel", "quelle", "quelles", "quels", "qui", "quoi", "quoique", "revoici", "revoilà", "s", "sa", "sans", "sauf", "se", "selon", "seront", "ses", "si", "sien", "sienne", "siennes", "siens", "sinon", "soi", "soit", "son", "sont", "sous", "suivant", "sur", "ta", "te", "tes", "tien", "tienne", "tiennes", "tiens", "toi", "ton", "tous", "tout", "toute", "toutes", "tu", "un", "une", "va", "vers", "voici", "voilà", "vos", "votre", "vous", "vu", "vôtre", "vôtres", "y", "à", "ça", "ès", "été", "être", "ô"};
    private JsoupUnit jsoup;
    private Map<String,WordAttribute> vocabulary;

    /**
     * Default constructor initial the jsoup instance
     */

    public HtmlReader(){
        jsoup = new JsoupUnit();
    }

    /**
     * Read html content and parser to Map content the frequency and words to generate the Index database
     * */
    public Map<String,WordAttribute> read(String filePath) throws IOException {
        return this.read(null,filePath);
    }

    /** Add html content to known Map and return the new Map which including the whole content.
     * */
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
         * [^\p{L}\d]+ means those who not a letter and not a number.
        * */
        List<String> words = new LinkedList<String>(Arrays.asList(temp.split("[^\\p{L}\\d]+")));
        words.removeAll(Arrays.asList(FRENCH_STOP_WORDS));

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

}
