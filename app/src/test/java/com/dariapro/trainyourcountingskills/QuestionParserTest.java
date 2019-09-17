package com.dariapro.trainyourcountingskills;

import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.random.QuestionParser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuestionParserTest {

    @Test
    public void questionParserTest() {
        Question question = new Question("",
                "(((1    * 2) + √36 ) *   5) + (3 + ( √16 * (1 + 3)))", "59");
        QuestionParser questionParser = new QuestionParser();
        String answer = questionParser.parseQuestion(question);
        boolean isValid1 = questionParser.questionIsValid(question);
        boolean isRight1 = answer.contentEquals(question.getRightAnswer());

        question = new Question("",
                "( 102 + (   √100 * (103 + ( 1 + (34 / 2))))) + (√16 + 5)", "1321");
        questionParser = new QuestionParser();
        answer = questionParser.parseQuestion(question);
        boolean isValid2 = questionParser.questionIsValid(question);
        boolean isRight2 = answer.contentEquals(question.getRightAnswer());

        question = new Question("",
                "11033 - (  10038 + ( 23 * ( 24 +    √121 )))", "190");
        questionParser = new QuestionParser();
        answer = questionParser.parseQuestion(question);
        boolean isValid3 = questionParser.questionIsValid(question);
        boolean isRight3 = answer.contentEquals(question.getRightAnswer());

        question = new Question("",
                "((1    * ", "");
        questionParser = new QuestionParser();
        boolean isValid4 = questionParser.questionIsValid(question);
        question = new Question("",
                "((1    * 2)) + 1 / ", "");
        questionParser = new QuestionParser();
        boolean isValid5 = questionParser.questionIsValid(question);

        assertEquals(isRight1, true);
        assertEquals(isRight2, true);
        assertEquals(isRight3, true);

        assertEquals(isValid1, true);
        assertEquals(isValid2, true);
        assertEquals(isValid3, true);
        assertEquals(isValid4, false);
        assertEquals(isValid5, false);
    }
}
