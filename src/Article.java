import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * api/
 *
 * @author ：Rush
 * @date : 2021/9/11 20:30
 */
public class Article {
    public static void main(String[] args) {
        double rate = Article.getRate(args[0],
                args[1]);
        Article.output(args[2],rate);
    }
    //储存相邻汉字之间两两结合的Hash作为论文特征,并排序。
    private final SortedSet<Integer> sortedHashFeature = new TreeSet<>();
    //两个字的词的数量
    private final int wordsCount;

    public int getWordsCount() {
        return wordsCount;
    }

    public Article(String path) throws ErrorException{
        BufferedReader reader = null;
        InputStreamReader fileStream = null;
        FileInputStream file = null;
        try{
            file = new FileInputStream(path);
            fileStream = new InputStreamReader(file, StandardCharsets.UTF_8);
            reader = new BufferedReader(fileStream);

            String line;
            //读取缓存中的每一行
            while((line = reader.readLine()) != null){
                //用正则把这行文本分割成不同的部分
                //用来匹配标点符号的正则表达式
                String splitSymbol = "[.。,，、《》”“：？]";
                String[] split = line.split(splitSymbol);

                //逐个短句分析
                for(String word : split){
                    for(int i = 0; i < word.length() - 1; i++){
                        int hash = word.substring(i,i + 2).hashCode();
                        sortedHashFeature.add(hash);
                    }
                }
            }
            if(sortedHashFeature.size() == 0){
                throw new ErrorException();
            }
        }catch (IOException e){
            System.out.println("输入的文件不存在");
        }finally {
            //静态资源释放方法 直接调用
            Utils.sourceClose(reader,fileStream,file);
        }
        wordsCount = sortedHashFeature.size();
    }

    public SortedSet<Integer> getFeature() {
        return sortedHashFeature;
    }

    public static int compare(Article origin,Article target){
        int same = 0;

        SortedSet<Integer> originFeature = origin.getFeature();
        SortedSet<Integer> targetFeature = target.getFeature();
        if(originFeature.size() == 0 || targetFeature.size() == 0) {
            return -1;
        }
        //由于特征是以整数形式从小到大排序存储到集合中的
        //采用双指针法，计算2个整数集合共同元素的个数

        //origin特征指针
        int originCur = originFeature.first();
        //target特征指针
        int targetCur = targetFeature.first();
        while(originFeature.size() != 0 && targetFeature.size() != 0){
            if(originCur == targetCur) {
                //记录相同数
                same++;
                //移除相同特征
                originFeature.remove(originCur); targetFeature.remove(targetCur);

                //找下一个特征
                if(originFeature.size() != 0){
                    originCur = originFeature.first();
                }
                if(targetFeature.size() != 0){
                    targetCur = targetFeature.first();
                }
            }else if(originCur < targetCur){
                //当前特征值小的特征集后移
                originFeature.remove(originCur);
                if(originFeature.size() != 0){
                    originCur = originFeature.first();
                }
                //遍历完了 跑路
                else{
                    break;
                }
            }else{
                targetFeature.remove(targetCur);
                if(targetFeature.size() != 0){
                    targetCur = targetFeature.first();
                }
                //遍历完了 跑路
                else{
                    break;
                }
            }
        }
        return same;
    }

    public static double getRate(String originPath,String targetPath){
        Article origin;
        Article target;
        try {
            origin = new Article(originPath);
            target = new Article(targetPath);
        }catch (ErrorException e) {
            e.error();
            return -1;
        }
        int same = compare(origin,target);
        return same * 1.0 / Math.min(origin.getWordsCount(),target.getWordsCount());
    }

    public static void output(String path,double rate){
        DecimalFormat format=new DecimalFormat("0.00");
        String data;
        File outputFile = new File(path);
        try (FileWriter output = new FileWriter(outputFile)) {
            data = format.format(rate);
            char[] chars = data.toCharArray();
            output.write(chars);
            System.out.println(chars);
        } catch (IOException e) {
            System.out.println("输出的文件夹不存在");
        }
    }
}
