package com.dariapro.trainyourcountingskills;

import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.random.parser.QuestionParser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuestionParserTest {

    @Test
    public void questionParserTest1() {
        Question question = new Question("",
                "(((1    * 2) + √36 ) *   5) + (3 + ( √16 * (1 + 3)))", "59");
        QuestionParser questionParser = new QuestionParser();
        String answer = questionParser.parse(question);
        boolean isValid1 = questionParser.questionIsValid(question);
        boolean isRight1 = answer.contentEquals(question.getRightAnswer());


        assertEquals(isRight1, true);
        assertEquals(isValid1, true);
    }

    @Test
    public void questionParserTest2() {
        Question question = new Question("",
                "( 102 + (   √100 * (103 + ( 1 + (34 / 2))))) + (√16 + 5)", "1321");
        QuestionParser questionParser = new QuestionParser();
        String answer = questionParser.parse(question);
        boolean isValid2 = questionParser.questionIsValid(question);
        boolean isRight2 = answer.contentEquals(question.getRightAnswer());

        assertEquals(isRight2, true);
        assertEquals(isValid2, true);
    }

    @Test
    public void questionParserTest3() {
        Question question = new Question("",
                "11033 - (  10038 + ( 23 * ( 24 +    √121 )))", "190");
        QuestionParser questionParser = new QuestionParser();
        String answer = questionParser.parse(question);
        boolean isValid3 = questionParser.questionIsValid(question);
        boolean isRight3 = answer.contentEquals(question.getRightAnswer());

        assertEquals(isRight3, true);
        assertEquals(isValid3, true);
    }

    @Test
    public void questionParserTest4() {
        Question question = new Question("",
                "((1    * ", "");
        QuestionParser questionParser = new QuestionParser();
        boolean isValid4 = questionParser.questionIsValid(question);

        assertEquals(isValid4, false);
    }

    @Test
    public void questionParserTest5() {
        Question question = new Question("",
                "((1    * 2)) + 1 / ", "");
        QuestionParser questionParser = new QuestionParser();
        boolean isValid5 = questionParser.questionIsValid(question);

        assertEquals(isValid5, false);
    }
}
