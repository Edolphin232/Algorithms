import java.io.*;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

class FordFulkerson {
    public static void main(String[] args) throws IOException {
        //File file = new File("C:\\Users\\edwar\\Documents\\Algorithms\\HW 1\\ProjectSelection\\src\\test");
        //BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int nodes = Integer.parseInt(st.nextToken());
        int edges = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int[] scores = new int[nodes + 2];
        for (int i = 1; i <= nodes; i++) {
            scores[i] = Integer.parseInt(st.nextToken());
        }
        int[][] rGraph = new int[nodes + 2][nodes + 2];
        for (int i = 0; i < edges; i++) {
            st = new StringTokenizer(br.readLine());
            rGraph[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = Integer.parseInt(st.nextToken());
        }
        int[] parent = new int[nodes + 1];
        while (findPath(rGraph, 1, nodes, nodes, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = nodes; v != 1; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }
            for (int v = nodes; v != 1; v = parent[v]) {
                int u = parent[v];
                rGraph[u][v] -= pathFlow;
                rGraph[v][u] += pathFlow;
            }
        }
        for (int i = 0; i < nodes + 1; i++) {
            for (int j = 0; j < nodes + 2; j++) {
                if (rGraph[i][j] > 0) {
                    rGraph[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        for (int i = 1; i <= nodes; i++) {
            if (scores[i] >= 0) rGraph[0][i] = scores[i];
            if (scores[i] < 0) rGraph[i][nodes + 1] = -scores[i];
        }
        rGraph[0][1] = Integer.MAX_VALUE;
        rGraph[nodes][nodes + 1] = Integer.MAX_VALUE;
        parent = new int[nodes + 3];

        int ans = 0;

//        for(int[] in : rGraph){
//            for(int i : in){
//                System.out.print(i + " ");
//            }
//            System.out.println();
//        }
        while (findPath(rGraph, 0, nodes + 1, nodes + 2, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = nodes + 1; v != 0; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }
            for (int v = nodes + 1; v != 0; v = parent[v]) {
                int u = parent[v];
                rGraph[u][v] -= pathFlow;
                rGraph[v][u] += pathFlow;
            }
        }
//        for(int[] in : rGraph){
//
//            for(int i : in){
//                System.out.print(i + " ");
//            }
//            System.out.println();
//        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(0);

        boolean[] visited = new boolean[nodes + 3];
        visited[0] = true;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            ans += scores[u];

            for (int v = 1; v <= nodes; v++) {
                if (rGraph[u][v] > 0 && !visited[v]) {
                    queue.add(v);
                    visited[v] = true;
                }
            }
        }
        //System.out.println(Arrays.deepToString(rGraph));
        System.out.println(ans);
    }

    static boolean findPath(int[][] residual, int s, int t, int nodes, int[] parent) {
        boolean[] visited = new boolean[nodes + 2];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = s+1; v <= t; v++) {
                if (!visited[v] && residual[u][v] > 0) {
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return false;
    }
}
