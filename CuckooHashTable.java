import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CuckooHashTable {

    static int [] hashtable ;
    static int [] randValue;
    static int k;
    static int tableLen;
    static int steps;
    static int numFlows;
    static Set<Integer>
            ids;
    public CuckooHashTable(int k, int n, int steps, int flows)
    {
        this.hashtable = new int[n];
        this.k = k;
        this.tableLen = n;
        this.randValue = new int[k];
        ids = new HashSet<>();
        this.steps = steps;
        numFlows = flows;
        int randomSize = randValue.length;
        Random rd = new Random();
        for (int i = 0; i < randomSize; i++) {
            randValue[i] = 0 + rd.nextInt(Integer.MAX_VALUE);   // min + rd.nextInt(maxValue)
        }
    }
    public  static void callCuckoo(int nValue, int flowVal, int kValue, int cuckooSteps)
    {
        CuckooHashTable cuckooHashTable = new CuckooHashTable(kValue, nValue, cuckooSteps, flowVal);
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
            File file = new File("CuckooHashOutput.txt");
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
        boolean isSuccess = false;

        for (int i = 0; i < hash.length; i++) {
            if (hashtable[hash[i] % tableLen] == 0) {
                hashtable[hash[i] % tableLen] = id;
                return true;
            }
        }

        for (int i = 0; i < hash.length; i++) {
            if(move(hash[i], steps))
            {
                hashtable[hash[i] % tableLen] = id;
                return true;
            }
        }
        return isSuccess;
    }

    public  static boolean move(int hashVal, int step) {
        if (step <= 0) {
            return false;
        }
            int [] hash = getHash(hashtable[hashVal % tableLen]);

            for (int i = 0; i < hash.length; i++) {
                if (hash[i] != hashVal && hashtable[hash[i] % tableLen] == 0) {
                    hashtable[hash[i] % tableLen] = hashtable[hashVal % tableLen];
                    return true;
                }
            }

            for (int i = 0; i < hash.length; i++) {
                if (hash[i] != hashVal && move(hash[i], step - 1)) {
                    hashtable[hash[i] % tableLen] = hashtable[hashVal % tableLen];
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
