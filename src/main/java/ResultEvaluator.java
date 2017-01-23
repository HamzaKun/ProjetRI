import java.io.*;
import java.util.*;

/**
 * Created by hamza on 09/12/16.
 */
public class ResultEvaluator {
    public String[] result;
    private static final int NUMBER_DOC = 138;
    LinkedHashMap<String, Integer> pertinence;

    /**
     * Returns a sorted list -ascending frequency-
     * that contains the pertinence of the document
     * @return
     */
    public List<List<Integer>> evaluate() {
        List<List<Integer>> sortedRes = new ArrayList<List<Integer>>();
        List<Map<String, Integer>> queriesResult = null;
        QueryEvaluator queryEvaluator = new QueryEvaluator();
        File file = new File("target/classes/requetes_2.html");
        JsoupUnit jsoup = new JsoupUnit();
        try {
            queriesResult = queryEvaluator.evaluateSemanticQueries(jsoup.readQueries(file, false));
//            queriesResult = queryEvaluator.evaluateQueries(jsoup.readQueries(file, true));
            for(int i=0; i<queriesResult.size(); i++) {
                ArrayList<Integer> resu = new ArrayList<Integer>();
                //System.out.println("\n\nFor the query "+ (i+1) + "\n\n");
                File pertFile = new File("target/classes/qrels/qrelQ" + (i+1) + ".txt");
                pertinence = (LinkedHashMap) parsePertinenceFile(pertFile);
                //System.out.println(pertinence);
                Map<String, Integer> result = queriesResult.get(i);
                Set set = result.entrySet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry)it.next();
                    resu.add(pertinence.get(entry.getKey()));
                    //System.out.println(entry.getKey() + ", " +entry.getValue() +", "+ pertinence.get(entry.getKey()));
                }
            sortedRes.add(resu);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sortedRes;
    }

    /**
     * Returns a Map of document names, and pertinence values
     * @param file
     * @return
     */


    public Map<String, Integer> parsePertinenceFile(File file) {
        Map<String, Integer> filePertinence = new LinkedHashMap<String, Integer>();
        try {
            String line;
            BufferedReader bf = new BufferedReader(new FileReader(file));
            while ( (line = bf.readLine()) != null ){
                String[] values = line.split("\\s+");
                filePertinence.put(values[0], Integer.parseInt(values[1]));
            }
            for(int i = 1;i<= NUMBER_DOC;i++){
                if(!filePertinence.containsKey("D"+i+".html")){
                    filePertinence.put("D"+i+".html",Integer.valueOf(0));
                }
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
            System.err.println(entry.getKey() + ", " +entry.getValue());
        }
*/
        return filePertinence;
    }
    /** Funtion P
     * @param pertinence ArrayList of pertinence of each document which is already sorted
     * @param k
     * @return
     * */
    public double P(ArrayList<Integer> pertinence,int k){
        int value = 0;
        for(int i = 0;i < k; i++){
            value += pertinence.get(i);
        }
        return (double)value/k;
    }
    /** Funtion Pr
     * @param pertinence ArrayList of pertinence of each document which is already sorted
     * @param query Sorted LinkedHashMap including the name of document and pertinence
     * @param k
     * @return
     * */
    public double Pr(ArrayList<Integer> pertinence,LinkedHashMap<String, Integer> query,int k){
        int pertinenceTotal = 0,value = 0;
        Iterator i = query.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            pertinenceTotal += (Integer) entry.getValue();
        }
        for(int j = 0;j < k; j++){
            value += pertinence.get(j);
        }
        return (double)value/pertinenceTotal;
    }

    public LinkedHashMap<String, Integer> getPertinence() {
        return pertinence;
    }

    public void setPertinence(LinkedHashMap<String, Integer> pertinence) {
        this.pertinence = pertinence;
    }
}
