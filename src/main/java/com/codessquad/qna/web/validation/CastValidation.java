package com.codessquad.qna.web.validation;

public class CastValidation {

    private CastValidation() {
    }

    public static Integer stringToInt(String stringInt){
        try{
            return Integer.parseInt(stringInt);
        } catch (NumberFormatException formatException){
            throw new IllegalArgumentException(stringInt + " is not a numeric value!");
        }
    }

}
