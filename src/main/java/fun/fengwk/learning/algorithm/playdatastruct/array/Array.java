package fun.fengwk.learning.algorithm.playdatastruct.array;

/**
 * 动态数组
 *
 * @author fengwk
 */
public class Array<E> {

    private E[] arr;
    private int size;

    public Array() {
        this(16);
    }

    public Array(int initCapacity) {
        if (initCapacity <= 0) {
            throw new IllegalArgumentException();
        }

        this.arr = (E[]) new Object[initCapacity];
    }

    /**
     * 在位置i添加元素e，原先[i.., size)元素均后移一位
     *
     * @param i
     * @param e
     */
    public void add(int i, E e) {
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException();
        }

        // 没有足够空间则扩容
        if (size == arr.length) {
            resize(arr.length * 2);
        }

        for (int j = size-1; j >= i; j--) {
            arr[j+1] = arr[j];
        }
        arr[i] = e;
        size++;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    public void addLast(E e) {
        add(size, e);
    }

    public E set(int i, E e) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }

        E old = arr[i];
        arr[i] = e;
        return old;
    }

    public E setFirst(E e) {
        return set(0, e);
    }

    public E setLast(E e) {
        return set(size-1, e);
    }

    public E remove(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }

        E removed = arr[i];

        for (int j = i+1; j < size; j++) {
            arr[j-1] = arr[j];
        }
        arr[size-1] = null;
        size--;

        int threshold = arr.length / 4;
        if (threshold >= 16 && size <= threshold) {
            resize(threshold);
        }

        return removed;
    }

    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size-1);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // 重新分配内部数组大小
    private void resize(int newSize) {
        E[] newArr = (E[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newArr[i] = arr[i];
        }
        this.arr = newArr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(arr[i]);
            if (i < size-1) {
                sb.append(',').append(' ');
            }
        }
        sb.append(']');
        return sb.toString();
    }

}
