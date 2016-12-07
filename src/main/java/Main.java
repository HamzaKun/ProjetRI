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
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        DbConnect dbConnect = new DbConnect();
        IndexCreator indexCreator = new IndexCreator();
        String query = "INSERT INTO RI.`vocabulaire`(`mot`, `frequence`) VALUES( ?, ?)";
        indexCreator.createVocabulary();
        Set set = indexCreator.getVocabulary().entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            System.out.println(me.getKey());
        }
        System.out.println("The size : " + indexCreator.getVocabulary().size());
    }
}
