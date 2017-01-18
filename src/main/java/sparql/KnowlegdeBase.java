package sparql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by BinaryTree on 2017/1/11.
 */
public class KnowlegdeBase {
    private static SparqlClient sparqlClient = new SparqlClient("localhost:3030/ProjetRI");

    public KnowlegdeBase() {
        this.sparqlClient = new SparqlClient("localhost:3030/ribase");
    }

    /**
     * Find known word's synonyms.
     * @return List of String. All synonyms finded.
     * */
    ArrayList<String> findSynonym(String word) {
        ArrayList<String> synonyms = new ArrayList<String>();
        String query = "prefix owl: <http://www.w3.org/2002/07/owl#>\n"
                + "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "SELECT ?labels WHERE\n"
                + "{\n"
                + "    ?ressource rdfs:label ?mot.\n"
                + "  ?ressource rdfs:label ?labels\n"
                + "  FILTER(?mot = \"" + word + "\" || ?mot = \""+ word+"\"@fr || ?mot = \"" + word + "\"@en)\n"
                + "}\n";
        Iterable<Map<String, String>> prix = sparqlClient.select(query);
        for(Map<String,String> res:prix){
            synonyms.add(res.get("labels"));
        }
        System.out.println(synonyms);
        return synonyms;
    }

    public static void main(String[] args) {
        new KnowlegdeBase().findSynonym("prix");
        new KnowlegdeBase().findRelation("Omar Sy", "lieu naissance");
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

}
