import java.util.HashMap;
import java.util.Map;

/** Word Module
 * including total frequency, index.
 * Index is a map, which including file name and frequency for each file.
 * Created by BinaryTree on 2016/11/25.
 */
public class WordAttribute {
    private int frequency;
    private Map<String, Integer> index;

    public Map<String, Integer> getIndex() {
        return index;
    }

    public void setIndex(Map<String, Integer> index) {
        this.index = index;
    }

    public WordAttribute(int frequency, Map<String, Integer> index) {
        this.frequency = frequency;
        this.index = index;
    }
    public WordAttribute() {
        this.frequency = 1;
        this.index = new HashMap<String, Integer>();
    }

    public int getFrequency() {
        return frequency;
    }

    public void increaseFrequencyInPath(String path){
        this.index.put(path,this.index.get(path)+1);
    }
    public void addPath(String path){
        this.index.put(path,1);
    }
    public void increaseFrequencyInTotal(){
        frequency += 1;
    }

}
