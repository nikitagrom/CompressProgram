package gromov.nikita.compress.gui_mvc;

import gromov.nikita.compress.Compressible;
import gromov.nikita.compress.methods.LZ77;
import gromov.nikita.compress.methods.LZ78;
import gromov.nikita.compress.methods.LZW;
import gromov.nikita.compress.methods.PPM;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Model {

    public static final int LZ77 = 0;
    public static final int LZ78 = 1;
    public static final int LZW = 2;
    public static final int PPM = 3;

    private List<File> files = new ArrayList<>();
    private String msg ;
    //init by default
    private Compressible typeOfCompressor = new LZ77();

    public void setTypeOfCompressor(int typeOfCompr) {
        switch (typeOfCompr) {
            case 0 : typeOfCompressor = new LZ77(); break;
            case 1 : typeOfCompressor = new LZ78(); break;
            case 2 : typeOfCompressor = new LZW(); break;
            case 3 : typeOfCompressor = new PPM(); break;
            default: throw new IllegalArgumentException("Arg must be IN [0;3], arg = " + typeOfCompressor);
        }
    }

    public void compress (){
         msg = "";

        for (File file : files) {
            String name = file.getAbsolutePath() + ".arh";
            String sname = file.getName() + ".arh";
            File newFile = new File(name);
            try(InputStream is = new FileInputStream(file);
                OutputStream os = new FileOutputStream(newFile);
            ) {
                long start = System.nanoTime();
                typeOfCompressor.compress(is, os);
                long stop = System.nanoTime();

                float startSize = file.length();
                float finalSize = newFile.length();

                msg += "Файл: " + sname + "\nВремя: " + (stop-start)/(long)1000000 + " мс, Компрессия: " + (finalSize/startSize)*100 + "%\n\n";

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void decompress (){
        msg = "";
        for (File file : files) {
            String name = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."));
            String sname = file.getName().substring(0, file.getName().lastIndexOf("."));
            File newFile = new File(name);
            try(InputStream is = new FileInputStream(file);
                OutputStream os = new FileOutputStream(newFile);
            ) {

                long start = System.nanoTime();
                typeOfCompressor.decompress(is, os);
                long stop = System.nanoTime();

                float startSize = file.length();
                float finalSize = newFile.length();

                msg += "Файл: " + sname + "\nВремя: " + (stop-start)/(long)1000000 + " мс, Декомпрессия: " + (startSize/finalSize)*100 + "%\n\n";

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getMsg() {
        return msg;
    }
    public void addRowsToModel(File file) {
        files.add(file);
    }
    public void clearAllRowsFromModel() {
        files = new ArrayList<>();
    }
}
