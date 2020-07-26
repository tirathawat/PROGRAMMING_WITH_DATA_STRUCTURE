public class HeapTree {
    private int[] Heap;
    private int size;

    private HeapTree(int maxsize) {
        this.size = 0;
        Heap = new int[maxsize + 1];
        Heap[0] = Integer.MAX_VALUE;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private int getParent(int current) {
        return current / 2;
    }

    private int getLeftChild(int current) {
        return 2 * current;
    }
    private int getRightChild(int current) {
        return (2 * current) + 1;
    }

    private boolean isLeaf(int current) {
        return getRightChild(current) > size && getLeftChild(current) > size;
    }

    private void swap(int i, int j) {
        int tmp;
        tmp = Heap[i];
        Heap[i] = Heap[j];
        Heap[j] = tmp;
    }

    private void siftDown(int current) {
        if (isLeaf(current)) return;
        int max = current;
        if (getLeftChild(current) <= size && Heap[max] < Heap[getLeftChild(current)]) max = getLeftChild(current);
        if (getRightChild(current) <= size && Heap[max] < Heap[getRightChild(current)]) max = getRightChild(current);
        if (current != max) {
            swap(current, max);
            siftDown(max);
        }

    }

    private void insert(int data) {
        Heap[++size] = data;
        int current = size;
        while (Heap[current] > Heap[getParent(current)]) {
            swap(current, getParent(current));
            current = getParent(current);
        }
    }

    private void show () {
        System.out.print("Heap Tree = ");
        for (int i = 1; i <= size; i++) System.out.print(Heap[i] + " ");
        System.out.println();
    }

    private int remove() {
        int popped = Heap[1];
        Heap[1] = Heap[size--];
        siftDown(1);
        return popped;
    }

    public static void main(String[] args) {
        HeapTree heapTree = new HeapTree(50);
        int[] data = {27, 36, 45, 68, 63, 99, 40, 12, 11, 36, 86, 18, 25 ,74};
        for (int i : data) {
            System.out.println("Insert " + i);
            heapTree.insert(i);
            heapTree.show();
        }
        while (!heapTree.isEmpty()) {
            heapTree.show();
            System.out.println("remove " + heapTree.remove());
        }
    }
}