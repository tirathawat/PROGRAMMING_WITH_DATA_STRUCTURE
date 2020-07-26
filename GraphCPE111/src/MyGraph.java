import java.util.*;

class Edge implements Comparable <Edge> {
    int start;
    int stop;
    int weight;
    String name;

    Edge (int i, int j, int w) {
        start = i;
        stop = j;
        weight = w;
        name = ((char)(start + 65)) + Character.toString((char)(stop + 65));
    }

    Edge (int i) {
        start = i;
        stop = 0;
        weight = 0;
        name = Character.toString((char)(start + 65));
    }

    @Override
    public int compareTo(Edge o) {
        return weight - o.weight;
    }

    public String toString() {
        return name + " = " + weight;
    }
}

class Kruskal {
    private ArrayList <Edge> MST;

    private void transitiveClosure(boolean[][] graph) {
        for (int m = 0; m < graph.length; m++)
            for (int i = 0; i < graph.length; i++)
                for (int j = 0; j < graph.length; j++)
                    graph[i][j] = (graph[i][j]) || ((graph[i][m]) && (graph[m][j]));
    }

    private void printMinimumSpanningTree () {
        int count = 0;
        System.out.println("Minimum Spanning Tree");
        for (Edge e : MST) {
            System.out.println(e.name + " = " + e.weight);
            count += e.weight;
        }
        System.out.println("Total weight = " + count);
    }

    private void printPriorityQueue(PriorityQueue<Edge> k) {
        PriorityQueue<Edge> x = new PriorityQueue<>(k);
        for (int i = 0; i < k.size(); i++) {
            Edge e = x.poll();
            System.out.println(e);
        }
    }

    void minimumSpanningTree(int[][] graph) {

        int size = graph.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                if (graph[i][j] == graph[j][i]) graph[j][i] = 0;
        }

        PriorityQueue<Edge> edgeSet = new PriorityQueue<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                if (graph[i][j] != 0) {
                    Edge e = new Edge(i, j, graph[i][j]);
                    edgeSet.add(e);
                }
        }

        MST = new ArrayList<>();
        boolean[][] loop = new boolean[size][size];
        Edge e;

        System.out.println("Start");
        System.out.println("Edge set");
        printPriorityQueue(edgeSet);

        System.out.println("\n******* Kruskal step *******");

        while ((e = edgeSet.poll()) != null) {
            if (!loop[e.start][e.stop]) {
                System.out.println("Edge set");
                printPriorityQueue(edgeSet);
                System.out.println("Select edge " + e);
                loop[e.start][e.stop] = true;
                loop[e.stop][e.start] = true;
                transitiveClosure(loop);
                MST.add(e);
                System.out.println("MST = " + MST + "\n");
            }
            else System.out.println("Delete " + e + "\n");
        }

        printMinimumSpanningTree();
    }
}

class Prim {
    private PriorityQueue <Edge> edgeSet;
    private PriorityQueue <Edge> primMap;
    private ArrayList <Edge> MST;
    private TreeSet <Integer> node = new TreeSet<>();

    private void printPriorityQueue(PriorityQueue<Edge> k) {
        PriorityQueue<Edge> x = new PriorityQueue<>(k);
        for (int i = 0; i < k.size(); i++) {
            Edge e = x.poll();
            System.out.println(e);
        }
    }

    private boolean checkNode(Edge destination) {
        return node.contains(destination.start) && node.contains(destination.stop);
    }

    private void printNode() {
        System.out.print("Node in MST = [ ");
        for (Integer i : node) System.out.printf(" %c ", i + 65);
        System.out.println(" ]");
    }

    private void addEdgeFromNode(Edge destination) {
        ArrayList <Edge> temp = new ArrayList<>();
        for (Edge x : edgeSet) {
            if ((x.start == destination.start || x.stop == destination.start || x.start == destination.stop || x.stop == destination.stop))
                primMap.add(x);
            else temp.add(x);
        }
        edgeSet.clear();
        edgeSet.addAll(temp);
    }

    private void printMinimumSpanningTree () {
        int count = 0;
        System.out.println("\nMinimum Spanning Tree");
        for (Edge e : MST) {
            System.out.println(e.name + " = " + e.weight);
            count += e.weight;
        }
        System.out.println("Total weight = " + count);
    }

    void MinimumSpanningTree (int[][] graph) {
        int row, col;
        row = col = graph.length;

        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                if (graph[i][j] == graph[j][i]) graph[i][j] = 0;
            }
        }

        MST = new ArrayList<>();
        primMap = new PriorityQueue<>();
        edgeSet = new PriorityQueue<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (graph[i][j] != 0) {
                    Edge e = new Edge(j, i, graph[i][j]);
                    edgeSet.add(e);
                }
            }
        }
        System.out.println("Edge set");
        printPriorityQueue(edgeSet);
        System.out.println("\n******* Prim step *******");
        Edge e = edgeSet.poll();
        primMap.add(e);
        while ((e = primMap.poll()) != null) {
            if (checkNode(e)) System.out.println("Select edge " + e + ", nodes are already in MST, remove");
            else {
                System.out.println("Select edge " + e);
                MST.add(e);
                node.add(e.start);
                node.add(e.stop);
                addEdgeFromNode(e);
                System.out.println("MST = " + MST);
                printNode();
                System.out.println("\nPrim table = " + primMap);
            }
        }

        printMinimumSpanningTree();
    }
}

