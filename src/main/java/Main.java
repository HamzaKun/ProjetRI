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
        System.out.println("Example if the result of our search engine");
        ResultEvaluator resultEvaluator = new ResultEvaluator();
        File file = new File("target/classes/qrels/qrelQ1.txt");
        resultEvaluator.parsePertinenceFile(file);
        resultEvaluator.evaluate();
    }
}
