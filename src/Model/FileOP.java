
package Model;
import SIGView.MainFrame;
import java.io.*;
import java.util.*;


public class FileOP {
    
    private final MainFrame main;

    public FileOP(MainFrame main) {
        this.main = main;
    }
    
    public ArrayList<String> readFile(File f) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
         FileReader fileReader = new FileReader(f);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String x;
        
        while ((x = bufferedReader.readLine()) != null) {
            lines.add(x);
        }
        return lines;
    }
}
