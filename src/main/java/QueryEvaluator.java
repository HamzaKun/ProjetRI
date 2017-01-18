import java.sql.Statement;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by hamza on 08/12/16.
 */
public class QueryEvaluator {

    private JsoupUnit jsoup;
    private String[] queries;
    private List<Map<String, Integer>> result;
    private static final int NUMBER_DOC = 138;

    /**
     * Return a map containing the documents & the frequency of the query words
     * @param query
     * @param connection
     * @return
     * @throws SQLException
     */
    public Map<String, Integer> evaluteQuery(String[] query, Connection connection) throws SQLException {
        Map<String, Integer> queryResult = new LinkedHashMap<String, Integer>();
        for (String word : query) {
            String sqlQuery = "Select document, frequence from RI.`index` where RI.`index`.`mot` like '%" + word + "%'";//mot like '%"+ word +"%'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                String document = rs.getString("document");
                int frequence = rs.getInt("frequence");
                if ( queryResult.get(document) == null ) {
                    queryResult.put(document, frequence);
                }else {
                    queryResult.put(document, queryResult.get(document) + frequence);
                }
                //System.out.println("\t( " + document + ", " + frequence + ")" + word);
            }
            rs.close();
            stmt.close();
        }
        for(int i = 1;i<= NUMBER_DOC;i++){
            if(!queryResult.containsKey("D"+i+".html")){
                queryResult.put("D"+i+".html",Integer.valueOf(0));
            }
        }
        queryResult = (LinkedHashMap<String, Integer>) sortByValue(queryResult);
        return queryResult;
    }

    /**
     * Calls the queryEvaluator method and iterates through all the queries
     * @param queries
     * @return
     */
    public List<Map<String, Integer>> evaluateQueries(List<String[]> queries) {
        try {
            DbConnect dbConnect = new DbConnect();
            Connection connection = dbConnect.getConnection();
            result = new ArrayList<Map<String, Integer>>();
            int i = 1;
            for (String[] query : queries) {
                //System.out.println("For the query :" + i++);
                result.add(evaluteQuery(query, connection));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void readQueries(String filePath) {
        File file = new File(filePath);

    }

    /**
     * Used to sort the Map using the word frequency
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
