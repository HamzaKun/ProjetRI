package sparql;

import java.util.ArrayList;

/**
 * Created by BinaryTree on 2017/1/11.
 */
public class KnowlegdeBase {
    private SparqlClient sparqlClient;

    public void setSparqlClient() {
        this.sparqlClient =  new SparqlClient("localhost:3030/ribase");
    }
    ArrayList<String> findSynonym(String word){
        
        return null;
    }
}
