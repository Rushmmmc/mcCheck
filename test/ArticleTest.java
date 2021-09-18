import org.junit.jupiter.api.Test;

/**
 * api/
 *
 * @author ：Rush
 * @date : 2021/9/18 19:10
 */
class ArticleTest {

    /**
     * 功能描述: 普通情况 两个文件都正常
     * @author Rush
     * @date 2021/9/18 19:56
     */
    @Test
    void normal() {
        double rate = Article.getRate("example/orig.txt",
                "example/orig_0.8_add.txt");
        Article.output("res/res.txt",rate);
    }

    /**
     * 功能描述: 源文件为空
     * @author Rush
     * @date 2021/9/18 19:57
     */
    @Test
    void SourceEmpty(){
        double rate = Article.getRate("example/empty.txt",
                "example/orig_0.8_add.txt");
        Article.output("res/res.txt",rate);
    }

    /**
     * 功能描述: 被查文件为空
     * @author Rush
     * @date 2021/9/18 19:57
     */
    @Test
    void targetEmpty(){
        double rate = Article.getRate("example/orig.txt",
                "example/empty.txt");
        Article.output("res/res.txt",rate);
    }

    @Test
    void del(){
        double rate = Article.getRate("example/orig.txt",
                "example/orig_0.8_del.txt");
        Article.output("res/res.txt",rate);
    }

    @Test
    void dis_1(){
        double rate = Article.getRate("example/orig.txt",
                "example/orig_0.8_dis_1.txt");
        Article.output("res/res.txt",rate);
    }

    @Test
    void dis_10(){
        double rate = Article.getRate("example/orig.txt",
                "example/orig_0.8_dis_10.txt");
        Article.output("res/res.txt",rate);
    }

    @Test
    void dis_15(){
        double rate = Article.getRate("example/orig.txt",
                "example/orig_0.8_dis_15.txt");
        Article.output("res/res.txt",rate);
    }

    @Test
    void inputError(){
        //输入的库不存在
        double rate = Article.getRate("example/origabcde.txt",
                "example/orig_0.8_dis_15.txt");
        Article.output("res/res.txt",rate);
    }

    @Test
    void outputError(){
        //输出的路径不存在
        double rate = Article.getRate("example/orig.txt",
                "example/orig_0.8_dis_15.txt");
        Article.output("....../res/resa.txt",rate);
    }

    @Test
    void inputOutputError(){
        //输入输出的路径都不存在
        double rate = Article.getRate("example/aorig.txt",
                "example/orig_0.8_dis_15.txt");
        Article.output("....../res/resa.txt",rate);
    }
}