class Dijkstra {
    private PriorityQueue <Edge> edgeSet;
    private PriorityQueue <Edge> dijk;
    private ArrayList <Edge> shortestPath;

    private void printPriorityQueue(PriorityQueue<Edge> k) {
        PriorityQueue<Edge> x = new PriorityQueue<>(k);
        for (int i = 0; i < k.size(); i++) {
            Edge e = x.poll();
            System.out.println(e);
        }
    }

    private void removeEdgeToNode(int dest) {
        boolean success = false;
        ArrayList<Edge> temp = new ArrayList<>();

        System.out.printf("remove edge that points to node %c\n", dest + 65);

        for (Edge e : edgeSet) {
            if (e.stop == dest) {
                System.out.println("    remove edge " + e + " from Edge set");
                success = true;
            }
            else temp.add(e);
        }

        edgeSet.clear();
        edgeSet.addAll(temp);

        if (!success)
            System.out.println("    No remove edge from Edge set");
        else
            System.out.println("Edge set = " + edgeSet);
        temp.clear();
        success = false;

        for (Edge e : dijk)
            if (e.stop == dest) {
                System.out.println("    remove edge " + e + " from Dijk queue");
                success = true;
            } else
                temp.add(e);
        dijk.clear();
        dijk.addAll(temp);
        if (!success)
            System.out.println("    No remove edge from Dijk queue");
        else
            System.out.println("Dijk queue = " + dijk);
    }

    private void printShortestPath() {
        System.out.println("\nShortest Path");
        for (Edge e : shortestPath) System.out.println(e.name + " = " + e.weight);
    }

    private void addEdgeFromNode(String name, int dest, int adj) {
        boolean success = false;
        ArrayList <Edge> temp = new ArrayList<>();

        System.out.println("Edge set = " + edgeSet);
        System.out.println("Dijk queue = " + dijk);
        System.out.println("Add edge that points from node " + (char) (dest + 65));

        for (Edge e : edgeSet) {
            if (e.start == dest) {
                e.weight += adj;
                e.name = name + (char)(e.stop + 65);
                dijk.add(e);
                System.out.println("    Add " + e.name + " to Dijk queue");
                success = true;
            }
            else temp.add(e);
        }
        edgeSet.clear();
        edgeSet.addAll(temp);

        if (!success)
            System.out.println("    No edge from Edge set");
        else {
            System.out.println("Edge set = " + edgeSet);
            System.out.println("Dijk queue = " + dijk);
        }
    }


    void ShortestPath(int[][] graph) {
        int row, col;
        row = col = graph.length;
        edgeSet = new PriorityQueue<>();
        dijk = new PriorityQueue<>();
        shortestPath = new ArrayList<>();

        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                if (graph[i][j] != 0) {
                    Edge e = new Edge(i, j, graph[i][j]);
                    edgeSet.add(e);
                }
            }
        }

        System.out.println("Graph Edge");
        printPriorityQueue(edgeSet);
        System.out.printf("\nSelect Start node 0-%d : ",col);

        int start = 0;

        Edge select = new Edge(start);

        System.out.println("Dijkstra step from node " + select.name);
        System.out.printf("******** Select node %c ********\n", select.start + 65);

        addEdgeFromNode(select.name, select.start, 0);
        removeEdgeToNode(select.start);

        System.out.println("\nDijk queue = " + dijk);

        while ((select = dijk.poll()) != null) {
            System.out.println("select path = " + select + " from Dijk");
            System.out.printf("******** Select node %c ********\n", select.stop + 65);
            shortestPath.add(select);
            System.out.println("shortest path = " + shortestPath);
            addEdgeFromNode(select.name, select.stop, select.weight);
            removeEdgeToNode(select.stop);
            if (!dijk.isEmpty())
                System.out.println("\nDijk queue = " + dijk);
        }

        printShortestPath();
    }



}

class Node {
    String name;
    int status;
    int weight;

    Node(int weight, String name) {
        this.name = name;
        status = 1;
        this.weight = weight;
    }
}

class Graph {

    private HashMap <Node, LinkedList<Node>> adjacencyMap;
    private boolean directed;

    Graph(boolean directed) {
        this.directed = directed;
        adjacencyMap = new HashMap<>();
    }

    private void addEdgeHelper(Node a, Node b) {
        LinkedList<Node> tmp = adjacencyMap.get(a);

        if (tmp != null) tmp.remove(b);
        else tmp = new LinkedList<>();
        tmp.add(b);
        adjacencyMap.put(a,tmp);
    }

    void addEdge(Node source, Node destination) {

        if (!adjacencyMap.keySet().contains(source))
            adjacencyMap.put(source, null);

        if (!adjacencyMap.keySet().contains(destination))
            adjacencyMap.put(destination, null);

        addEdgeHelper(source, destination);

        if (!directed) {
            addEdgeHelper(destination, source);
        }
    }

