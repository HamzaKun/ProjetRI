import java.io.*;
import java.util.*;

/**
 * Created by hamza on 09/12/16.
 */
public class ResultEvaluator {
    public String[] result;

    public List<Integer> evaluate() {
        List<Integer> sortedRes = new ArrayList<Integer>();
        List<SortedMap<String, Integer>> queriesResult = null;
        QueryEvaluator queryEvaluator = new QueryEvaluator();
        File file = new File("target/classes/requetes.html");
        JsoupUnit jsoup = new JsoupUnit();
        try {
            queriesResult = queryEvaluator.evaluateQueries(jsoup.readQueries(file));
            for(int i=0; i<queriesResult.size(); i++) {
                File pertFile = new File("target/classes/qrels/qrelQ" + (i+1) + ".txt");
                HashMap<String, Integer> pertinence = (HashMap) parsePertinenceFile(pertFile);
                Map<String, Integer> result = queriesResult.get(i);
                Set set = result.entrySet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry)it.next();
                    System.out.println(entry.getKey() + ", " +entry.getValue());
                    int r = pertinence.get(entry.getValue());
                    sortedRes.add(pertinence.get(entry.getValue()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Map<String, Integer> parsePertinenceFile(File file) {
        Map<String, Integer> filePertinence = new HashMap<String, Integer>();

        try {
            String line;
            BufferedReader bf = new BufferedReader(new FileReader(file));
            //line = bf.readLine();
            while ( (line = bf.readLine()) != null ){
                String[] values = line.split("\\s+");
                filePertinence.put(values[0], Integer.parseInt(values[1]));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        Display the Map's data
        Set set = filePertinence.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            System.out.println(entry.getKey() + ", " +entry.getValue());
        }
         */
        return filePertinence;
    }
}
