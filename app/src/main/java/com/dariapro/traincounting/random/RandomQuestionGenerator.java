package com.dariapro.traincounting.random;

import com.dariapro.traincounting.entity.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Pleshchankova Daria
 *
 */
public class RandomQuestionGenerator {
    private final String[] operators = {"+", "-", "*", "/"};
    private final String[] otherOperators = {"√"};

    public RandomQuestionGenerator() {
    }

    public int generateOperator(boolean plus, boolean minus,
                                boolean multiply, boolean divide) {
        List<Integer> operatorsList = new ArrayList<>();
        Random randomOperator = new Random();
        if (plus) {
            operatorsList.add(0);
        }
        if (minus) {
            operatorsList.add(1);
        }
        if (multiply) {
            operatorsList.add(2);
        }
        if (divide) {
            operatorsList.add(3);
        }
        if (operatorsList.isEmpty()) {
            operatorsList.add(0);
        }
        int index = randomOperator.nextInt(operatorsList.size());
        return operatorsList.get(index);
    }

    public int generateRandomNumber(int level) {
        if (level <= 0 || level > 5) {
            level = 1;
        }
        Random numberRandom = new Random();
        int number = 0;
        while (number == 0) {
            number = numberRandom.nextInt((int) Math.pow(10, level));
        }
        return number;
    }

    public Question generateQuestion(int level, boolean plus, boolean minus,
                                     boolean multiply, boolean divide, boolean root) {
        int operatorSpecified = generateOperator(plus, minus, multiply, divide);
        int firstNumber = generateRandomNumber(level);
        int secondNumber = generateRandomNumber(level);
        int answer = 0;
        if (operatorSpecified == 0) {
            answer = firstNumber + secondNumber;
        }
        if (operatorSpecified == 1) {
            if (firstNumber < secondNumber) {
                int temp = firstNumber;
                firstNumber = secondNumber;
                secondNumber = temp;
            }
            answer = firstNumber - secondNumber;
        }
        if (operatorSpecified == 2)
            answer = firstNumber * secondNumber;
        if (operatorSpecified == 3) {
            answer = firstNumber;
            firstNumber = firstNumber * secondNumber;
        }

        int addRootToFirstNumber = 0;
        int addRootToSecondNumber = 0;
        if (root) {
            addRootToFirstNumber = new Random().nextInt(2);
            addRootToSecondNumber = new Random().nextInt(2);
        }
        String firstNumberStr = null;
        if (addRootToFirstNumber == 1) {
            firstNumber = firstNumber * firstNumber;
            firstNumberStr = otherOperators[0] + firstNumber;
        }
        else {
            firstNumberStr = String.valueOf(firstNumber);
        }
        String secondNumberStr = null;
        if (addRootToSecondNumber == 1) {
            secondNumber = secondNumber * secondNumber;
            secondNumberStr = otherOperators[0] + secondNumber;
        }
        else {
            secondNumberStr = String.valueOf(secondNumber);
        }
        return new Question("", firstNumberStr + " " +
                operators[operatorSpecified] + " " +
                secondNumberStr, String.valueOf(answer));
    }

    public Question generateExpressionFromQuestionAndNumber(int level, boolean plus, boolean minus,
                                                            boolean multiply, boolean divide,
                                                            Question question) {
        int operatorSpecified = generateOperator(plus, minus, multiply, divide);
        int secondNumber = generateRandomNumber(level);
        int answer = 0;
        int questionAnswer = Integer.parseInt(question.getRightAnswer());
        boolean changePlaces = false;
        if (operatorSpecified == 0)
            answer = questionAnswer + secondNumber;
        if (operatorSpecified == 1) {
            if (questionAnswer < secondNumber) {
                changePlaces = true;
                answer = secondNumber - questionAnswer;
            }
            else {
                answer = questionAnswer - secondNumber;
            }
        }
        if (operatorSpecified == 2)
            answer = questionAnswer * secondNumber;
        if (operatorSpecified == 3) {
            changePlaces = true;
            answer = secondNumber;
            secondNumber = secondNumber * questionAnswer;
        }

        String questionExpression;
        if (changePlaces) {
            questionExpression = secondNumber + " " + operators[operatorSpecified] +
                    " ( " + question.getQuestion() + " )";
        }
        else {
            questionExpression = "( " + question.getQuestion() + " ) " +
                    operators[operatorSpecified] + " " + secondNumber;
        }

        return new Question("", questionExpression, String.valueOf(answer));
    }

    public Question generateRandomExpression(int level) {
        //StringBuffer expression = "√4";
        Random quantityRandom = new Random();
        int quantity = quantityRandom.nextInt(2) + 2;

        Question question = generateQuestion(level, true, true, true, true, true);
        boolean plus = true;
        boolean minus = true;
        boolean multiply = true;
        boolean divide = true;
        for (int i = 0; i < quantity; i++) {
            if (Integer.parseInt(question.getRightAnswer()) == 0) {
                divide = false;
            }
            question = generateExpressionFromQuestionAndNumber(level, plus, minus, multiply, divide,
                                                               question);
        }
        return question;
    }
}
