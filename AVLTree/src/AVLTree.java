import java.util.*;

class Node {
    int data;
    int balance, height;
    Node left, right;

    Node (int data) {
        this.data = data;
        left = right = null;
    }
}

public class AVLTree {

    private Node root;

    private AVLTree() {root = null;}

    private void reCalculate(Node n) {
        int leftHeight=-1, rightHeight=-1;
        if (n.left != null) {reCalculate(n.left); leftHeight = n.height ;}
        if (n.right != null) {reCalculate(n.right); rightHeight = n.height;}
        n.height = 1+Math.max(leftHeight,rightHeight);
        n.balance = rightHeight - leftHeight;
    }

    private int nodeHeight(Node n) {
        if (n == null) return -1;
        else return 1 + Math.max(nodeHeight(n.left), nodeHeight(n.right));
    }

    private int balanceFactor (Node n) {
        return (n == null)? 0 : nodeHeight(n.right) - nodeHeight(n.left);
    }

    private Node rotateRight(Node current) { //left of left
        Node temp = current.left;
        current.left = temp.right;
        temp.right = current;
        current = temp;
        return current;
    }

    private Node rotateLeft(Node current) { //right of right
        Node temp = current.right;
        current.right = temp.left;
        temp.left = current;
        current = temp;
        return current;
    }

    private Node rotateLeftToRight (Node current) { //right of left
        current.left = rotateLeft(current.left);
        current = rotateRight(current);
        return current;
    }

    private Node rotateRightToLeft (Node current) { //left of right
        current.right = rotateRight(current.right);
        current = rotateLeft(current);
        return current;
    }

    private Node adjustNode (Node current) {
        if (current.balance == -2 && current.left.balance == -1) return rotateRight(current);
        else if (current.balance == 2 && current.right.balance == 1) return rotateLeft(current);
        else if (current.balance == -2 && current.left.balance == 1) return rotateLeftToRight(current);
        else if (current.balance == 2 && current.right.balance == -1) return rotateRightToLeft(current);
        else return current;
    }

    private Node insertRecursion(Node node, int data) {
        if (node == null) return (new Node(data));
        if (data < node.data) node.left = insertRecursion(node.left, data);
        if (data > node.data) node.right = insertRecursion(node.right, data);
        node.balance = balanceFactor(node);
        return adjustNode(node);
    }

    private void insertRecursion (int data) {
        root = insertRecursion(root, data);
    }

    private void insert (int data) {
        Stack <Node> stack = new Stack<>();
        Node newNode = new Node(data);
        Node current, parent = null;
        if (root == null) {
            root = newNode;
            System.out.println("insert " + data);
            printNode(root);
            return;
        }

        current = root;
        while (current != null) {
            parent = current;
            stack.push(current);
            if (current.data > data) current = current.left;
            else current = current.right;
        }

        if (parent.data > data) {
            parent.left = newNode;
            stack.push(parent.left);
            System.out.println("insert " + parent.left.data);
        }
        else {
            parent.right = newNode;
            stack.push(parent.right);
            System.out.println("insert " + parent.right.data);
        }

        while (!stack.empty()) {
            printNode(root);
            Node node = stack.pop();
            System.out.println("pop node " + node.data);
            int balance = balanceFactor(node);
            System.out.println("Current Balance = " + balance);
            if (balance == -2) System.out.println("Left current Balance = " + balanceFactor(node.left));
            if (balance == 2) System.out.println("Right current Balance = " + balanceFactor(node.right));
            if (balanceFactor(node) == -2 && balanceFactor(node.left) == -1) {
                System.out.println("Right Rotation");
                if (node == root) root = rotateRight(node);
                else stack.pop().left = rotateRight(node);
                printNode(root);
            }
            else if (balanceFactor(node) == 2 && balanceFactor(node.right) == 1) {
                System.out.println("Left Rotation");
                if (node == root) root = rotateLeft(node);
                else stack.pop().right = rotateLeft(node);
                printNode(root);
            }
            else if (balanceFactor(node) == -2 && balanceFactor(node.left) == 1) {
                System.out.println("Left to Right Rotation");
                if (node == root) root = rotateLeftToRight(node);
                else stack.pop().left = rotateLeftToRight(node);
                printNode(root);
            }
            else if (balanceFactor(node) == 2 && balanceFactor(node.right) == -1) {
                System.out.println("Right to Left Rotation");
                if (node == root) root = rotateRightToLeft(node);
                else stack.pop().right = rotateRightToLeft(node);
                printNode(root);
            }

        }

    }

    ///Show tree diagram
    private void printNode(Node root) {
        int maxLevel = AVLTree.maxLevel(root);
        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || AVLTree.isAllElementsNull(nodes)) return;

        int floor = maxLevel - level;
        int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        AVLTree.printWhitespaces(firstSpaces);

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

            AVLTree.printWhitespaces(betweenSpaces);
        }

        System.out.println();

        for (int i = 1; i <= edgeLines; i++) {
            for (Node node : nodes) {
                AVLTree.printWhitespaces(firstSpaces - i);
                if (node == null) {
                    AVLTree.printWhitespaces(edgeLines + edgeLines + i + 1);
                    continue;
                }

                if (node.left != null)
                    System.out.print("/");
                else
                    AVLTree.printWhitespaces(1);

                AVLTree.printWhitespaces(i + i - 1);

                if (node.right != null)
                    System.out.print("\\");
                else
                    AVLTree.printWhitespaces(1);

                AVLTree.printWhitespaces(edgeLines + edgeLines - i);
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
        return Math.max(AVLTree.maxLevel(node.left), AVLTree.maxLevel(node.right)) + 1;
    }

    private static boolean isAllElementsNull(List list) {
        for (Object object : list) if (object != null) return false;
        return true;
    }
    ///

    public static void main (String[] args) {
        AVLTree avlTree = new AVLTree();
        int[] data1 = {14, 10, 9, 5, 7, 16, 25};
        //int[] data2 = {14, 10, 20, 5, 25, 16, 12, 2 , 9, 7};
        //int[] data3 = {10, 20, 30, 40, 50, 25};
        for (int i : data1) avlTree.insert(i);
        avlTree.printNode(avlTree.root);
    }
}
