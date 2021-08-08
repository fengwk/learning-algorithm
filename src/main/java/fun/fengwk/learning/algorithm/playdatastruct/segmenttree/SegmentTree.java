package fun.fengwk.learning.algorithm.playdatastruct.segmenttree;

import fun.fengwk.learning.algorithm.playdatastruct.bst.TreePrinter;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author fengwk
 */
public class SegmentTree<E, T> {

    private T[] tree;
    private E[] data;
    private Function<E, T> converter;
    private BiFunction<T, T, T> merger;

    public SegmentTree(E[] data, Function<E, T> converter, BiFunction<T, T, T> merger) {
        this.data = data;
        this.converter = converter;
        this.merger = merger;
        this.tree = (T[]) new Object[data.length*4];

        buildTree(0, 0, data.length);
    }

    // 使用[dataLeft, dataRight)来构建线段树tree[treeIndex]
    private void buildTree(int treeIndex, int dataLeft, int dataRight) {
        if (dataLeft + 1 == dataRight) {
            tree[treeIndex] = converter.apply(data[dataLeft]);
            return;
        }

        int dataMid = (dataRight - dataLeft) / 2 + dataLeft;
        buildTree(left(treeIndex), dataLeft, dataMid);
        buildTree(right(treeIndex), dataMid, dataRight);

        tree[treeIndex] = merger.apply(tree[left(treeIndex)], tree[right(treeIndex)]);
    }

    // 获取区间[i,j)的统计值
    public T range(int i, int j) {
        return doRange(0, 0, data.length, i, j);
    }

    private T doRange(int treeIndex, int dataLeft, int dataRight, int i, int j) {
        if (dataLeft >= i && dataRight <= j) {
            return tree[treeIndex];
        }

        int dataMid = (dataRight - dataLeft) / 2 + dataLeft;

        // [dataLeft, dataMid)
        // [dataMid, dataRight)
        // [i, j)

        T l = null, r = null;
        if (i < dataMid) {
            l = doRange(left(treeIndex), dataLeft, dataMid, i, j);
        }
        if (j > dataMid) {
            r = doRange(right(treeIndex), dataMid, dataRight, i, j);
        }

        if (l == null) {
            return r;
        }

        if (r == null) {
            return l;
        }

        return merger.apply(l, r);
    }

    private int left(int i) {
        return 2*i+1;
    }

    private int right(int i) {
        return 2*i+2;
    }

    @Override
    public String toString() {
        return new TreePrinter<Integer>(
                i -> String.valueOf(tree[i]),
                i -> left(i) >= tree.length || tree[left(i)] == null ? null : left(i),
                i -> right(i) >= tree.length || tree[right(i)] == null ? null : right(i))
                .toString(0);
    }

}
