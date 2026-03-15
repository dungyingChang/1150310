import java.util.Arrays;
import java.util.Comparator;

public class WeightedIntervalScheduling {

    // 工作類別
    static class Job {
        int start;
        int finish;
        int weight;

        Job(int start, int finish, int weight) {
            this.start = start;
            this.finish = finish;
            this.weight = weight;
        }
    }

    // 找到第 i 個工作之前，最後一個不衝突的工作
    public static int[] computeP(Job[] jobs) {
        int n = jobs.length;
        int[] p = new int[n];

        for (int i = 0; i < n; i++) {
            p[i] = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (jobs[j].finish <= jobs[i].start) {
                    p[i] = j;
                    break;
                }
            }
        }
        return p;
    }

    // DP 求最大權重
    public static int weightedIntervalScheduling(Job[] jobs) {
        Arrays.sort(jobs, Comparator.comparingInt(a -> a.finish));

        int n = jobs.length;
        int[] p = computeP(jobs);
        int[] dp = new int[n];

        dp[0] = jobs[0].weight;

        for (int i = 1; i < n; i++) {
            int include = jobs[i].weight;
            if (p[i] != -1) {
                include += dp[p[i]];
            }

            int exclude = dp[i - 1];
            dp[i] = Math.max(include, exclude);
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        Job[] jobs = {
            new Job(1, 3, 5),
            new Job(2, 5, 6),
            new Job(4, 6, 5),
            new Job(6, 7, 4),
            new Job(5, 8, 11),
            new Job(7, 9, 2)
        };

        long startTime = System.nanoTime();

        int result = weightedIntervalScheduling(jobs);

        long endTime = System.nanoTime();

        System.out.println("Maximum Weight: " + result);
        System.out.println("Running Time: " + (endTime - startTime) + " ns");
        System.out.println("Time Complexity: O(n^2)");
    }
}