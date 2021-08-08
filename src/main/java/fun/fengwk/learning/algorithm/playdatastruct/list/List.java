package fun.fengwk.learning.algorithm.playdatastruct.list;

/**
 * @author fengwk
 */
public class List<E> {

    class Node {

        E val;
        Node next;

        public Node(E val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    private Node head;
    private int size;

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

        if (i == 0) {
            head = new Node(e, head);
        } else {
            Node precursor = findNode(i-1);
            precursor.next = new Node(e, precursor.next);
        }

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

        Node node = findNode(i);
        E old = node.val;
        node.val = e;
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

        E removed;
        if (size == 1) {
            removed = head.val;
            head = null;
        } else if (i == 0) {
            removed = head.val;
            head = head.next;
        } else {
            Node precursor = findNode(i-1);
            removed = precursor.next.val;
            precursor.next = precursor.next.next;
        }

        size--;
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

    // 调用当前方法必须先保证链表非空，且i在范围内
    private Node findNode(int i) {
        Node found = head;
        for (int j = 1; j <= i; j++) {
            found = found.next;
            // 每次循环结束found就会变为j位置的值
            // 最终found会变为i位置的值
        }
        return found;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(head.val);
        Node cur = head.next;
        while (cur != null) {
            sb.append("->").append(cur.val);
            cur = cur.next;
        }
        sb.append(']');
        return sb.toString();
    }

}
