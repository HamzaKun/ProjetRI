import java.sql.Statement;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by hamza on 08/12/16.
 */
public class QueryEvaluator {

    private JsoupUnit jsoup;
    private String[] queries;
    private List<SortedMap<Integer, String>> result;

    public SortedMap<Integer, String> evaluteQuery(String[] query, Connection connection) throws SQLException{
        SortedMap<Integer, String> queryResult = new TreeMap<Integer, String>();
        for(String word : query) {
            String sqlQuery = "Select document, frequence from RI.`index` where RI.`index`.`mot` like '%" + word + "%'";//mot like '%"+ word +"%'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while(rs.next()) {
                String document = rs.getString("document");
                int frequence = rs.getInt("frequence");
                queryResult.put(frequence, document);
                System.out.println("( " + document + ", " + frequence +")");
            }
            rs.close();
            stmt.close();
        }
        return null;
    }

    public List<SortedMap<Integer, String>> evaluateQueries(List<String[]> queries) {
        try {
            DbConnect dbConnect = new DbConnect();
            Connection connection = dbConnect.getConnection();
            result = new ArrayList<SortedMap<Integer, String>>();
            for(String[] query : queries) {
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
}
