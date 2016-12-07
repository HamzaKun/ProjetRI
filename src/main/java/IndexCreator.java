import org.tartarus.snowball.ext.FrenchStemmer;
import sun.security.acl.WorldGroupImpl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by hamza on 25/11/16.
 */
public class IndexCreator {

    Map<String, WordAttribute> vocabulary;

    public Map<String, WordAttribute> getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Map<String, WordAttribute> vocabulary) {
        this.vocabulary = vocabulary;
    }

    public void createVocabulary(String resourcesPath) {
        FrenchStemmer frenchStemmer = new FrenchStemmer();
        File folder = new File(resourcesPath);
        File[] listOfFiles = folder.listFiles();
        for (File tmpFile : listOfFiles) {
            if (tmpFile.isFile()) {
                try {
                    Map<String, WordAttribute> fileWords;
                    fileWords = HtmlReader.newInstance().read(resourcesPath + tmpFile.getName());
                    Set set = fileWords.entrySet();
                    // Get an iterator
                    Iterator i = set.iterator();
                    // Display elements
                    while (i.hasNext()) {
                        Map.Entry me = (Map.Entry) i.next();
                        frenchStemmer.setCurrent((String)me.getKey());
                        frenchStemmer.stem();
                        if(Pattern.matches(frenchStemmer.getCurrent(), "[^a-z]")){

                        }
                        System.out.print(me.getKey() + ": ");
                        System.out.println(me.getValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void createIndex(String resourcesPath) {
        DbConnect dbConnect = new DbConnect();
        //Connection conn = dbConnect.getConnection();
        //String query = "INSERT INTO RI.`vocabulaire`(`mot`, `frequence`) VALUES( ?, ?)";
        createVocabulary();

    }

    public void createVocabulary() {
        FrenchStemmer frenchStemmer = new FrenchStemmer();
        File folder = new File("target/classes/corpus-utf8");
        File[] listOfFiles = folder.listFiles();
        vocabulary = null;
        HtmlReader htmlReader = HtmlReader.newInstance();
        /**
         * Iterate through the files of the repo
         */

        for (File tmpFile : listOfFiles) {
            if (tmpFile.isFile()) {
                try {
                    if ( vocabulary == null)
                        vocabulary = htmlReader.read("target/classes/corpus-utf8/" + tmpFile.getName());
                    else
                        vocabulary = htmlReader.read(vocabulary, "target/classes/corpus-utf8/" + tmpFile.getName());
                    //Set set = vocabulary.entrySet();
                    // Get an iterator
                    //Iterator i = set.iterator();
                    //PreparedStatement pstatement = conn.prepareStatement(query);
                    // Display elements
                    /*while (i.hasNext()) {
                        Map.Entry me = (Map.Entry) i.next();
                        /*pstatement.setString(1, frenchStemmer.getCurrent());
                        pstatement.setInt(2, (Integer) me.getValue());*/
                        //pstatement.executeUpdate();*/
                        //System.out.print(frenchStemmer.getCurrent() + ": ");
                        //System.out.println(me.getValue().getFrequency());
                    //}
                    //pstatement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        /**
         * We should iterate on the map
         */
    }
}
