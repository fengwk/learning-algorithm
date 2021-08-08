package fun.fengwk.learning.algorithm.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author fengwk
 */
public class Solution347 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution347().topKFrequent(new int[] {4,1,-1,2,-1,2,3}, 2)));
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequents = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            frequents.compute(nums[i], (k0, v0) -> v0 == null ? 1 : v0+1);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(frequents::get));
        for (Map.Entry<Integer, Integer> entry : frequents.entrySet()) {
            if (pq.size() < k) {
                pq.offer(entry.getKey());
            } else if (entry.getValue() > frequents.get(pq.peek())) {
                pq.poll();
                pq.offer(entry.getKey());
            }
        }

        int[] res = new int[pq.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = pq.poll();
        }
        return res;
    }

}
