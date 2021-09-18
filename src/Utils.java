import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * api/
 *
 * @author ：Rush
 * @date : 2021/9/18 8:36
 */
public class Utils {
    public static void sourceClose(BufferedReader reader, InputStreamReader fileStream, FileInputStream file){
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
