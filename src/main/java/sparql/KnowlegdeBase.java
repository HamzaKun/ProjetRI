package sparql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by BinaryTree on 2017/1/11.
 */
public class KnowlegdeBase {
    private static SparqlClient sparqlClient = new SparqlClient("localhost:3030/ProjetRI");

    //TODO: changing the dataset's name
    public void setSparqlClient() {
        this.sparqlClient =  new SparqlClient("localhost:3030/ribase");
    }
    ArrayList<String> findSynonym(String word){
        
        return null;
    }

    public List<String> findRelation(String word1, String word2) {
        List<String> relations = new ArrayList<String>();
        String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "\n" +
                "SELECT DISTINCT ?label\n" +
                "WHERE {\n" +
                "  ?subject ?predicate ?object.\n" +
                "  ?res1 rdfs:label ?mot1.\n" +
                "  ?res2 rdfs:label ?mot2.\n" +
                "  {?res1 ?res2 ?object } UNION {?res2 ?res1 ?object}.\n" +
                "  ?object rdfs:label ?label\n" +
                "  FILTER(?mot1 = \""+ word1+ "\" || ?mot1 = \"" + word1 + "\"@fr)\n" +
                "  FILTER(?mot2 = \"" + word2 + "\" || ?mot2 = \"" + word2 + "\"@fr)\n" +
                "}";
        Iterable<Map<String, String>> results = sparqlClient.select(query);

        for (Map<String, String> result : results) {
            relations.add(result.get("label"));
        }
        System.out.println(relations);
        return relations;
    }

    public static void main(String[] args) {
        String word1 = "Omar Sy";
        String word2 = "lieu naissance";
        //System.out.println("executing the query:" + "\n" + query);
        KnowlegdeBase knowlegdeBase = new KnowlegdeBase();

        //System.out.println("Finding the relations between" + word1 + ", and " + word2);
        knowlegdeBase.findRelation(word1, word2);
    }
}
