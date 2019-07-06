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
    public final String[] operators = {"+", "-", "*", "/"};

    public RandomQuestionGenerator() {
    }

    public Question generateTwoRandomNumbersExample(int level,
                                                    boolean plus,
                                                    boolean minus,
                                                    boolean multiply,
                                                    boolean divide) {
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
        int operatorSpecified = 1;
        int index = randomOperator.nextInt(operatorsList.size());
        operatorSpecified = operatorsList.get(index);

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

    public Question generateRandomExpression(int level) {
        String expression = "Sqrt(4)";
        int answer = 2;
        Question question = new Question("", expression, String.valueOf(answer));
        return question;
    }
}
