package com.codessquad.qna.web;

public class DataFacotry {
    public static void main(String[] args) {
        int k = 2;
        int l = 2;
        for(int i=1; i<=180; i++) {
            k++;
            k = k%3;
            if(k==0) {
                l++;
            }
            for(int j=9; j>=1; j--){
                System.out.println("INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATED_DATE_TIME, COUNT_OF_ANSWERS) VALUES ('"+i+"', '1', '"+i+"', 'dui', '2020-0"+l+"-"+k+""+j+" 16:03:03.106461', '0')");
                if(j != 1 ) {
                    i++;
                }
            }
        }
    }
}
