public class temp {
    public static void main(String[] args) {
        for(int i=1; i<=200; i++) {
            System.out.println(
                    "INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATED_DATE_TIME, COUNT_OF_ANSWERS) VALUES ('"+ i + "', '1', '6', 'dui', '2021-03-20 16:03:03.106461', '1')"
            );
        }

    }
}
