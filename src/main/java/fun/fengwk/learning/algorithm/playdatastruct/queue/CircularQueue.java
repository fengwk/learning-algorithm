package fun.fengwk.learning.algorithm.playdatastruct.queue;

import java.util.NoSuchElementException;

/**
 * @author fengwk
 */
public class CircularQueue<E> {

    private E[] arr;
    // 指向下一个入队位置
    private int tail;
    // 指向下一个出队位置
    private int head;

    public CircularQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.arr = (E[]) new Object[capacity+1];
    }

    public boolean isEmpty() {
        return tail == head;
    }

    public boolean isFull() {
        return (tail + 1) % arr.length == head;
    }

    /**
     * 入队元素
     *
     * @param e
     * @throws IllegalStateException
     */
    public void enqueue(E e) {
        if (isFull()) {
            throw new IllegalStateException("queue is full");
        }

        arr[tail % arr.length] = e;
        tail++;
    }

    /**
     * 出队元素
     *
     * @return
     * @throws NoSuchElementException
     */
    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        E ret = arr[head];
        arr[head] = null;
        head = (head + 1) % arr.length;
        return ret;
    }

    /**
     * 查看队首元素
     *
     * @return
     * @throws NoSuchElementException
     */
    public E front() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return arr[head];
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "front []";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("front [").append(arr[head]);
        for (int i = head +1; i != tail;) {
            sb.append(", ").append(arr[i]);
            i = (i+1) % arr.length;
        }
        sb.append(']');
        return sb.toString();
    }
}