    public boolean hasEdge(Node source, Node destination) {
        return adjacencyMap.containsKey(source) && adjacencyMap.get(source).contains(destination);
    }

    void breadthFirstSearch(Node node) {

        if (node == null) return;

        LinkedList<Node> queue = new LinkedList<>();
        queue.add(node);
        node.status = 2;

        while (!queue.isEmpty()) {
            Node currentFirst = queue.removeFirst();
            currentFirst.status = 3;
            System.out.print(currentFirst.name + " ");

            LinkedList<Node> allNeighbors = adjacencyMap.get(currentFirst);

            for (Node neighbor : allNeighbors) {
                if (neighbor.status == 1) {
                    queue.add(neighbor);
                    neighbor.status = 2;
                }
            }
        }

        System.out.println();
    }


    private void depthFirstSearch(Node node) {

        if (node == null) return;

        node.status = 3;
        System.out.print(node.name + " ");

        LinkedList<Node> allNeighbors = adjacencyMap.get(node);

        if (allNeighbors == null) return;

        for (Node neighbor : allNeighbors) {
            if (neighbor.status == 1) {
                neighbor.status = 2;
                depthFirstSearch(neighbor);
            }

        }
    }

}

public class MyGraph {
    public static void main (String[] args) {

        //Create graph from node
        Graph graph = new Graph(false);
        Node A = new Node(0, "A");
        Node B = new Node(0, "B");
        Node C = new Node(0, "C");
        Node D = new Node(0, "D");
        Node E = new Node(0, "E");
        Node F = new Node(0, "F");
        graph.addEdge(A, B);
        graph.addEdge(A, C);
        graph.addEdge(B, D);
        graph.addEdge(C, D);
        graph.addEdge(E, D);
        graph.addEdge(E, F);
        graph.addEdge(B, E);
        graph.addEdge(D, F);
        graph.addEdge(C, F);

        //Undirected graph
        int[][] graph1 = {
                {0, 2, 4 , 1, 0, 0, 0},
                {2, 0, 0 , 3, 10, 0, 0},
                {4, 0, 0 , 2, 0, 5, 0},
                {1, 3, 2 , 0, 7, 8, 4},
                {0, 10, 0 , 7, 0, 0, 6},
                {0, 0, 5 , 8, 0, 0, 1},
                {0, 0, 0 , 4, 6, 1, 0}
        };

        //Directed graph
        int[][] graph2 = {
                {0, 2, 0 , 1, 0, 0, 0},
                {0, 0, 0 , 3, 10, 0, 0},
                {4, 0, 0 , 0, 0, 5, 0},
                {0, 0, 2 , 0, 7, 8, 4},
                {0, 0, 0 , 0, 0, 0, 6},
                {0, 0, 0 , 0, 0, 0, 0},
                {0, 0, 0 , 0, 0, 1, 0}
        };

        //CPE111 Final Exam 2/59
        int[][] graphExam = new int[][]{
                {0, 9, 8, 4, 0, 4, 0, 0, 0, 0, 0, 0},
                {9, 0, 0, 8, 10, 0, 0, 0, 0, 0, 0, 0},
                {8, 0, 0, 0, 0, 7, 0, 7, 0, 0, 0, 0},
                {4, 8, 0, 0, 4, 2, 4, 0, 3, 0, 0, 0},
                {0, 10, 0, 4, 0, 0, 4, 0, 0, 6, 0, 0},
                {4, 0, 7, 2, 0, 0, 0, 8, 1, 0, 2, 0},
                {0, 0, 0, 4, 4, 0, 0, 0, 3, 5, 0, 0},
                {0, 0, 7, 0, 0, 8, 0, 0, 0, 0, 8, 0},
                {0, 0, 0, 3, 0, 1, 3, 0, 0, 6, 1, 6},
                {0, 0, 0, 0, 6, 0, 5, 0, 6, 0, 0, 6},
                {0, 0, 0, 0, 0, 2, 0, 8, 1, 0, 0, 6},
                {0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 0}};

        int[][] graphExam2 = new int[][] {
                {0, 2, 3, 2, 0, 4, 0, 4, 0},
                {2, 0, 0, 3, 6, 3, 4, 6, 0},
                {3, 0, 0, 7, 0, 2, 0, 8, 6},
                {2, 3, 7, 0, 7, 1, 4, 0, 0},
                {0, 6, 0, 7, 0, 0, 5, 7, 5},
                {4, 3, 2, 1, 0, 0, 2, 0, 7},
                {0, 4, 0, 4, 5, 2, 0, 0, 8},
                {4, 6, 8, 0, 7, 0, 0, 0, 0},
                {0, 0, 6, 0, 5, 7, 8, 0, 0}};


        /*graph.breadthFirstSearch(A);
        graph.depthFirstSearch(A);*/

        /*Kruskal kruskal = new Kruskal();
        kruskal.minimumSpanningTree(graph1);*/

        Prim prim = new Prim();
        prim.MinimumSpanningTree(graphExam2);

        /*Dijkstra dijkstra = new Dijkstra();
        dijkstra.ShortestPath(graph2);*/


    }
}
