package com.dariapro.traincounting.entity;

import java.util.UUID;

public class Answer {
    private UUID answerId;
    private String answer = null;
    private boolean isRight = false;

    public Answer (String answer, boolean isRight) {
        this.answerId = UUID.randomUUID();
        this.answer = answer;
        this.isRight = isRight;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }
}
