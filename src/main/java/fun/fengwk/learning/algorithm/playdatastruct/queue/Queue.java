package fun.fengwk.learning.algorithm.playdatastruct.queue;

import java.util.NoSuchElementException;

/**
 * @author fengwk
 */
public class Queue<E> {

    class Node {

        E val;
        Node next;

        public Node(E val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    // 队头
    private Node head;
    // 队尾
    private Node tail;

    public boolean isEmpty() {
        return head == null;
    }

    /**
     * 入队元素
     *
     * @param e
     */
    public void enqueue(E e) {
        if (head == null) {
            head = tail = new Node(e, null);
        } else {
            tail = tail.next = new Node(e, null);
        }
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

        E retVal = head.val;
        Node newHead = head.next;
        head.val = null;
        head.next = null;
        if (newHead == null) {
            head = tail = null;
        } else {
            head = newHead;
        }
        return retVal;
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

        return head.val;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "front []";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("front [").append(head.val);
        Node cur = head.next;
        while (cur != null) {
            sb.append("->").append(cur.val);
            cur = cur.next;
        }
        sb.append(']');
        return sb.toString();
    }
}
