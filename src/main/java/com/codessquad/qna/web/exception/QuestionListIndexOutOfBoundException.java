package com.codessquad.qna.web.exception;


public class QuestionListIndexOutOfBoundException extends RuntimeException{

    public QuestionListIndexOutOfBoundException(int index, int lowerBound, int upperbound){
        super(String.format(
                "Question index %d is out of bound! Question list's lower bound is %d and upper bound is %d!",
                 index, lowerBound, upperbound));
    }
    public QuestionListIndexOutOfBoundException(int index, int upperbound){
        this(index, 0, upperbound);
    }

}
