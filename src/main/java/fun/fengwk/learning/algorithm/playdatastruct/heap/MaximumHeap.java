package fun.fengwk.learning.algorithm.playdatastruct.heap;

import fun.fengwk.learning.algorithm.playdatastruct.bst.TreePrinter;
import java.util.NoSuchElementException;

/**
 * @author fengwk
 */
public class MaximumHeap<E extends Comparable<E>> {

    private E[] arr;
    private int size;

    public MaximumHeap() {
        arr = (E[]) new Comparable[8];
    }

    public boolean isEmpty() {
        return size==0;
    }

    public void offer(E e) {
        if (size == arr.length) {
            resize(arr.length * 2);
        }

        arr[size] = e;
        siftUp(size);

        size++;
    }

    public E poll() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        E ret = arr[0];
        swap(0, size-1);
        arr[size-1] = null;
        size--;
        int threshold = arr.length / 4;
        if (threshold >= 16 && size <= threshold) {
            resize(threshold);
        }

        siftDown(0);

        return ret;
    }

    private void resize(int newSize) {
        E[] newArr = (E[]) new Comparable[newSize];
        for (int i = 0; i < size; i++) {
            newArr[i] = arr[i];
        }
        this.arr = newArr;
    }

    private void swap(int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int p = parent(i);
            if (arr[p].compareTo(arr[i]) >= 0) {
                break;
            }
            swap(p, i);
            i = p;
        }
    }

    private void siftDown(int i) {
        while (i < size) {
            int l = left(i);
            if (l >= size) {
                break;
            }

            int max = i;
            if (arr[l].compareTo(arr[i]) > 0) {
                max = l;
            }

            int r = right(i);
            if (r < size && arr[r].compareTo(arr[max]) > 0) {
                max = r;
            }

            if (max == i) {
                break;
            }

            swap(i, max);
            i=max;
        }
    }

    private int parent(int i) {
        return (i-1)/2;
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
                i -> String.valueOf(arr[i]),
                i -> left(i) < size ? left(i) : null,
                i -> right(i) < size ? right(i) : null)
                .toString(0);
    }
}
