package stack;

import java.util.Stack;

public class StackQuestions {

    public static String reverseString(String str) {
        Stack<Character> stack = new Stack<>();
        String ans = "";
        
        for (char ch : str.toCharArray())
            stack.push(ch);

        while (!stack.empty())
            ans += stack.pop();
        return ans;
    }

    public static boolean balancedParenthesis(String str) {
        Stack<Character> stack = new Stack<>();
        
        for(char curr : str.toCharArray()) {
              // if closing bracket
              if(curr == ')' || curr == ']' || curr == '}') {
                 if(stack.empty())
                     return false;
                 
                 char currPopped = stack.pop();
                 
                 if((currPopped == '(' && curr != ')') ||
                    (currPopped == '{' && curr != '}') ||
                    (currPopped == '[' && curr != ']')) {
                     return false;
                 }
              } else {
                  // if opening bracket
                  stack.push(curr);
              }
        }
        return stack.empty();
    }

    public static Stack<Integer> pushAtBottom(Stack <Integer> stack, int x) {
        pushAtBottomUtil(stack, x);
        return stack;
    }
    
    private static void pushAtBottomUtil(Stack<Integer> stack, int x) {
      if(stack.isEmpty()) {
          stack.push(x);
          return;
      }
      
      int curr = stack.pop();
      pushAtBottomUtil(stack, x);
      stack.push(curr);
    }

    public static void deleteMid(Stack<Integer>stack,int size) {
        deleteMidUtil(stack, size/2);
    } 
    
    private static void deleteMidUtil(Stack<Integer>stack,int mid){
        if(mid == 0) {
            stack.pop();
            return;
        }
        int curr = stack.pop();
        deleteMidUtil(stack, mid-1);
        stack.push(curr);
    }

    public static void reverseStack(Stack<Integer> stack) {
        if (stack.empty())
            return;

        int num = stack.pop();
        reverseStack(stack);
        pushAtBottomUtil(stack, num);
    }

    public static void sortStack(Stack<Integer> stack) {
        if (stack.isEmpty())
            return;

        int num = stack.pop();
        sortStack(stack);
        sortedInsert(stack, num);
    }
     
    private static void sortedInsert(Stack<Integer> stack, int num) {
        if (stack.empty() || num >= stack.peek()) {
            stack.push(num);
            return;
        }

        int curr = stack.pop();
        sortedInsert(stack, num);
        stack.push(curr);
    }
    
    public static boolean findRedundantBrackets(String str) {
        Stack<Character> stack = new Stack<>();
        for (char ch : str.toCharArray()) {
            if (isOperator(ch, '(', '+', '-', '*', '/')) {
                stack.push(ch);
            } else if (ch == ')') { // got closing bracket (ignore lowercase letter)
                // if there are no operator in stack, bracket is redundant
                if(stack.peek() == '(')
                    return true;

                // if there are some operators in stack, bracket is non-redundant
                // remove all operators
                while (!stack.isEmpty() && 
                    isOperator(stack.peek(),  '+', '-', '*', '/')) {
                    stack.pop();
                }
                // remove opening bracket
                stack.pop();
            }
        }
        return false;
    }
    
    private static boolean isOperator(char target, char ...options) {
        for(char option : options) {
            if(target == option) 
                return true;
        }
        return false;
    }

    public static int findMinimumCost(String str) {

        // odd condition
        if (str.length() % 2 == 1) {
            return -1;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (ch == '{')
                stack.push(ch);
            else {
                // ch is closed brace
                if (!stack.empty() && stack.peek() == '{') {
                    stack.pop();
                } else {
                    stack.push(ch);
                }
            }
        }

        // stack contains invalid expression
        int a = 0, b = 0;
        while (!stack.isEmpty()) {
            if (stack.peek() == '{') {
                b++;
            } else {
                a++;
            }
            stack.pop();
        }

        int ans = (a + 1) / 2 + (b + 1) / 2;
        return ans;
    }

    private static int[] nextSmallerElement(int[] arr, int n) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int[] ans = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            int currHeight = arr[i];
            while (stack.peek() != -1 && arr[stack.peek()] >= currHeight) {
                stack.pop();
            }
            // ans is stack ka top
            ans[i] = stack.peek();
            stack.push(i);
        }
        return ans;
    }

    private static int[] prevSmallerElement(int[] arr, int n) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            int currHeight = arr[i];
            while (stack.peek() != -1 && arr[stack.peek()] >= currHeight) {
                stack.pop();
            }
            // ans is stack ka top
            ans[i] = stack.peek();
            stack.push(i);
        }
        return ans;
    }

    public static int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] next = nextSmallerElement(heights, n);
        int[] prev = prevSmallerElement(heights, n);

        int area = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int currHeight = heights[i];

            if (next[i] == -1) {
                next[i] = n;
            }
            int b = next[i] - prev[i] - 1;
            int newArea = currHeight * b;
            area = Math.max(area, newArea);
        }
        return area;
    }

    private static boolean knows(int[][] matrix, int row, int col, int n) {
        return matrix[row][col] == 1;
    }

    public static int celebrity(int[][] matrix, int n) {
        Stack<Integer> stack = new Stack<>();
        // step1: push all element in stack
        for (int i = 0; i < n; i++) {
            stack.push(i);
        }

        // step2: get 2 elements and copare them

        while (stack.size() > 1) {

            int a = stack.peek();
            stack.pop();

            int b = stack.peek();
            stack.pop();

            if (knows(matrix, a, b, n)) {
                stack.push(b);
            } else {
                stack.push(a);
            }
        }
        int ans = stack.peek();
        // step3: single element in stack is potential celeb
        // so verify it

        int zeroCount = 0;

        for (int i = 0; i < n; i++) {
            if (matrix[ans][i] == 0)
                zeroCount++;
        }

        // all zeroes
        if (zeroCount != n)
            return -1;

        // column check
        int oneCount = 0;

        for (int i = 0; i < n; i++) {
            if (matrix[i][ans] == 1)
                oneCount++;
        }

        if (oneCount != n - 1)
            return -1;

        return ans;
    }

    public static int maxArea(int matrix[][], int n, int m) {
        // compute area for first row
        int area = largestRectangleArea(matrix[0]);

        for (int row = 1; row < n; row++) {
            for (int col = 0; col < m; col++) {

                // row udpate: by adding previous row'stack value
                if (matrix[row][col] != 0)
                    matrix[row][col] = matrix[row][col] + matrix[row - 1][col];
                else
                    matrix[row][col] = 0;
            }

            // entire row is updated now
            area = Math.max(area, largestRectangleArea(matrix[row]));

        }
        return area;
    }
}
