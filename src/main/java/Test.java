/**
 * Created by hamza on 16/11/16.
 */
import org.tartarus.snowball.ext.FrenchStemmer;

import java.io.*;
import java.util.Scanner;

public class Test {
    public static void main(String[] args){
            //InputStream in = Test.class.getResourceAsStream("corpus-utf8/D1.html");
            File folder = new File("target/classes/corpus-utf8");
            File[] listOfFiles = folder.listFiles();
            for(File tmpFile : listOfFiles) {
                if (tmpFile.isFile()) {
                    try {
                        Scanner scanner = new Scanner(tmpFile);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            System.out.println(line);
                        }
                        scanner.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        String par = "intelligemment";
        FrenchStemmer frenchStemmer = new FrenchStemmer();
        frenchStemmer.setCurrent(par);
        frenchStemmer.stem();
        System.out.println(frenchStemmer.getCurrent());
    }
}
