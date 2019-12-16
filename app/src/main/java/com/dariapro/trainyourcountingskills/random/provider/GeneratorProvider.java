package com.dariapro.trainyourcountingskills.random.provider;

import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.random.arguments.GeneratorArguments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GeneratorProvider {

    protected final int MIN_LEVEL = 1;
    protected final int MAX_LEVEL = 3;

    protected final String[] operators = {"+", "-", "*", "/"};
    protected final String[] specialOperators = {"√"};

    public abstract Question generate(GeneratorArguments args);

    protected String generateOperator(boolean plus, boolean minus,
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
        return operators[operatorsList.get(index)];
    }

    protected int generateRandomNumber(int level) {
        Random numberRandom = new Random();
        int number = 0;
        while (number == 0) {
            number = numberRandom.nextInt((int) Math.pow(10, level));
        }
        number += 1;
        return number;
    }

}
