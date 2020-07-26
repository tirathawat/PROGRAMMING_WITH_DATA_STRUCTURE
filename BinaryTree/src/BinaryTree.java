import java.util.*;

class Node {
    int data;
    Node left, right;

    Node (int data) {
        this.data = data;
        this.left = this.right = null;
    }

}

class BinaryTree {
    private Node root;

    private BinaryTree() {
        root = null;
    }

    private void insert(int data) {
        Node current, parent = null;
        Node newNode = new Node(data);

        if (root == null) root = newNode;
        else {
            current = root;

            while (current != null) {
                parent = current;

                if (current.data < newNode.data) current = current.right;
                else current = current.left;
            }

            if (parent.data < newNode.data) parent.right = newNode;
            else parent.left = newNode;
        }
    }

    private int minValue(Node current) {
        int min = current.data;
        while (current.left != null) {
            min = current.left.data;
            current = current.left;
        }
        delete(min);
        return min;
    }

    private void delete(int data) {
        if (root == null) return; //ไม่มีต้นไม้
        Node current = root, parent = root;
        while (current.data != data) {
            parent = current;

            if (current.data > data) current = current.left;
            else current = current.right;

            if (current == null) return;//ค้นหาไม่เจอ
        }
        if (current.right == null) {//มีโหนดด้านซ้ายเพียงโหนดเดียว หรือ ไม่มีทั้งซ้ายขวา --> แทนที่โหนดที่ต้องการลบด้วยโหนดซ้าย(ถ้าไม่มีโหนดซ้าย current.left == null)
            if (root.data == data) root = root.left; //ตัวที่ต้องการลบเป็น root
            else if (parent.right == current) parent.right = current.left;//ตัวที่ต้องการลบอยู่ด้านขวาของโหนดพ่อ
            else parent.left = current.left;//ตัวที่ต้องการลบอยู่ด้านซ้ายของโหนดพ่อ
        }
        else if (current.left == null) {//มีโหนดด้านขวาเพียงโหนดเดียว หรือ ไม่มีทั้งซ้ายขวา --> แทนที่โหนดที่ต้องการลบด้วยโหนดขวา(ถ้าไม่มีโหนดขวา current.right == null)
            if (root.data == data) root = root.right;//ตัวที่ต้องการลบเป็น root
            else if (parent.right == current) parent.right = current.right;//ตัวที่ต้องการลบอยู่ด้านขวาของโหนดพ่อ
            else parent.left = current.right;//ตัวที่ต้องการลบอยู่ด้านซ้ายของโหนดพ่อ
        }
        else {//มีโหนดลูกทั้งซ้ายและขวา
            Node q;
            if (root.data == data) {
                root = root.left;
                current = root;
                while (current.right != null) current = current.right;
                current.right = parent.right;
            }
            else if (parent.right == current) {
                parent.right = q = current.left;
                while (q.right != null) q = q.right;
                q.right = current.right;
            }
            else {
                parent.left = q = current.right;
                while (q.left != null) q = q.left;
                q.left = current.left;
            }
        }
    }

    private void breadthFirstSearch(Node root) {
        Node current = root;
        Queue <Node> queue = new LinkedList<>();
        while (current != null) {
            System.out.println("access node " + current.data);
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);

            if (queue.isEmpty()) current = null;
            else current = queue.poll();
        }
    }

    private void preOrder(Node current) {
        System.out.println("access node " + current.data);
        if (current.left != null) preOrder(current.left);
        if (current.right != null) preOrder(current.right);
    }

    private void inOrder(Node current) {
        if (current.left != null) inOrder(current.left);
        System.out.println("access node " + current.data);
        if (current.right != null) inOrder(current.right);
    }

    private void postOrder(Node current) {
        if (current.left != null) postOrder(current.left);
        if (current.right != null) postOrder(current.right);
        System.out.println("access node " + current.data);
    }

    private Node binarySearch (int data) {
        Node current = root;

        while (current != null && current.data != data) {
            if (current.data > data) current = current.left;
            else current = current.right;
        }
        return current;
    }

    ///Show tree diagram
    private void printNode(Node root) {
        int maxLevel = BinaryTree.maxLevel(root);
        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BinaryTree.isAllElementsNull(nodes)) return;

        int floor = maxLevel - level;
        int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BinaryTree.printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.data);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BinaryTree.printWhitespaces(betweenSpaces);
        }

        System.out.println();

        for (int i = 1; i <= edgeLines; i++) {
            for (Node node : nodes) {
                BinaryTree.printWhitespaces(firstSpaces - i);
                if (node == null) {
                    BinaryTree.printWhitespaces(edgeLines + edgeLines + i + 1);
                    continue;
                }

                if (node.left != null)
                    System.out.print("/");
                else
                    BinaryTree.printWhitespaces(1);

                BinaryTree.printWhitespaces(i + i - 1);

                if (node.right != null)
                    System.out.print("\\");
                else
                    BinaryTree.printWhitespaces(1);

                BinaryTree.printWhitespaces(edgeLines + edgeLines - i);
            }

            System.out.println();
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static int maxLevel(Node node) {
        if (node == null) return 0;
        return Math.max(BinaryTree.maxLevel(node.left), BinaryTree.maxLevel(node.right)) + 1;
    }

    private static boolean isAllElementsNull(List list) {
        for (Object object : list) if (object != null) return false;
        return true;
    }
    ///

    public static void main (String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        int[] data1 = {10, 15, 30, 25, 13, 38};
        int[] data2 = {10, 8, 13, 2, 7, 12, 14, 11, 5 , 9};
        int[] data3 = {10, 5, 15, 13, 30, 12, 14, 25, 38};
        int[] data4 = {10, 5, 15, 13, 30, 12, 14, 25, 26, 38};
        int[] data5 = {10, 20, 30, 31, 32, 34, 35, 36, 38, 39, 40, 50};
        int delete = 10;
        for (int i : data3) binaryTree.insert(i);
        binaryTree.printNode(binaryTree.root);
        System.out.println("Before delete 100 and " + delete);
        binaryTree.breadthFirstSearch(binaryTree.root);
        binaryTree.delete(100);
        binaryTree.delete(delete);
        System.out.println("After delete 100 and " + delete);
        binaryTree.printNode(binaryTree.root);
        binaryTree.breadthFirstSearch(binaryTree.root);
        System.out.println("Pre Order");
        binaryTree.preOrder(binaryTree.root);
        System.out.println("In Order");
        binaryTree.inOrder(binaryTree.root);
        System.out.println("Post Order");
        binaryTree.postOrder(binaryTree.root);
        System.out.println("Binary Search (Search 13)");
        Node node = binaryTree.binarySearch(13);
        if (node != null) System.out.println("Result = " + node.data);
        else System.out.println("Not found");

        System.out.println("Binary Search (Search 100)");
        node = binaryTree.binarySearch(100);
        if (node != null) System.out.println("Result = " + node.data);
        else System.out.println("Not found");
    }
}

