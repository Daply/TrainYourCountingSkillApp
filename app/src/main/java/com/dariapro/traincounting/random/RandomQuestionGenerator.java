package com.dariapro.traincounting.random;

import com.dariapro.traincounting.entity.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

/**
 * @author Pleshchankova Daria
 *
 */
public class RandomQuestionGenerator {
    private final String[] operators = {"+", "-", "*", "/"};
    private final String[] otherOperators = {"√"};

    private Map<String, Integer> question = null;

    public RandomQuestionGenerator() {
    }

    public Question generateQuestion(int level, boolean plus, boolean minus,
                                     boolean multiply, boolean divide) {
        int operatorSpecified = generateOperator(plus, minus, multiply, divide);
        return generateTwoRandomNumbers(level, operatorSpecified);
    }

    public int generateOperator(boolean plus, boolean minus,
                                boolean multiply, boolean divide) {
        List<Integer> operatorsList = new ArrayList<Integer>();
        Random randomOperator = new Random();
        if (plus) {
            operatorsList.add(1);
        }
        if (minus) {
            operatorsList.add(2);
        }
        if (multiply) {
            operatorsList.add(3);
        }
        if (divide) {
            operatorsList.add(4);
        }
        if (operatorsList.isEmpty()) {
            operatorsList.add(1);
        }
        int index = randomOperator.nextInt(operatorsList.size());
        int operatorSpecified = operatorsList.get(index);
        return operatorSpecified;
    }

    public Question generateTwoRandomNumbers(int level, int operatorSpecified) {
        Random numberRandom = new Random();
        int firstNumber = 0;
        while (firstNumber == 0) {
            firstNumber = numberRandom.nextInt((int) Math.pow(10, level));
        }
        int secondNumber = 0;
        while (secondNumber == 0) {
            secondNumber = numberRandom.nextInt((int) Math.pow(10, level));
        }
        int answer = 0;
        if (operatorSpecified == 1)
            answer = firstNumber + secondNumber;
        if (operatorSpecified == 2) {
            if (firstNumber < secondNumber) {
                int temp = firstNumber;
                firstNumber = secondNumber;
                secondNumber = temp;
            }
            answer = firstNumber - secondNumber;
        }
        if (operatorSpecified == 3)
            answer = firstNumber * secondNumber;
        if (operatorSpecified == 4) {
            answer = firstNumber;
            firstNumber = firstNumber * secondNumber;
        }

        Question question = new Question("", firstNumber + " " +
                operators[operatorSpecified-1] + " " +
                secondNumber, String.valueOf(answer));
        return question;
    }

    public Question generateExpressionFromQuestionAndNumber(int level, int operatorSpecified,
                                                            Question question) {
        Random numberRandom = new Random();
        int secondNumber = 0;
        while (secondNumber == 0) {
            secondNumber = numberRandom.nextInt((int) Math.pow(10, level));
        }
        int answer = 0;

        int questionAnswer = Integer.parseInt(question.getRightAnswer());
        boolean changePlaces = false;
        if (operatorSpecified == 1)
            answer = questionAnswer + secondNumber;
        if (operatorSpecified == 2) {
            if (questionAnswer < secondNumber) {
                changePlaces = true;
                answer = secondNumber - questionAnswer;
            }
            else {
                answer = questionAnswer - secondNumber;
            }
        }
        if (operatorSpecified == 3)
            answer = questionAnswer * secondNumber;
        if (operatorSpecified == 4) {
            changePlaces = true;
            answer = secondNumber;
            secondNumber = secondNumber * questionAnswer;
        }

        String questionExpression = new String();
        if (changePlaces) {
            questionExpression = secondNumber + " " + operators[operatorSpecified-1] +
                    " ( " + question.getQuestion() + " )";
        }
        else {
            questionExpression = "( " + question.getQuestion() + " ) " +
                    operators[operatorSpecified-1] + " " + secondNumber;
        }

        Question newQuestion = new Question("", questionExpression, String.valueOf(answer));
        return newQuestion;
    }

    public Question generateRandomExpression(int level) {
        //StringBuffer expression = "√4";
        Random quantityRandom = new Random();
        int quantity = quantityRandom.nextInt(2) + 2;

        Question question = generateQuestion(level, true, true, true, true);
        int operator = 0;
        boolean plus = true;
        boolean minus = true;
        boolean multiply = true;
        boolean divide = true;
        for (int i = 0; i < quantity; i++) {
            if (Integer.parseInt(question.getRightAnswer()) == 0) {

            }
            operator = generateOperator(plus, minus, multiply, divide);
            question = generateExpressionFromQuestionAndNumber(level, operator, question);
        }
        return question;
    }
}
