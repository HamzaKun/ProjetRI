/**
 * Created by hamza on 16/11/16.
 */

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Example if the result of our search engine");
        ResultEvaluator resultEvaluator = new ResultEvaluator();
        ArrayList<List<Integer>> result = (ArrayList<List<Integer>>) resultEvaluator.evaluate();
        double[][] P = new double[5][9];
        double[][] R = new double[5][9];

        for(int i = 0; i< 5 ; i++){
            for(int j=0; j<9; j++) {
                P[i][j] = resultEvaluator.P( (ArrayList<Integer>)result.get(j), 5*(i+1) );
                R[i][j] = resultEvaluator.P( (ArrayList<Integer>)result.get(j), 5*(i+1) );
                System.out.println("Pertinence P@" + 5*(i+1) + " : " + P[i][j]);
                System.out.println("Rappel R@" + 5*(i+1) + " : " + R[i][j]);
            }
        }

    }
}
