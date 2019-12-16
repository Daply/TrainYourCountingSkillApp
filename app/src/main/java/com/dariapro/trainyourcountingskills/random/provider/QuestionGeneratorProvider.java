package com.dariapro.trainyourcountingskills.random.provider;

import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.random.arguments.GeneratorArguments;
import com.dariapro.trainyourcountingskills.random.entity.QuestionCompact;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionGeneratorProvider extends GeneratorProvider {

    @Override
    public Question generate(GeneratorArguments args) {
        return generateQuestion(args);
    }

    protected QuestionCompact createQuestionCompact(int firstNumber, int secondNumber, String operatorSpecified) {
        int answer = 0;
        if (operatorSpecified.equals("+")) {
            answer = firstNumber + secondNumber;
        }
        if (operatorSpecified.equals("-")) {
            if (firstNumber < secondNumber) {
                int temp = firstNumber;
                firstNumber = secondNumber;
                secondNumber = temp;
            }
            answer = firstNumber - secondNumber;
        }
        if (operatorSpecified.equals("*"))
            answer = firstNumber * secondNumber;
        if (operatorSpecified.equals("/")) {
            answer = firstNumber;
            firstNumber = firstNumber * secondNumber;
        }
        return new QuestionCompact(firstNumber, secondNumber, operatorSpecified, answer);
    }

    protected void addSpecialOperatorsToQuestionCompact(QuestionCompact questionCompact, GeneratorArguments args) {
        if (args.isRoot()) {
            addSpecialOperatorToQuestionCompact(questionCompact, specialOperators[0]);
        }
    }

    protected void addSpecialOperatorToQuestionCompact(QuestionCompact questionCompact, String specialOperatorSpecified) {
        int addSpecialOperatorFirstNumber = 0;
        int addSpecialOperatorToSecondNumber = 0;
        addSpecialOperatorFirstNumber = new Random().nextInt(2);
        addSpecialOperatorToSecondNumber = new Random().nextInt(2);
        if (addSpecialOperatorFirstNumber == 1) {
            int newValue = processSpecialOperator(questionCompact.getFirstNumber(), specialOperatorSpecified);
            if (newValue != questionCompact.getFirstNumber()) {
                questionCompact.setFirstNumber(newValue);
                questionCompact.putNewFirstNumberSpecialOperator(specialOperatorSpecified);
            }
        }
        if (addSpecialOperatorToSecondNumber == 1) {
            int newValue = processSpecialOperator(questionCompact.getSecondNumber(), specialOperatorSpecified);
            if (newValue != questionCompact.getSecondNumber()) {
                questionCompact.setSecondNumber(newValue);
                questionCompact.putNewSecondNumberSpecialOperator(specialOperatorSpecified);
            }
        }
    }

    private int processSpecialOperator(int number, String specialOperator) {
        int result = 0;
        if (specialOperator.equals(specialOperators[0])) {
            result = number * number;
            int currentLevel = getNumberOfDigits(number);
            int resultLevel = getNumberOfDigits(result);
            if (resultLevel >= currentLevel*2) {
                result = number;
            }
        }
        return result;
    }

    private int getNumberOfDigits(int number) {
        int count = 0;
        while (number != 0) {
            number /= 10;
        }
        return count;
    }

    protected Question generateQuestion(GeneratorArguments args) {
        String operatorSpecified = generateOperator(args.isPlus(),
                args.isMinus(), args.isMultiply(), args.isDivide());
        int firstNumber = generateRandomNumber(args.getLevel());
        int secondNumber = generateRandomNumber(args.getLevel());

        QuestionCompact questionCompact = createQuestionCompact(firstNumber, secondNumber, operatorSpecified);
        if (args.isRoot()) {
            addSpecialOperatorsToQuestionCompact(questionCompact, args);
        }

        return new Question("", questionCompact.getFirstNumberToString() + " " +
                operatorSpecified + " " +
                questionCompact.getSecondNumberToString(), String.valueOf(questionCompact.getAnswer()));
    }

}
