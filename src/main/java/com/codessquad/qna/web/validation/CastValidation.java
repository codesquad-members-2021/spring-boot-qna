package com.codessquad.qna.web.validation;

public class CastValidation {

    private CastValidation() {
    }

    public static Integer stringToInt(String stringInt){
        if(stringInt.matches("\\d+")){
            return Integer.parseInt(stringInt);
        }
        throw new IllegalArgumentException(stringInt + " is not a numeric value!");
    }

}
