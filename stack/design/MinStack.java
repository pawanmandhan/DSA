package stack.design;

import java.util.Stack;

public class MinStack {

    private Stack<Integer> stack = new Stack<>();
    private int minItem = Integer.MAX_VALUE;

    public void push(int data) {
        // for first element
        if (stack.empty()) {
            stack.push(data);
            minItem = data;
        } else {
            if (data < minItem) {
                stack.push(2 * data - minItem);
                minItem = data;
            } else {
                stack.push(data);
            }
        }
    }

    public int pop() {
        if (stack.empty()) {
            return -1;
        }

        int curr = stack.peek();
        stack.pop();
        if (curr > minItem) {
            return curr;
        } else {
            int prevMin = minItem;
            int val = 2 * minItem - curr;
            minItem = val;
            return prevMin;
        }
    }

    public int top() {
        if (stack.empty())
            return -1;

        int curr = stack.peek();
        if (curr < minItem) {
            return minItem;
        } else {
            return curr;
        }
    }

    public boolean isEmpty() {
        return stack.empty();
    }

    public int getMin() {
        if (stack.empty())
            return -1;

        return minItem;
    }
}
