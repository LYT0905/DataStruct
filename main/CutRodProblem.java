// 钢条切割问题
public class CutRodProblem {
    // 二维数组实现
    public static int cut(int[] values, int n){
        int[][] dp = new int[values.length][n + 1];
        for (int i = 1; i < values.length; i++){
            for (int j = 1; j < n + 1; j++){
                if (j >= i){
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - i] + values[i]);
                }else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[dp.length - 1][n];
    }

    // 一维数组实现
    public static int cut1(int[] values, int n){
        int[] dp = new int[n + 1];
        for (int i = 1; i < values.length; i++){
            for (int j = 1; j < n + 1; j++){
                if (j >= i){
                    dp[j] = Math.max(dp[j], dp[j - i] + values[i]);
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(cut1(new int[]{0, 1, 5, 8, 9}, 4));
    }
}
