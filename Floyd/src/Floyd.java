public class Floyd {

    private static final int MAX = 9999;

    private void copyMatrix (int[][] MPL, int[][] MPK) {
        int n = MPL.length;
        for (int i = 0; i < n; i++) {
            System.arraycopy(MPK[i], 0, MPL[i], 0, n);
        }
    }

    private void shortestPath (int[][] graph) {
        int n = graph.length;
        int i, j, k = 1, m;
        int[][][] MP = new int[n][n][n];int[][] MPL = new int[n][n];
        copyMatrix(MP[k], graph);
        /*for (m = 0; m < n; m++) {
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    if (graph[i][j] > graph[i][m] + graph[m][j]) graph[i][j] = graph[i][m] + graph[m][j];
                }
            }
            show(graph);
        }*/


        for (k = 2; k < n; k++) {
            copyMatrix(MP[k], MP[k-1]);
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    /*if (i != j) {
                        for (m = 0; m < n; m++)
                            if (MP[k][i][j] > MP[k-1][i][m] + MP[k-1][m][j]) MP[k][i][j] = MP[k-1][i][m] + MP[k-1][m][j];
                    }*/
                    for (m = 0; m < n; m++)
                        if (MP[k][i][j] > MP[k-1][i][m] + MP[k-1][m][j]) MP[k][i][j] = MP[k-1][i][m] + MP[k-1][m][j];
                }
            }
        }


        for (k = 1; k < n; k++) {
            show(MP[k]);
        }

        //show(graph);
    }

    private void show (int[][] graph) {
        int n = graph.length;
        for (int[] ints : graph) {
            for (int j = 0; j < n; j++) {
                if (ints[j] == MAX) System.out.print("INF ");
                else System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main (String[] args) {
        int[][] graph = {{MAX, 5, MAX, MAX},
                        {7, MAX, MAX, 2},
                        {MAX, 3, MAX, MAX},
                        {4, MAX, 1, MAX}};
        Floyd floyd = new Floyd();
        //floyd.show(graph);
        floyd.shortestPath(graph);
    }
}
