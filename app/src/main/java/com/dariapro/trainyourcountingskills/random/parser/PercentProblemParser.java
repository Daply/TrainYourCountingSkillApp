package com.dariapro.trainyourcountingskills.random.parser;

import com.dariapro.trainyourcountingskills.entity.Question;

public class PercentProblemParser extends Parser {

    @Override
    public String parse(Question question) {
        String result = new String();
        if (question.getQuestion().matches("(What is )[0-9]+(% of )[0-9]+\\?")) {
            int percentIndex = question.getQuestion().indexOf("%");
            int questionIndex = question.getQuestion().indexOf("?");
            int percent = Integer.parseInt(question.getQuestion().substring(8, percentIndex));
            int number = Integer.parseInt(question.getQuestion().substring(percentIndex + 5, questionIndex));
            double doubResult = (((double)percent)/100)*((double)number);
            result = String.valueOf((int)doubResult);
        }
        else if (question.getQuestion().matches("(If )[0-9]+(\\% of some number is )[0-9]+(, what is the number\\?)")) {
            int percentIndex = question.getQuestion().indexOf("%");
            int commaIndex = question.getQuestion().indexOf(",");
            int percent = Integer.parseInt(question.getQuestion().substring(3, percentIndex));
            int number = Integer.parseInt(question.getQuestion().substring(percentIndex + 20, commaIndex));
            double doubResult = (((double)number)*100)/((double)percent);
            result = String.valueOf((int)doubResult);
        }
        else if (question.getQuestion().matches("(What percentage of )[0-9]+( equals )[0-9]+\\?")) {
            int lastIndex = question.getQuestion().indexOf("equals") - 1;
            int questionIndex = question.getQuestion().indexOf("?");
            int firstNumber = Integer.parseInt(question.getQuestion().substring(19, lastIndex));
            int secondNumber = Integer.parseInt(question.getQuestion().substring(lastIndex + 8, questionIndex));
            double doubResult = (((double)secondNumber)/((double)firstNumber))*100;
            result = String.valueOf((int)doubResult);
        }
        return result;
    }

}
