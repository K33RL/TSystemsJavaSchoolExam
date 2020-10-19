package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here
        Double result;
        if (statement == null) {
            return null;
        }
        if (statement.contains("--")
                || statement.contains("++")
                || statement.contains("//")
                || statement.contains("**")
                || statement.contains(",")
                || statement.contains("..")
                || statement.isEmpty()) {
            return null;
        }
        ExpressionParser n = new ExpressionParser();
        List<String> expression = n.parse(statement);
        boolean flag = n.flag;
        if (flag) {
            for (String x : expression) System.out.print(x + " ");
            System.out.println();
            System.out.println(calc(expression).toString());
        }
        try {
            result = calc(expression);
        }catch (NullPointerException e){
            return null;
        }


        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        DecimalFormat format = new DecimalFormat("#.#####");
        format.setDecimalSeparatorAlwaysShown(false);
        Locale.setDefault(Locale.US);

        if (Double.isInfinite(result)) {
            return null;
        }

        return format.format(result);
 
    }

    public static Double calc(List<String> postfix) {
        Deque<Double> stack = new ArrayDeque<Double>();
        for (String x : postfix) {
            if (x.equals("+")) stack.push(stack.pop() + stack.pop());
            else if (x.equals("-")) {
                Double b = stack.pop(), a = stack.pop();
                stack.push(a - b);
            } else if (x.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (x.equals("/")) {
                Double b = stack.pop(), a = stack.pop();
                stack.push(a / b);
            } else if (x.equals("u-")) {
                stack.push(-stack.pop());
            } else stack.push(Double.valueOf(x));
        }
        return stack.pop();
    }

}

class ExpressionParser {
    private static String operators = "+-*/";
    private static String delimiters = "() " + operators;
    public static boolean flag = true;

    private static boolean isDelimiter(String token) {
        if (token.length() != 1) return false;
        for (int i = 0; i < delimiters.length(); i++) {
            if (token.charAt(0) == delimiters.charAt(i)) return true;
        }
        return false;
    }

    private static boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private static int priority(String token) {
        if (token.equals("(")) return 1;
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*") || token.equals("/")) return 3;
        return 4;
    }

    public static List<String> parse(String infix) {
        List<String> postfix = new ArrayList<String>();
        Deque<String> stack = new ArrayDeque<String>();
        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);
        String prev = "";
        String curr = "";
        while (tokenizer.hasMoreTokens()) {
            curr = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                System.out.println("Некорректное выражение.");
                flag = false;
                return null;
            }
            if (curr.equals(" ")) continue;
            else if (isDelimiter(curr)) {
                if (curr.equals("(")) stack.push(curr);
                else if (curr.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            System.out.println("Скобки не согласованы.");
                            flag = false;
                            return null;
                        }
                    }
                    stack.pop();
                } else {
                    if (curr.equals("-") && (prev.equals("") || (isDelimiter(prev) && !prev.equals(")")))) {
                        // унарный минус
                        curr = "u-";
                    } else {
                        while (!stack.isEmpty() && (priority(curr) <= priority(stack.peek()))) {
                            postfix.add(stack.pop());
                        }

                    }
                    stack.push(curr);
                }

            } else {
                postfix.add(curr);
            }
            prev = curr;
        }

        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) postfix.add(stack.pop());
            else {
                System.out.println("Скобки не согласованы.");
                flag = false;
                return null;
            }
        }
        return postfix;
    }
}
