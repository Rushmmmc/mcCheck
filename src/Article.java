import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * api/
 *
 * @author ：Rush
 * @date : 2021/9/11 20:30
 */
public class Article {
    //储存相邻汉字之间两两结合的Hash作为论文特征,并排序。
    private SortedSet<Integer> sortedHashFeature = new TreeSet<Integer>();
    //用来匹配标点符号的正则表达式
    private final String splitSymbol = "[.。,，、《》”“：？]";
    public Article(String path){
        BufferedReader reader = null;
        InputStreamReader fileStream = null;
        FileInputStream file = null;

        try{
            file = new FileInputStream(path);
            fileStream = new InputStreamReader(file, "UTF-8");
            reader = new BufferedReader(fileStream);

            String line;
            //读取缓存中的每一行
            while((line = reader.readLine()) != null){
                //用正则把这行文本分割成不同的部分
                String[] split = line.split(splitSymbol);

                //逐个短句分析
                for(String word : split){
                    for(int i = 0; i < word.length() - 1; i++){
                        int hash = word.substring(i,i + 2).hashCode();
                        sortedHashFeature.add(hash);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            sourceClose(reader,fileStream,file);
        }
    }

    public SortedSet<Integer> getFeature() {
        return sortedHashFeature;
    }

    public int Compare(Article source,Article target){
        return 0;
    }

    public void sourceClose(BufferedReader reader,InputStreamReader fileStream,FileInputStream file){
        if(reader != null){
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(fileStream != null){
            try {
                fileStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(file != null){
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
