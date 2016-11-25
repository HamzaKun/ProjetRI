/**
 * Created by hamza on 16/11/16.
 */

import org.tartarus.snowball.ext.FrenchStemmer;

import java.beans.Statement;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        DbConnect dbConnect = new DbConnect();
        Connection conn = dbConnect.getConnection();
        String query = "INSERT INTO RI.`vocabulaire`(`mot`, `frequence`) VALUES( ?, ?)";
        FrenchStemmer frenchStemmer = new FrenchStemmer();

        //InputStream in = Test.class.getResourceAsStream("corpus-utf8/D1.html");
        File folder = new File("target/classes/corpus-utf8");
        File[] listOfFiles = folder.listFiles();
        for (File tmpFile : listOfFiles) {
            if (tmpFile.isFile()) {
                try {
                    Map<String, Integer> vocabulaire;
                    vocabulaire = HtmlReader.newInstance().read("target/classes/corpus-utf8/" + tmpFile.getName());
                    Set set = vocabulaire.entrySet();
                    // Get an iterator
                    Iterator i = set.iterator();
                    PreparedStatement pstatement = conn.prepareStatement(query);
                    // Display elements
                    while (i.hasNext()) {
                        Map.Entry me = (Map.Entry) i.next();
                        frenchStemmer.setCurrent((String)me.getKey());
                        frenchStemmer.stem();
                        pstatement.setString(1, frenchStemmer.getCurrent());
                        pstatement.setInt(2, (Integer) me.getValue());
                        pstatement.executeUpdate();


                        System.out.print(me.getKey() + ": ");
                        System.out.println(me.getValue());
                    }
                    pstatement.close();

                    //Used to read files
                    /*Scanner scanner = new Scanner(tmpFile);
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        System.out.println(line);
                    }
                    scanner.close();*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        String par = "intelligemment";

        frenchStemmer.setCurrent(par);
        frenchStemmer.stem();
        System.out.println(frenchStemmer.getCurrent());
    }
}
