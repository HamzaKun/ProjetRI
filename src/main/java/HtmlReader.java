import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinaryTree on 2016/11/25.
 */
public class HtmlReader {
    private TestJsoup jsoup;
    public HtmlReader(){
        try {
            jsoup = new TestJsoup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // read html from local document
    /*@ Return HashMap key ==  word Value == frequency
    * */
    public Map<String,Integer> read(String s) throws IOException {
        String temp = jsoup.ReadHtml(s);
        Map table = new HashMap<String,Integer>();
        String words[]  = temp.split(" ");
        for(String x:words){
            if(table.containsKey(x)){
                table.put(x,(Integer)table.get(x) + 1);
            }else {
                table.put(x,1);
            }
        }
        return table;
    }

    public static HtmlReader newInstance() {
        HtmlReader temp = new HtmlReader();
        return temp;
    }
}
