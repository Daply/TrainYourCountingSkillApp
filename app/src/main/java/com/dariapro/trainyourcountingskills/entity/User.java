package com.dariapro.trainyourcountingskills.entity;

/**
 * @author Pleshchankova Daria
 *
 */
public class User {

    private String name;
    private Record randomQuestionsRecord;

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
