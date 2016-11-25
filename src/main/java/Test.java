/**
 * Created by hamza on 16/11/16.
 */
import org.tartarus.snowball.ext.FrenchStemmer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Test {
    private Map table;
    public static void main(String[] args) throws IOException {
        String par = "intelligemment";
        FrenchStemmer frenchStemmer = new FrenchStemmer();
        frenchStemmer.setCurrent(par);
        frenchStemmer.stem();
        System.out.println(frenchStemmer.getCurrent());
        TestJsoup test = new TestJsoup();
        test.Testfile();

    }

    private void ReadString(String s){
        table = new LinkedHashMap<String,Integer>();
        String temp[]  = s.split(" ");
        for(String x:temp){
            
        }
    }
}
