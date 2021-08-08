package fun.fengwk.learning.algorithm.playdatastruct.stack;

import org.junit.Test;

/**
 * @author fengwk
 */
public class StackTest {

    @Test
    public void test() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        System.out.println(stack);

        System.out.println(stack.pop());
        System.out.println(stack.peek());

        System.out.println(stack);

        while (!stack.isEmpty()) {
            stack.pop();
        }
        System.out.println(stack);
    }

}
