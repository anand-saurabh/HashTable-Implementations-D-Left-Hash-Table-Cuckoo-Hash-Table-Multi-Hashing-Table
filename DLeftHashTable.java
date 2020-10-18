import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DLeftHashTable {

    static int [] hashtable ;
    static int [] randValue;
    static int tableLen;
    static int segments;
    static  int segmentSize;
    static int numFlows;
    static Set<Integer> ids;
    public DLeftHashTable(int n, int segments, int flows)
    {
        hashtable = new int[n];
        tableLen = n;
        randValue = new int[segments];
        int randomSize = randValue.length;
        this.segments = segments;
        segmentSize = n / segments;
        numFlows = flows;
        Random rd = new Random();
        ids = new HashSet<>();
        for (int i = 0; i < randomSize; i++) {
            randValue[i] = 0 + rd.nextInt(Integer.MAX_VALUE);// min + rd.nextInt(maxValue)
        }
    }
    public  static void callDLeft(int nValue, int flowVal, int totSeg)
    {
        DLeftHashTable dLeftHashTable = new DLeftHashTable(nValue, totSeg, flowVal);
        for (int i = 0; i < numFlows; i++) {
            insert();
        }
        int count = 0;
        for (int i = 0; i < hashtable.length; i++) {
            if (hashtable[i] != 0) {
                count++;
            }
        }
        System.out.println("The number of table entries " + count);
        System.out.println("The table entries are printed below");

        for (int i = 0; i < hashtable.length; i++)
        {
                System.out.println(hashtable[i]);
        }
        writeToFile(count);
    }

    static void writeToFile(int count)
    {
        BufferedWriter outputFile = null;
        try {
            File file = new File("DLeftOutput.txt");
            outputFile = new BufferedWriter(new FileWriter(file));
            outputFile.write(String.valueOf(count));
            outputFile.newLine();

            for (int i = 0; i < tableLen; i++)
            {
                outputFile.write(String.valueOf(hashtable[i]));
                outputFile.newLine();
            }
        } catch ( IOException e ) {
        } finally {
            if ( outputFile != null ) {
                try {
                    outputFile.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static boolean insert() {

        int id = getRandomInteger();
        int [] hash = new int[segments];
        int start = 0;
        int index = 0;
        for (int i = 0; i < segments; i++)
        {
            getHash(id, start, hash, index);
            start += segmentSize;
            index++;
        }
        int len = hash.length;
        boolean isSuccess = false;
        for (int i = 0; i < len; i++)
        {
            if(hashtable[hash[i]] == 0)
            {
                hashtable[hash[i]] = id;
                return true;
            }
        }
        return isSuccess;
    }
    public static int[] getHash(int id, int start, int[] hashes, int index)
    {
        hashes[index] = start + ((id ^ randValue[index]) % segmentSize);
        return hashes;
    }
    public static int getRandomInteger(){
        int val =  ((int) (Math.random()*(Integer.MAX_VALUE - 1))) + 1;// (max - min) + min
        while(ids.contains(val))
        {
            val =  ((int) (Math.random()*(Integer.MAX_VALUE - 1))) + 1;
        }
        ids.add(val);
        return val;
    }
}
