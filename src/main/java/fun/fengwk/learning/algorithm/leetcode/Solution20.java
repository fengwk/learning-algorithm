package fun.fengwk.learning.algorithm.leetcode;

/**
 * @author fengwk
 */
public class Solution20 {

    public static void main(String[] args) {
        System.out.println(new Solution20().isValid("]"));
    }

    public boolean isValid(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    static class Stack<E> {

        class Node {

            E val;
            Node next;

            public Node(E val, Node next) {
                this.val = val;
                this.next = next;
            }
        }

        private Node head;

        /**
         * 入栈元素
         *
         * @param e
         */
        public void push(E e) {
            if (head == null) {
                head = new Node(e, null);
            } else {
                head = new Node(e, head);
            }
        }

        /**
         * 出栈元素，没有元素返回null
         *
         * @return
         */
        public E pop() {
            if (head == null) {
                return null;
            }

            E popVal = head.val;
            Node newHead = head.next;
            head.val = null;
            head.next = null;
            head = newHead;
            return popVal;
        }

        /**
         * 查看栈顶元素，没有元素返回null
         *
         * @return
         */
        public E peek() {
            if (head == null) {
                return null;
            }

            return head.val;
        }

        /**
         * 检查栈中是否还有元素
         *
         * @return
         */
        public boolean isEmpty() {
            return head == null;
        }

        @Override
        public String toString() {
            if (head == null) {
                return "top []";
            }

            StringBuilder sb = new StringBuilder();
            sb.append("top [").append(head.val);
            Node cur = head.next;
            while (cur != null) {
                sb.append("->").append(cur.val);
                cur = cur.next;
            }
            sb.append(']');
            return sb.toString();
        }
    }


}
