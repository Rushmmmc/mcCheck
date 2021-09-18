/**
 * api/
 *
 * @author ：Rush
 * @date : 2021/9/18 19:30
 */
public class ErrorException extends Exception{
    public void error(){
        System.out.println("源文件或被查文件为空");
    }
}
