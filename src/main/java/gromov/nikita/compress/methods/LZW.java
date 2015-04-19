package gromov.nikita.compress.methods;

import gromov.nikita.compress.Compressible;

import java.io.*;
import java.util.*;

/**
 * TEMP CAP
 */
public class LZW implements Compressible {



    public long compress(InputStreamReader src, OutputStreamWriter target) {

        ArrayList<CoderLZW>list = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(src)){

            while(br.ready()) {

                char buff[] = new char[100*1024];

                br.read(buff);

                buff = scanBuff(buff);

                list.add(new CoderLZW(new String(buff)));

            }

        }catch (IOException e){

        }

        for (int i = 0; i <list.size() ; i++) {

            list.get(i).endWorks();

        }

        try {

            BufferedWriter wr = new BufferedWriter(target);

            for (int i = 0; i <list.size() ; i++) {

                CoderLZW temp =list.get(i);

                wr.write(temp.toString()+" ");

                for (int j = 0; j <temp.getIndex().size() ; j++) {

                    wr.write(temp.getIndex().get(j)+" ");

                }

                wr.write('w');

            }

            wr.flush();

            wr.close();

        }catch (IOException e){

        }

        return 1;

    }

    static char[] scanBuff(char buff[]){

        int i;

        for (i = 0; i <buff.length&&buff[i]!=0; i++);

        char newBuff[] =new char[i];

        for (int j = 0; j <newBuff.length ; j++) {

            newBuff[j]=buff[j];

        }

        return newBuff;

    }


    public long decompress(InputStreamReader src, OutputStreamWriter target) {

        ArrayList<DecoderLZW> decoderLZW = new ArrayList<>();

        try{

            BufferedReader br = new BufferedReader(src);

            String tmp = new String();

            while (br.ready()){

                tmp+=br.readLine();

            }

            String []array = tmp.split("w");

            for (int i = 0; i <array.length ; i++) {

                decoderLZW.add(new DecoderLZW(array[i]));

            }

        }catch (IOException e){

            System.out.println(e);

        }

        for (int i = 0; i <decoderLZW.size() ; i++) {

            decoderLZW.get(i).endWorks();

        }

        try {

            BufferedWriter bw = new BufferedWriter(target);

            Collections.sort(decoderLZW);

            for (int i = 0; i <decoderLZW.size() ; i++)

            {

                bw.write(decoderLZW.get(i).getResult());

            }

            bw.flush();

            bw.close();



        }catch (IOException e){

            System.out.println(e);

        }

        return 0;

    }

    @Override
    public long compress(InputStream src, OutputStream target) {
        return 0;
    }

    @Override
    public long decompress(InputStream src, OutputStream target) {
        return 0;
    }
}

class CoderLZW  implements Runnable,Comparable<CoderLZW> {

    private static int DICT_SIZE = 256;

    private Thread thread;

    private LinkedList<Integer> index;

    private String string;

    CoderLZW(String name) {

        index = new LinkedList<>();

        string = name;

        thread = new Thread(this);

        thread.start();

    }

    @Override

    public void run() {

        char[] symbols = string.toCharArray();

        int end = 0;

        String buffer = new String();

        Map<String, Integer> table = new HashMap<>();



        int size = DICT_SIZE;

        for (int i = 0; i < size; i++) {

            char c = (char) i;

            table.put("" + c, i);

        }

        while (end < symbols.length) {

            if (table.containsKey(buffer + symbols[end])) {

                buffer = buffer + symbols[end];

            } else {

                index.add(table.get(buffer));

                table.put((buffer + symbols[end]), size++);

                buffer = "" + symbols[end];

            }

            end++;

        }

        if (!buffer.equals("")) {

            index.add(table.get(buffer));

        }

    }

    public boolean endWorks() {

        try {

            thread.join();

            return true;

        } catch (InterruptedException e) {

            return false;

        }

    }



    public LinkedList<Integer> getIndex() {

        return index;

    }

    public String toString(){

        return thread.getName();

    }

    @Override

    public int compareTo(CoderLZW o) {

        return this.toString().compareTo(o.toString());

    }

}

class DecoderLZW  implements Runnable,Comparable<DecoderLZW> {

    private static int DICT_SIZE = 256;

    private Thread t;

    private String result;

    private String indexs;

    private ArrayList<Integer> compressed = new ArrayList<>();

    private String threadName;

    DecoderLZW(String indexs) {

        this.indexs = indexs;

        t = new Thread(this);

        String tmp[] = indexs.split(" ");

        threadName = tmp[0];

        for (int i = 1; i < tmp.length; i++) {

            try {

                compressed.add(Integer.valueOf(tmp[i]));
            } catch (NumberFormatException e) {

                System.out.println(e + " " + i + " " + tmp[i]);

            }

        }


        t.start();

    }

    @Override

    public void run() {

        int dictSize = DICT_SIZE;

        Map<Integer, String> dictionary = new HashMap<>();

        for (int i = 0; i < dictSize; i++)

            dictionary.put(i, "" + (char) i);

        String w = "" + (char) (int) (compressed.remove(0));

        StringBuilder result = new StringBuilder(w);

        for (int k : compressed) {

            String entry;

            if (dictionary.containsKey(k)) {

                entry = dictionary.get(k);

            } else if (k == dictSize)

                entry = w + w.charAt(0);

            else

                throw new IllegalArgumentException("Bad compressed k: " + k);

            result.append(entry);

            // Add w+entry[0] to the dictionary.

            dictionary.put(dictSize++, w + entry.charAt(0));


            w = entry;

        }

        this.result = result.toString();

    }

    public String getResult() {

        return result;

    }

    public String getThreadName() {

        return threadName;

    }

    @Override

    public int compareTo(DecoderLZW o) {

        String tmp[] = threadName.split("-");

        int x = Integer.valueOf(tmp[1]);

        tmp = o.getThreadName().split("-");

        int y = Integer.valueOf(tmp[1]);

        return x - y;

    }

    public String toString() {

        return threadName;

    }

    public boolean endWorks() {

        try {

            t.join();

            return true;

        } catch (InterruptedException e) {

            return false;

        }

    }

}

