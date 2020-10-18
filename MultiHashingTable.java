import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MultiHashingTable {
    static int [] hashtable ;
    static int [] randValue;
    static int k;
    static int tableLen;
    static int numFlows;
    static Set<Integer>
            ids;
    public MultiHashingTable(int k, int n, int flows)
    {
        this.hashtable = new int[n];
        this.k = k;
        this.tableLen = n;
        this.randValue = new int[k];
        ids = new HashSet<>();
        numFlows = flows;
        int randomSize = randValue.length;
        Random rd = new Random();
        for (int i = 0; i < randomSize; i++) {
            randValue[i] = 0 + rd.nextInt(Integer.MAX_VALUE);   // min + rd.nextInt(maxValue)
        }
    }
    public  static void callMultiHash(int nValue, int flowVal, int kValue)
    {
        MultiHashingTable multiHashingTable = new MultiHashingTable(kValue, nValue,flowVal);
        for (int i = 0; i < numFlows; i++) {
            insert();
        }

        int count = 0;
        for (int i = 0; i < hashtable.length; i++) {
            if (hashtable[i] != 0) {
                count++;
            }
        }
        System.out.println("The number of table entries: " + count);
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
            File file = new File("MultiHashingOutput.txt");
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
        int[] hash = getHash(id);
        int len = hash.length;
        int index;
        for (int i = 0; i < len; i++)
        {
            index = hash[i] % tableLen;
            if(hashtable[index] == 0)
            {
                hashtable[index] = id;
                return true;
            }
        }
        return false;
    }
    public static int[] getHash(int id)
    {
        int [] hashes = new int[k];
        for (int i = 0; i < k; i++)
        {
            hashes[i] = id ^ randValue[i];
        }
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
