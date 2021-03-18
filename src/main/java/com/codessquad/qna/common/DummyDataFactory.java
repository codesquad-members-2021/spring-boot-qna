package com.codessquad.qna.common;

import com.codessquad.qna.question.Answer;
import com.codessquad.qna.question.Question;
import com.codessquad.qna.user.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DummyDataFactory {
    public static List<User> createUsers() {
        return Arrays.asList(
                new User(null, "javajigi", "1234", "자바지기", "javajigi@sample.net"),
                new User(null, "slipp", "1234", "슬립", "slipp@sample.net"),
                new User(null, "mskim", "1234", "김문수", "mskim@sample.net"),
                new User(null, "test", "test", "test", "test@test.net")
        );
    }

    public static List<Answer> createAnswers() {
        return Arrays.asList(
                new Answer("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까",
                        LocalDateTime.of(2016, 01, 15, 18, 47),
                        new Question(1L),
                        new User(1L, "", "", "", "")),
                new Answer("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까",
                        LocalDateTime.of(2016, 01, 15, 18, 47),
                        new Question(2L),
                        new User(2L, "", "", "", "")),
                new Answer("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까",
                        LocalDateTime.of(2016, 01, 15, 18, 47),
                        new Question(3L),
                        new User(4L, "", "", "", "")),
                new Answer("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까",
                        LocalDateTime.of(2016, 01, 15, 18, 47),
                        new Question(1L),
                        new User(1L, "", "", "", ""))
        );
    }

    public static List<Question> createQuestions() {
        return Arrays.asList(
                new Question("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까",
                        "test contents",
                        LocalDateTime.of(2016, 01, 15, 18, 47),
                        new User(1L, "", "", "", "")),
                new Question("runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?",
                        "test contents",
                        LocalDateTime.of(2016, 01, 05, 18, 47),
                        new User(3L, "", "", "", "")),
                new Question("InitializingBean implements afterPropertiesSet() 호출되지 않는 문제.\n",
                        "A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.\n" +
                                "서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.",
                        LocalDateTime.of(2015, 12, 30, 01, 47),
                        new User(4L, "", "", "", "")),
                new Question("InitializingBean implements afterPropertiesSet() 호출되지 않는 문제.\n",
                        "A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니다.</p><p>B라는 클래스는 InitializingBean 을 상속하고 afterPropertiesSet을 구현하고 있습니다.서버가 가동되면서 bean들이 초기화되는 시점에 B라는 클래스의 afterPropertiesSet 메소드는</p><p>A라는 클래스의 특정 메소드인 afunc()를 호출하고 있습니다.A 에 의존성을 가지는 B라는 클래스가 있습니",
                        LocalDateTime.of(2015, 12, 30, 01, 47),
                        new User(4L, "", "", "", "")),
                new Question("새로운 게시글",
                        "게시글 입니다.",
                        LocalDateTime.of(2015, 12, 30, 01, 47),
                        new User(4L, "", "", "", ""))
        );
    }
}
