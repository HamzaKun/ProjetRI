/**
 * Created by hamza on 16/11/16.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        ResultEvaluator resultEvaluator = new ResultEvaluator();
        //Specify whether use normalised search or not. TF/TFmax or TF
        resultEvaluator.setNormalisation(false);
        //Specify use which as enrich method Synonyms/Relation/Both
        resultEvaluator.setType(QueryEvaluator.ANALYSE_TYPE.SYNONYMS);
        ArrayList<List<Integer>> result = (ArrayList<List<Integer>>) resultEvaluator.evaluate();

//        ArrayList<List<Integer>> result = (ArrayList<List<Integer>>) resultEvaluator.evaluateNative();
        double[][] P = new double[5][11];
        double[][] R = new double[5][11];

        for(int i = 0; i< 5 ; i++){
            for(int j=0; j<11; j++) {
                P[i][j] = resultEvaluator.P( (ArrayList<Integer>)result.get(j), 5*(i+1) );
                R[i][j] = resultEvaluator.Pr( (ArrayList<Integer>)result.get(j), resultEvaluator.getPertinence(), 5*(i+1));
                //System.out.println( 5*(i+1) + " " + P[i][j]);
                //System.out.println( 5*(i+1) + " " + R[i][j]);
            }
        }
        //for (int i = 0; i<3; i++)
        for(int j = 0; j<11; j++) {
            System.out.print(P[0][j] + "\t" +P[1][j] + "\t" +P[4][j] + "\t" );
        }
        System.out.println("\n Rappel");
        for(int j = 0; j<11; j++) {
            System.out.print(String.format("%1.4f", R[0][j]) + "\t" +String.format("%1.4f",R[1][j]) + "\t" + String.format("%1.4f",R[4][j]) + "\t" );
        }
    }
}
