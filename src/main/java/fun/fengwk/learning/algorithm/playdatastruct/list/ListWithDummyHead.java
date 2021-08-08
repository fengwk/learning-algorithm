package fun.fengwk.learning.algorithm.playdatastruct.list;

/**
 * @author fengwk
 */
public class ListWithDummyHead<E> {

    class Node {

        E val;
        Node next;

        public Node(E val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    private Node dummyHead = new Node(null, null);
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

        Node precursor = findNode(i-1);
        precursor.next = new Node(e, precursor.next);
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

        Node precursor = findNode(i-1);
        E removed = precursor.next.val;
        precursor.next = precursor.next.next;
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

    private Node findNode(int i) {
        Node found = dummyHead;
        for (int j = 0; j <= i; j++) {
            found = found.next;
        }
        return found;
    }

    @Override
    public String toString() {
        if (dummyHead.next == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(dummyHead.next.val);
        Node cur = dummyHead.next.next;
        while (cur != null) {
            sb.append("->").append(cur.val);
            cur = cur.next;
        }
        sb.append(']');
        return sb.toString();
    }

}
