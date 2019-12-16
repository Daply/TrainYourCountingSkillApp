package com.dariapro.trainyourcountingskills.random.provider;

import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.random.arguments.GeneratorArguments;
import com.dariapro.trainyourcountingskills.random.entity.QuestionCompact;

import java.util.Random;

public class ExpressionGeneratorProvider extends QuestionGeneratorProvider {

    @Override
    public Question generate(GeneratorArguments args) {
        return generateRandomExpression(args);
    }

    protected QuestionCompact createQuestionExpressionCompact(GeneratorArguments args, Question question) {
        String operatorSpecified = generateOperator(args.isPlus(),
                args.isMinus(), args.isMultiply(), args.isDivide());
        int secondNumber = generateRandomNumber(args.getLevel());
        int questionAnswer = Integer.parseInt(question.getRightAnswer());
        return createQuestionCompact(secondNumber, questionAnswer, operatorSpecified);
    }

    protected String createExpression(QuestionCompact questionCompact, Question question) {
        String questionExpression;
        boolean changePlaces = false;
        int questionAnswer = Integer.parseInt(question.getRightAnswer());
        int nextNumber = 0;
        if (questionCompact.getFirstNumber() == questionAnswer) {
            nextNumber = questionCompact.getSecondNumber();
        }
        else if (questionCompact.getSecondNumber() == questionAnswer) {
            nextNumber = questionCompact.getFirstNumber();
        }
        if (questionAnswer < nextNumber) {
            changePlaces = true;
        }
        if (changePlaces) {
            questionExpression = nextNumber + " " + questionCompact.getOperator() +
                    " ( " + question.getQuestion() + " )";
        }
        else {
            questionExpression = "( " + question.getQuestion() + " ) " +
                    questionCompact.getOperator() + " " + nextNumber;
        }
        return questionExpression;
    }

    protected Question generateExpressionFromQuestionAndNumber(GeneratorArguments args,
                                                            Question question) {
        QuestionCompact questionCompact = createQuestionExpressionCompact(args, question);
        String questionExpression = createExpression(questionCompact, question);
        return new Question("", questionExpression, String.valueOf(questionCompact.getAnswer()));
    }

    protected Question generateRandomExpression(GeneratorArguments args) {
        Random quantityRandom = new Random();
        int quantity = quantityRandom.nextInt(2) + 2;

        Question question = generateQuestion(args);
        while (Integer.parseInt(question.getRightAnswer()) == 0) {
            question = generateQuestion(args);
        }
        for (int i = 0; i < quantity; i++) {
            if (Integer.parseInt(question.getRightAnswer()) != 0) {
                question = generateExpressionFromQuestionAndNumber(args, question);
            }
        }
        return question;
    }
}
