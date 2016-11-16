/**
 * Created by hamza on 16/11/16.
 */
import org.tartarus.snowball.ext.FrenchStemmer;
public class Test {
    public static void main(String[] args){
        String par = "intelligemment";
        FrenchStemmer frenchStemmer = new FrenchStemmer();
        frenchStemmer.setCurrent(par);
        frenchStemmer.stem();
        System.out.println(frenchStemmer.getCurrent());
    }
}
