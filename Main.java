import java.util.Scanner;

public class Main {

    public static void main(String [] args)
    {
        Scanner in = new Scanner(System.in);
        int option;
        while(true) {
            System.out.println("Enter 1: MultiHash 2: DLeft 3: Cuckoo HashTable 4: exit");
            option = Integer.parseInt(in.nextLine());
            switch (option) {
                case 1: MultiHashingTable.callMultiHash(1000, 1000, 3);
                break;
                case 2: DLeftHashTable.callDLeft(1000, 1000, 4);
                break;
                case 3: CuckooHashTable.callCuckoo(1000, 1000, 3, 2);
                break;
                case 4: System.exit(0);
                break;
                default: System.out.println("Please Enter a Valid Option");
            }
        }
    }
}
