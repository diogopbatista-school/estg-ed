package Collections.MainTests.Stacks;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Stacks.ArrayStack;
import Collections.Stacks.LinkedStack;
import Collections.Stacks.StackADT;


public class PostFixCalculator {
    public static void main(String[] args) {

        StackADT<Integer> stack = new ArrayStack<>();
        StackADT<Integer> linkedStack = new LinkedStack<>();

        String string = "3 4 + 5 * 6 -";

        try {
            System.out.println("Resultado: " + calculate(string, stack));
            System.out.println("Resultado: " + calculate(string, linkedStack));
        } catch (EmptyCollectionException e) {
            System.out.println(e.getMessage());
        }


    }

    public static int calculate(String string, StackADT<Integer> stack) throws EmptyCollectionException {

        String[] tokens = string.split(" ");
        for (String token : tokens) {
            if (token.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (token.equals("-")) {
                stack.push(-stack.pop() + stack.pop());
            } else if (token.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (token.equals("/")) {
                stack.push((int) (1 / (1.0 * stack.pop()) * stack.pop()));
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();



    }
}
