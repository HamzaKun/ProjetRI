import java.util.ArrayList;

/**
 * Created by BinaryTree on 2016/11/25.
 */
public class WordAttribute {
    private int frequency;
    private ArrayList<String> index;

    public ArrayList<String> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<String> index) {
        this.index = index;
    }

    public WordAttribute(int frequency, ArrayList<String> index) {
        this.frequency = frequency;
        this.index = index;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    public void increaseValue(){
        this.frequency += 1;
    }
    public void addPath(String path){
        this.index.add(path);
    }

}
