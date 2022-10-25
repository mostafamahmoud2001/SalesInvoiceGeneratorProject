/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SIGModel;

import SIGView.MainFrame;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;


/**
 *
 * @author DELL
 */
public class FileOperation {
    private final MainFrame mainFrame;

    public FileOperation(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
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
    

