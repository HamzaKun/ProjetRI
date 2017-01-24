import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by hamza on 09/12/16.
 */
public class ResultEvaluator {
    public String[] result;
    private static final int NUMBER_DOC = 138;
    LinkedHashMap<String, Integer> pertinence;
    //parameter for specify normalisation true = TF/TFmax false = TF
    private  boolean normalisation;
    private QueryEvaluator.ANALYSE_TYPE type;
    public boolean isNormalisation() {
        return normalisation;
    }

    public void setNormalisation(boolean normalisation) {
        this.normalisation = normalisation;
    }


    /**
     * Returns a sorted list -ascending frequency-
     * that contains the pertinence of the document
     * @return
     */
    public List<List<Integer>> evaluate() throws IOException {
        List<List<Integer>> sortedRes = new ArrayList<List<Integer>>();
        List<Map<String, Float>> queriesResult = null;
        QueryEvaluator queryEvaluator = new QueryEvaluator();
        queryEvaluator.setNormal(this.normalisation);
        queryEvaluator.setType(this.type);
        File file = new File("target/classes/requetes_2.html");
        JsoupUnit jsoup = new JsoupUnit();

          //  queriesResult = queryEvaluator.evaluateSemanticQueries(jsoup.readQueryRI(file));
                  queriesResult = queryEvaluator.evaluateSemanticQueries(jsoup.readQueries(file, false, JsoupUnit.TYPE.TYPE_SEMANTIC));

//            queriesResult = queryEvaluator.evaluateQueries(jsoup.readQueries(file, true));
            sortedRes = temp(queriesResult);

        return sortedRes;
    }
    /**
     * Returns a sorted list -ascending frequency-
     * that contains the pertinence of the document
     * @return
     */
    public List<List<Integer>> evaluateNative() throws IOException {
        List<List<Integer>> sortedRes = new ArrayList<List<Integer>>();
        List<Map<String, Float>> queriesResult = null;
        QueryEvaluator queryEvaluator = new QueryEvaluator();
        queryEvaluator.setType(this.type);
        queryEvaluator.setNormal(this.isNormalisation());
        File file = new File("target/classes/requetes_2.html");
        JsoupUnit jsoup = new JsoupUnit();

            queriesResult = queryEvaluator.evaluateQueries(jsoup.readQueries(file, true, JsoupUnit.TYPE.TPYE_NORMAL));
//            queriesResult = queryEvaluator.evaluateQueries(jsoup.readQueries(file, true));

                sortedRes = temp(queriesResult);
        return sortedRes;
    }

    List<List<Integer>> temp(List<Map<String, Float>> queriesResult)

    {
        List<List<Integer>> sortedRes = new ArrayList<List<Integer>>();
        for (int i = 0; i < queriesResult.size(); i++) {
            ArrayList<Integer> resu = new ArrayList<Integer>();
            //System.out.println("\n\nFor the query "+ (i+1) + "\n\n");
            File pertFile = new File("target/classes/qrels/qrelQ" + (i + 1) + ".txt");
            pertinence = (LinkedHashMap) parsePertinenceFile(pertFile);
            //System.out.println(pertinence);
            Map<String, Float> result = queriesResult.get(i);
            Set set = result.entrySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                resu.add(pertinence.get(entry.getKey()));
                //System.out.println(entry.getKey() + ", " +entry.getValue() +", "+ pertinence.get(entry.getKey()));
            }
            sortedRes.add(resu);
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

    public QueryEvaluator.ANALYSE_TYPE getType() {
        return type;
    }

    public void setType(QueryEvaluator.ANALYSE_TYPE type) {
        this.type = type;
    }
}
