public class MyHashing {

    private int Tsize;

    private MyHashing(int Tsize) {
        this.Tsize = Tsize;
    }

    private int hashFunction(int data) {
        return data;
    }

    private int collisionResolve(int i) {
        return i*i;
    }

    private void saveHashing(int[] table, int data) {
        int h, i = 0;
        do {
            h = (hashFunction(data) + collisionResolve(i)) % Tsize;
            i++;
        } while(table[h] != 0) ;
        table[h] = data;
    }

    int searchHashing (int[] table, int key) {
        int h, i = 0 ;
        do {
            h = (hashFunction(key) + collisionResolve(i))% Tsize ;
            i++;
        } while((table[h] != key) && table[h] != 0);
        if (table[h] != 0) return h;
        else return -1;
    }

    public static void main (String[] args) {
        int[] dataExam = {38, 64, 76, 115, 129, 141, 157, 195, 375};
        int[] data = {415, 604, 871, 921, 163, 121, 895};
        int[] table = new int[13];
        MyHashing myHashing = new MyHashing(table.length);

        for (int i : dataExam) {
            System.out.printf("h(%d)\n", i);
            myHashing.saveHashing(table, i);
        }

        for (int i = 0; i < table.length; i++) System.out.println("Index " + i + " = " + table[i]);

    }
}
