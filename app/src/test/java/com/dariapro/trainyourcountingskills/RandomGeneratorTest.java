package com.dariapro.trainyourcountingskills;

import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.random.arguments.GeneratorArguments;
import com.dariapro.trainyourcountingskills.random.generator.RandomGenerator;
import com.dariapro.trainyourcountingskills.random.parser.Parser;
import com.dariapro.trainyourcountingskills.random.parser.PercentProblemParser;
import com.dariapro.trainyourcountingskills.random.parser.QuestionParser;
import com.dariapro.trainyourcountingskills.random.provider.ExpressionGeneratorProvider;
import com.dariapro.trainyourcountingskills.random.provider.GeneratorProvider;
import com.dariapro.trainyourcountingskills.random.provider.ProblemGeneratorProvider;
import com.dariapro.trainyourcountingskills.random.provider.QuestionGeneratorProvider;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RandomGeneratorTest {

    @Test
    public void generateExpressionFromQuestionAndNumberTest() {
        RandomGenerator randomGenerator = new RandomGenerator();
        Parser questionParser = new QuestionParser();
        GeneratorArguments genArgs = new GeneratorArguments();
        genArgs.setLevel(1);
        GeneratorProvider generatorProvider = new QuestionGeneratorProvider();

        assertEquals(launchTests(randomGenerator, questionParser, genArgs,
                generatorProvider, 100), true);
    }

    @Test
    public void generateRandomExpressionTest() {
        RandomGenerator randomGenerator = new RandomGenerator();
        QuestionParser questionParser = new QuestionParser();
        GeneratorArguments genArgs = new GeneratorArguments();
        genArgs.setLevel(1);
        GeneratorProvider generatorProvider = new ExpressionGeneratorProvider();

        assertEquals(launchTests(randomGenerator, questionParser, genArgs,
                generatorProvider, 100), true);
    }

    @Test
    public void generateRandomProblemTest() {
        RandomGenerator randomGenerator = new RandomGenerator();
        Parser questionParser = new PercentProblemParser();
        GeneratorArguments genArgs = new GeneratorArguments();
        genArgs.setLevel(1);
        GeneratorProvider generatorProvider = new ProblemGeneratorProvider();

        assertEquals(launchTests(randomGenerator, questionParser, genArgs,
                generatorProvider, 100), true);
    }

    private boolean launchTests(RandomGenerator randomGenerator,
                               Parser questionParser,
                               GeneratorArguments genArgs,
                               GeneratorProvider generatorProvider,
                               int numberOfTests) {
        boolean isRight = true;
        for (int i = 0; i < numberOfTests; i++) {
            Question question = randomGenerator.generate(genArgs, generatorProvider);
            String answer = question.getRightAnswer();
            String rightAnswer = questionParser.parse(question);
            if (!answer.contentEquals(rightAnswer)) {
                isRight = false;

                System.out.println(question.getQuestion() + "   ansewer : " + question.getRightAnswer());
            }
        }
        return isRight;
    }
}
