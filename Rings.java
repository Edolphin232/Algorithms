import java.io.*;
import java.util.StringTokenizer;

public class Rings{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int[] pegs = new int[n + 2];
        pegs[0] = pegs[n + 1] = 1;
        for (int i = 1; i <= n; i++) {
            pegs[i] = Integer.parseInt(st.nextToken());
        }
        int[][] dp = new int[n + 2][n + 2];
        for (int r = 2; r <= n + 1; r++) {
            for (int l = 0; l <= n - r + 1; l++) {
                int right = l + r;
                int max = 0;
                for (int i = l + 1; i < right; i++) {
                    max = Math.max(max, dp[l][i] + dp[i][right] + pegs[i] * pegs[l] * pegs[right]);
                }
                dp[l][right] = max;
            }
        }
        System.out.println(dp[0][n+1]);
    }
}