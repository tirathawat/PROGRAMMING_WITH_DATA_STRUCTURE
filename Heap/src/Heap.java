public class Heap {

    private void swap(int[] data, int i, int j) {
        int temp;
        temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private void siftDown(int[] data, int i, int n) {
        int j, max;
        max = i;
        do {
            j = max;
            if (2 * j + 1 <= n && data[2 * j + 1] > data[max]) max = 2 * j + 1;
            if (2 * j + 2 <= n && data[2 * j + 2] > data[max]) max = 2 * j + 2;
            if (j != max) swap(data, j, max);
        } while (j != max);
    }

    private void sort(int[] data) {
        int count = data.length;

        for (int i = count / 2; i >= 0; i--) {
            System.out.println(i+")");
            for (int j : data) System.out.print(j + " ");
            System.out.println();

            siftDown(data, i, count - 1);//ทำให้เป็น Heap
        }

        /*for (int i = count - 1; i > 0; i--) {
            swap(data, i, 0);
            siftDown(data, 0, i - 1);
        }*/
    }

    public static void main (String[] args) {
        int[] data = {27, 36, 45, 68, 63, 99, 40, 12, 11, 36, 86, 18, 25 ,74};

        Heap heap = new Heap();

        System.out.print("Before : ");
        for (int i : data) System.out.print(i + " ");
        System.out.println();

        heap.sort(data);

        System.out.print("After : ");
        for (int i : data) System.out.print(i + " ");
        System.out.println();
    }
}
