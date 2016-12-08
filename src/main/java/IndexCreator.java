import org.tartarus.snowball.ext.FrenchStemmer;
import sun.security.acl.WorldGroupImpl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public IndexCreator createVocabulary() {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
        /**
         * We should iterate on the map
         */
    }

    public void fillDatabase() {
        /*try {
            DbConnect dbConnect = new DbConnect();
            Connection connection = dbConnect.getConnection();
            String query = "INSERT INTO RI.`vocabulaire`(`mot`, `frequence`) VALUES( ?, ?)";
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(query);
            Set set = vocabulary.entrySet();
            //Get an iterator
            Iterator i = set.iterator();
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry) i.next();
                //System.out.println("(" + me.getKey() + ", "+ ((WordAttribute)me.getValue()).getFrequency() + ")");
                ps.setString(1, (String) me.getKey());
                ps.setInt(2, ((WordAttribute)me.getValue()).getFrequency());
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        try {
            //int wordId = 1;
            DbConnect dbConnect = new DbConnect();
            Connection connection = dbConnect.getConnection();
            connection.setAutoCommit(false);
            String queryIndex = "INSERT INTO RI.`index`(`mot`, `document`, `frequence`) VALUES( ?, ?, ?)";
            PreparedStatement psIndex = connection.prepareStatement(queryIndex);
            Set set = vocabulary.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry) i.next();
                Set indexSet = ((WordAttribute)me.getValue()).getIndex().entrySet();
                Iterator idxI = indexSet.iterator();
                while (idxI.hasNext()) {
                    Map.Entry indexEntry = (Map.Entry) idxI.next();
                    psIndex.setString(1, (String) me.getKey());
                    psIndex.setString(2, (String) indexEntry.getKey());
                    psIndex.setInt(3, (Integer) indexEntry.getValue());
                    psIndex.addBatch();
                }

                //wordId++;
            }
            psIndex.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
