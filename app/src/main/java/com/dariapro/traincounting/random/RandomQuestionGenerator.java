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
    public final String[] operators = {"+", "-", "*", ":"};

    public RandomQuestionGenerator() {
    }

    public Question generateTwoRandomNumbersExample(int level) {
        Random random = new Random();
        int operatorSpecified = 1;
        if (operatorSpecified == -1)
            operatorSpecified = random.nextInt(4);
        int firstNumber = random.nextInt((int) Math.pow(10, level));
        int secondNumber = random.nextInt((int) Math.pow(10, level));
        int answer = 0;
        if (operatorSpecified == 1)
            answer = firstNumber + secondNumber;
        if (operatorSpecified == 2)
            answer = firstNumber - secondNumber;
        if (operatorSpecified == 3)
            answer = firstNumber * secondNumber;
        if (operatorSpecified == 4)
            answer = firstNumber / secondNumber;

        Question question = new Question("", firstNumber + " " +
                operators[operatorSpecified-1] + " " +
                secondNumber, String.valueOf(answer));
        return question;
    }
}
