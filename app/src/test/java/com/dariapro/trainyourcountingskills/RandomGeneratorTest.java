package com.dariapro.trainyourcountingskills;

import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.random.QuestionParser;
import com.dariapro.trainyourcountingskills.random.RandomQuestionGenerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RandomGeneratorTest {
    @Test
    public void generateOperatorTest() {
        RandomQuestionGenerator randQuestGen = new RandomQuestionGenerator();
        int operator1 = randQuestGen.generateOperator(false, false, false, false);

        int operator2 = randQuestGen.generateOperator(true, false, false, false);
        int operator3 = randQuestGen.generateOperator(false, true, false, false);
        int operator4 = randQuestGen.generateOperator(false, false, true, false);
        int operator5 = randQuestGen.generateOperator(false, false, false, true);

        int operator6 = randQuestGen.generateOperator(true, true, false, false);
        boolean operator6Result = operator6 == 0 || operator6 == 1;
        int operator7 = randQuestGen.generateOperator(true, true, true, false);
        boolean operator7Result = operator7 == 0 || operator7 == 1 || operator7 == 2;
        int operator8 = randQuestGen.generateOperator(true, true, true, true);
        boolean operator8Result = operator8 == 0 || operator8 == 1 || operator8 == 2 || operator8 == 3;
        int operator9 = randQuestGen.generateOperator(false, true, true, false);
        boolean operator9Result = operator9 == 1 || operator9 == 2;
        int operator10 = randQuestGen.generateOperator(false, true, true, true);
        boolean operator10Result = operator10 == 1 || operator10 == 2 || operator10 == 3;
        int operator11 = randQuestGen.generateOperator(false, false, true, true);
        boolean operator11Result = operator11 == 2 || operator11 == 3;

        int operator12 = randQuestGen.generateOperator(true, false, true, false);
        boolean operator12Result = operator12 == 0 || operator12 == 2;
        int operator13 = randQuestGen.generateOperator(true, false, false, true);
        boolean operator13Result = operator13 == 0 || operator13 == 3;
        int operator14 = randQuestGen.generateOperator(false, true, false, true);
        boolean operator14Result = operator14 == 1 || operator14 == 3;

        int operator15 = randQuestGen.generateOperator(true, false, true, true);
        boolean operator15Result = operator15 == 0 || operator15 == 2 || operator15 == 3;
        int operator16 = randQuestGen.generateOperator(true, true, false, true);
        boolean operator16Result = operator16 == 0 || operator16 == 1 || operator16 == 3;

        assertEquals(operator1, 0);
        assertEquals(operator2, 0);
        assertEquals(operator3, 1);
        assertEquals(operator4, 2);
        assertEquals(operator5, 3);

        assertEquals(operator6Result, true);
        assertEquals(operator7Result, true);
        assertEquals(operator8Result, true);
        assertEquals(operator9Result, true);
        assertEquals(operator10Result, true);
        assertEquals(operator11Result, true);
        assertEquals(operator12Result, true);
        assertEquals(operator13Result, true);
        assertEquals(operator14Result, true);
        assertEquals(operator15Result, true);
        assertEquals(operator16Result, true);
    }

    @Test
    public void generateRandomNumberTest() {
        RandomQuestionGenerator randQuestGen = new RandomQuestionGenerator();
        int number1 = randQuestGen.generateRandomNumber(-100);
        boolean number1Result = number1 > 0 && number1 % 10 == number1;
        int number2 = randQuestGen.generateRandomNumber(0);
        boolean number2Result = number2 > 0 && number2 % 10 == number2;
        int number3 = randQuestGen.generateRandomNumber(100);
        boolean number3Result = number3 > 0 && number3 % 10 == number3;

        int number4 = randQuestGen.generateRandomNumber(1);
        boolean number4Result = number4 > 0 && number4 % 10 == number4;
        int number5 = randQuestGen.generateRandomNumber(2);
        boolean number5Result = number5 > 0 && number5 % 100 == number5;
        int number6 = randQuestGen.generateRandomNumber(3);
        boolean number6Result = number6 > 0 && number6 % 1000 == number6;
        int number7 = randQuestGen.generateRandomNumber(4);
        boolean number7Result = number7 > 0 && number7 % 10000 == number7;
        int number8 = randQuestGen.generateRandomNumber(5);
        boolean number8Result = number8 > 0 && number8 % 100000 == number8;

        assertEquals(number1Result, true);
        assertEquals(number2Result, true);
        assertEquals(number3Result, true);

        assertEquals(number4Result, true);
        assertEquals(number5Result, true);
        assertEquals(number6Result, true);
        assertEquals(number7Result, true);
        assertEquals(number8Result, true);
    }

    @Test
    public void generateQuestionTest() {
        RandomQuestionGenerator randQuestGen = new RandomQuestionGenerator();
        QuestionParser questionParser = new QuestionParser();
        Question question = randQuestGen
                .generateQuestion(1, true, true, true, true, true);
        String answer = question.getRightAnswer();
        String rightAnswer = questionParser.parseQuestion(question);
        boolean isRight = answer.contentEquals(rightAnswer);

        assertEquals(isRight, true);
    }

    @Test
    public void generateExpressionFromQuestionAndNumberTest() {
        RandomQuestionGenerator randQuestGen = new RandomQuestionGenerator();
        QuestionParser questionParser = new QuestionParser();
        Question question = randQuestGen
                .generateQuestion(1, true, true, true, true, true);
        Question expression = randQuestGen
                .generateExpressionFromQuestionAndNumber(1, true, true,
                        true, true, question);
        String answer = expression.getRightAnswer();
        String rightAnswer = questionParser.parseQuestion(expression);
        boolean isRight = answer.contentEquals(rightAnswer);

        assertEquals(isRight, true);
    }

    @Test
    public void generateRandomExpressionTest() {
        RandomQuestionGenerator randQuestGen = new RandomQuestionGenerator();
        QuestionParser questionParser = new QuestionParser();
        Question expression = randQuestGen
                .generateRandomExpression(1);
        String answer = expression.getRightAnswer();
        String rightAnswer = questionParser.parseQuestion(expression);
        boolean isRight = answer.contentEquals(rightAnswer);

        assertEquals(isRight, true);
    }
}
