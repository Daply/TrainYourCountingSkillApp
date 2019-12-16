package com.dariapro.trainyourcountingskills.random.provider;

import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.random.arguments.GeneratorArguments;

import java.util.Random;

public class ProblemGeneratorProvider extends GeneratorProvider {

    @Override
    public Question generate(GeneratorArguments args) {
        return generateRandomPercentProblem(args.getLevel());
    }

    protected Question generateRandomPercentProblem(int level) {
        if (level >= MIN_LEVEL && level <= (MAX_LEVEL - 1
        )) {
            return generateSimplePercentProblem(level);
        }
        else if (level == MAX_LEVEL) {
            Random rand = new Random();
            int questionType = 1 + rand.nextInt(2);
            switch (questionType) {
                case 1:
                    return generateSimplePercentProblem(level);
                case 2:
                    return generateComplexPercentProblem(level);
            }
        }
        return null;
    }

    protected Question generateSimplePercentProblem(int level) {
        Random rand = new Random();
        int start = 10;
        int last = (int) Math.pow(10, level);
        int answer = start + rand.nextInt(last);
        int percent = 0;
        double percentDouble = 0.1;
        int number = 0;
        double numberDouble = 0;
        double answerDouble = 0;
        while (!checkIfNoPrecision(percentDouble) &&
                percentDouble != 0) {
            number = start + rand.nextInt((int)Math.pow(10, 2));
            if (answer > number) {
                int tmp = answer;
                answer = number;
                number = tmp;
            }
            numberDouble = (double)number;
            answerDouble = (double)answer;
            percentDouble = (answerDouble / numberDouble)*100;
        }
        percent = (int) percentDouble;
        int questionType = 1 + rand.nextInt(3);
        switch(questionType) {
            case 1:
                return new Question("",
                        "What is " + percent + "% of " + number + "?",
                        String.valueOf(answer));
            case 2:
                return new Question("",
                        "If " + percent + "% of some number is " + answer + ", what is the number?",
                        String.valueOf(number));
            case 3:
                return new Question("",
                        "What percentage of " + number + " equals " + answer + "?",
                        String.valueOf(percent));
        }
        return null;
    }

    protected Question generateComplexPercentProblem(int level) {
        Random rand = new Random();
        int start = 10;
        int last = (int) Math.pow(10, level);
        int answer = start + rand.nextInt(last);
        int number = 0;
        double numberDouble = 0.1;
        int percent = 0;
        double percentPart = 0;
        int percentOfNumber = 0;
        while (percentOfNumber != 0) {
            while (!checkIfNoPrecision(numberDouble)) {
                percent = rand.nextInt((int)Math.pow(10, 2));
                percentPart = ((double)percent)/100;
                numberDouble = answer / percentPart;
            }
            number = (int) numberDouble;
            percentOfNumber = findPercent(number);
        }
        return new Question("", "If " + percent + "% of some number is " + answer +
                ", what is " + percentOfNumber + "% of this number?", String.valueOf(number));
    }

    protected boolean checkIfNoPrecision(double number) {
        String numAsStr = String.valueOf(number);
        int precision = 0;
        try {
            if (numAsStr.contains(".")) {
                precision = Integer.parseInt(numAsStr.split("\\.")[1]);
            }
        }
        catch (NumberFormatException e) {
            return false;
        }
        if (precision == 0)
            return true;
        else
            return false;
    }

    protected int findPercent(int number) {
        int percent = 0;
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                switch (i) {
                    case 2:
                        percent = 50;
                        break;
                    case 4:
                        percent = 25;
                        break;
                    case 5:
                        percent = 20;
                        break;
                    case 10:
                        percent = 10;
                        break;
                    case 20:
                        percent = 5;
                        break;
                    case 25:
                        percent = 4;
                        break;
                }
            }
        }
        return percent;
    }

}
