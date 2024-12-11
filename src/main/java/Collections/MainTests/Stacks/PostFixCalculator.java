package Collections.MainTests.Stacks;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Stacks.ArrayStack;
import Collections.Stacks.LinkedStack;
import Collections.Stacks.StackADT;

/**
 * PostFixCalculator tests the PostFixCalculator class.
 */
public class PostFixCalculator {
    /**
     * Main method
     * @param args arguments
     */
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

    /**
     * Method that calculates the result of a postfix expression
     * @param string the postfix expression
     * @param stack the stack to use
     * @return the result of the expression
     * @throws EmptyCollectionException
     */
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
