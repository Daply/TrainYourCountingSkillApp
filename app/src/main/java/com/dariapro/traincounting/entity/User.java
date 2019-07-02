package com.dariapro.traincounting.entity;

public class User {

    private String name = null;
    private Record randomQuestionsRecord = null;

    public User (){
        name = new String();
        randomQuestionsRecord = new Record();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Record getRandomQuestionsRecord() {
        return randomQuestionsRecord;
    }

    public void setRandomQuestionsRecord(Record randomQuestionsRecord) {
        this.randomQuestionsRecord = randomQuestionsRecord;
    }
}