package com.dariapro.trainyourcountingskills.random.entity;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class QuestionCompact {

    private int firstNumber;
    private Queue<String> firstNumberSpecialOperators;
    private int secondNumber;
    private Queue<String> secondNumberSpecialOperators;
    private String operator;
    private int answer;

    public QuestionCompact(int firstNumber, int secondNumber, String operator, int answer) {
        this.firstNumber = firstNumber;
        this.firstNumberSpecialOperators = new LinkedList<>();
        this.secondNumber = secondNumber;
        this.secondNumberSpecialOperators = new LinkedList<>();
        this.operator = operator;
        this.answer = answer;
    }

    public String getFirstNumberToString() {
        String firstNumberResult = new String();
        for (String operator: this.firstNumberSpecialOperators) {
            firstNumberResult += operator;
        }
        firstNumberResult += this.firstNumber;
        return firstNumberResult;
    }

    public String getSecondNumberToString() {
        String secondNumberResult = new String();
        for (String operator: this.secondNumberSpecialOperators) {
            secondNumberResult += operator;
        }
        secondNumberResult += this.secondNumber;
        return secondNumberResult;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public Queue getFirstNumberSpecialOperators() {
        return firstNumberSpecialOperators;
    }

    public void setFirstNumberSpecialOperators(Queue<String> firstNumberSpecialOperators) {
        this.firstNumberSpecialOperators = firstNumberSpecialOperators;
    }

    public Queue getSecondNumberSpecialOperators() {
        return secondNumberSpecialOperators;
    }

    public void setSecondNumberSpecialOperators(Queue<String> secondNumberSpecialOperators) {
        this.secondNumberSpecialOperators = secondNumberSpecialOperators;
    }

    public void putNewFirstNumberSpecialOperator(String operator) {
        this.firstNumberSpecialOperators.add(operator);
    }

    public void putNewSecondNumberSpecialOperator(String operator) {
        this.secondNumberSpecialOperators.add(operator);
    }
}
