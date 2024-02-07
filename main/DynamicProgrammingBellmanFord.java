import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DynamicProgrammingBellmanFord {
    public static void main(String[] args) {
        List<Edges> edges = List.of(
                new Edges(6, 5, 9),
                new Edges(4, 5, 6),
                new Edges(1, 6, 14),
                new Edges(3, 6, 2),
                new Edges(3, 4, 11),
                new Edges(2, 4, 15),
                new Edges(1, 3, 9),
                new Edges(1, 2, 7)
        );

        bellmanFord(edges);
    }
    public static void bellmanFord(List<Edges> edges){
        int[] dp = new int[7];
        int length = dp.length - 2;
        dp[1] = 0;
        for (int i = 2; i < dp.length; i++){
            dp[i] = Integer.MAX_VALUE;
        }
        print(dp);
        for (int i = 0; i < length; i++){
            for (Edges edges1 : edges){
                if (dp[edges1.from] != Integer.MAX_VALUE) {
                    dp[edges1.to] = Integer.min(dp[edges1.to], dp[edges1.from] + edges1.weight);
                }
            }
        }
        print(dp);
    }

    static void print(int[] dp) {
        System.out.println(Arrays.stream(dp)
                .mapToObj(i -> i == Integer.MAX_VALUE ? "∞" : String.valueOf(i))
                .collect(Collectors.joining(",", "[", "]")));
    }
}

class Edges{
    int from;
    int to;
    int weight;

    public Edges(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
