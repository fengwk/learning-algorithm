package fun.fengwk.learning.algorithm.retrieve20;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwk
 */
public class ListMerge {

    public static List<Integer> diff(int[] arr1, int[] arr2) {
        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;
        Integer pre = null;
        while (i < arr1.length || j < arr2.length) {
            if (pre != null && ((i < arr1.length && arr1[i] == pre) || (j < arr2.length && arr2[j] == pre))) {
                if (i < arr1.length && arr1[i] == pre) {
                    i++;
                }
                if (j < arr2.length && arr2[j] == pre) {
                    j++;
                }
            } else {
                if (i >= arr1.length) {
                    pre = arr2[j];
                    res.add(arr2[j]);
                    j++;
                } else if (j >= arr2.length) {
                    pre = arr1[i];
                    res.add(arr1[i]);
                    i++;
                } else {
                    if (arr1[i] == arr2[j]) {
                        pre = arr1[i];
                        j++;
                        i++;
                    } else if (arr1[i] < arr2[j]) {
                        res.add(arr1[i]);
                        i++;
                    } else {
                        res.add(arr2[j]);
                        j++;
                    }
                }
            }
        }
        return res;
    }

    public static List<Integer> union(int[] arr1, int[] arr2) {
        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;
        Integer pre = null;
        while (i < arr1.length || j < arr2.length) {
            if (pre != null && ((i < arr1.length && arr1[i] == pre) || (j < arr2.length && arr2[j] == pre))) {
                if (i < arr1.length && arr1[i] == pre) {
                    i++;
                }
                if (j < arr2.length && arr2[j] == pre) {
                    j++;
                }
            } else {
                if (i >= arr1.length) {
                    pre = arr2[j];
                    res.add(arr2[j]);
                    j++;
                } else if (j >= arr2.length) {
                    pre = arr1[i];
                    res.add(arr1[i]);
                    i++;
                } else {
                    if (arr1[i] == arr2[j]) {
                        pre = arr1[i];
                        res.add(arr1[i]);
                        j++;
                        i++;
                    } else if (arr1[i] < arr2[j]) {
                        res.add(arr1[i]);
                        i++;
                    } else {
                        res.add(arr2[j]);
                        j++;
                    }
                }
            }
        }
        return res;
    }

    public static List<Integer> intersection(int[] arr1, int[] arr2) {
        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;
        Integer pre = null;
        while (i < arr1.length && j < arr2.length) {
            if (pre != null && (arr1[i] == pre || arr2[j] == pre)) {
                if (arr1[i] == pre) {
                    i++;
                }
                if (arr2[j] == pre) {
                    j++;
                }
            } else {
                if (arr1[i] == arr2[j]) {
                    pre = arr1[i];
                    res.add(arr1[i]);
                    j++;
                    i++;
                } else if (arr1[i] < arr2[j]) {
                    i++;
                } else {
                    j++;
                }
            }
        }
        return res;
    }


}
