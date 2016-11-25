/**
 * Created by hamza on 16/11/16.
 */

import org.tartarus.snowball.ext.FrenchStemmer;

import java.beans.Statement;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Test {


    public static void main(String[] args) {
        DbConnect dbConnect = new DbConnect();
        Connection conn = dbConnect.getConnection();
        String query = "INSERT INTO RI.`vocabulaire`(`mot`, `document`) VALUES( ?, ?)";
        FrenchStemmer frenchStemmer = new FrenchStemmer();

        //InputStream in = Test.class.getResourceAsStream("corpus-utf8/D1.html");
        File folder = new File("target/classes/corpus-utf8");
        File[] listOfFiles = folder.listFiles();
        for (File tmpFile : listOfFiles) {
            if (tmpFile.isFile()) {
                try {
                    PreparedStatement pstatement = conn.prepareStatement(query);
                    pstatement.setString(1, "mot");
                    pstatement.setString(2, "D1.html");
                    pstatement.executeUpdate();
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
