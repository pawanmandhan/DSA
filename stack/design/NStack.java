package stack.design;

public class NStack {
    private int[] arr;
    private int[] top;
    private int[] next;

    private int stackCount, size;

    private int freespot;

    // Initialize your data structure.
    public NStack(int stackCount, int size) {
        this.stackCount = stackCount;
        this.size = size;
        arr = new int[size];
        top = new int[stackCount];
        next = new int[size];

        // top initialise
        for (int i = 0; i < stackCount; i++) {
            top[i] = -1;
        }

        // next initialise
        for (int i = 0; i < size; i++) {
            next[i] = i + 1;
        }
        // update last index value to -1
        next[size - 1] = -1;

        // initialise freespot
        freespot = 0;
    }

    // Pushes 'X' into the Mth stack. Returns true if it gets pushed into the stack,
    // and false otherwise.
    public boolean push(int num, int stackNumber) {
        // check for overflow
        if (freespot == -1) {
            return false;
        }
        // find index
        int index = freespot;
        // insert element into array
        arr[index] = num;
        // update freespot
        freespot = next[index];
        // update next;
        next[index] = top[stackNumber - 1];
        // update top
        top[stackNumber - 1] = index;
        return true;
    }

    // Pops top element from Mth Stack. Returns -1 if the stack is empty, otherwise
    // returns the popped element.
    public int pop(int stackNumber) {
        // check underflow condition
        if (top[stackNumber - 1] == -1) {
            return -1;
        }
        int index = top[stackNumber - 1];
        top[stackNumber - 1] = next[index];
        next[index] = freespot;
        freespot = index;
        return arr[index];
    }
};
