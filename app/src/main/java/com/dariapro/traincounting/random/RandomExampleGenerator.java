package com.dariapro.traincounting.random;

import com.dariapro.traincounting.entity.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomExampleGenerator {
    public final String[] operators = {"+", "-", "*", ":"};

    public RandomExampleGenerator() {
    }

    public Question generateTwoRandomNumbersExample(int level, int operatorSpecified) {
        Random random = new Random();
        int oper = operatorSpecified;
        if (operatorSpecified == -1)
            oper = random.nextInt(4);
        int firstNumber = random.nextInt((int) Math.pow(10, level));
        int secondNumber = random.nextInt((int) Math.pow(10, level));
        int answer = 0;
        if (oper == 1)
            answer = firstNumber + secondNumber;
        if (oper == 2)
            answer = firstNumber - secondNumber;
        if (oper == 3)
            answer = firstNumber * secondNumber;
        if (oper == 4)
            answer = firstNumber / secondNumber;

        Question question = new Question("", firstNumber + " " +
                operators[oper-1] + " " +
                secondNumber, String.valueOf(answer));
        return question;
    }
}
