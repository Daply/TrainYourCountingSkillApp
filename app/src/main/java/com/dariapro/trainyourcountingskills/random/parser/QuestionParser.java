package com.dariapro.trainyourcountingskills.random.parser;

import com.dariapro.trainyourcountingskills.entity.Question;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pleshchankova Daria
 *
 */
public class QuestionParser extends Parser {

    public boolean questionIsValid(Question question) {
        Deque<String> tokens = tokenize(question.getQuestion());
        int numberOfValues = 0;
        int numberOfOperators = 0;
        for (String tok: tokens) {
            if (tok.matches("[-+*/]")) {
                numberOfOperators++;
            }
            if (tok.matches("[0-9]+")) {
                numberOfValues++;
            }
        }
        if (numberOfValues > numberOfOperators) {
            return true;
        }
        return false;
    }

    @Override
    public String parse(Question question) {
        String questionCondition = "(" + question.getQuestion() + ")";
        Deque<String> tokens = tokenize(questionCondition);

        Stack<String> roots = new Stack<String>();
        Stack<String> operators = new Stack<String>();
        Stack<String> values = new Stack<String>();
        for (String tok: tokens) {
            if (tok.matches("[√]")) {
                roots.push(tok);
            }
            if (tok.matches("[-+*/]")) {
                operators.push(tok);
            }
            if (tok.matches("[0-9]+")) {
                if (!roots.isEmpty()) {
                    int value = Integer.parseInt(tok);
                    tok = String.valueOf((int)Math.sqrt(value));
                    roots.pop();
                }
                values.push(tok);
            }
            if (tok.equals(")")) {
                int secondNumber = 1;
                if (!values.isEmpty()) {
                    secondNumber = Integer.parseInt(values.pop());
                }
                else {
                    try {
                        throw new Exception("Not valid question!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                int firstNumber = 1;
                if (!values.isEmpty()) {
                    firstNumber = Integer.parseInt(values.pop());
                }
                else {
                    try {
                        throw new Exception("Not valid question!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                int result = 0;
                if (!operators.isEmpty()) {
                    String operator = operators.pop();
                    if (operator.equals("+")) {
                        result = firstNumber + secondNumber;
                    }
                    if (operator.equals("-")) {
                        result = firstNumber - secondNumber;
                    }
                    if (operator.equals("*")) {
                        result = firstNumber * secondNumber;
                    }
                    if (operator.equals("/")) {
                        result = firstNumber / secondNumber;
                    }
                }
                values.push(String.valueOf(result));
            }
        }
        return values.pop();
    }

    private Deque<String> tokenize(String input) {
        Deque<String> tokens = new LinkedList<>();
        Pattern pattern = Pattern.compile("[-+*/√\\(\\)]|[0-9]*(\\.?[0-9]+)");
        Matcher m = pattern.matcher(input);
        while (m.find()) {
            tokens.add(m.group());
        }
        return tokens;
    }
}
