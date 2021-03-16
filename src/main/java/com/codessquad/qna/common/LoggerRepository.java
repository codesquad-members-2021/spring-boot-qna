package com.codessquad.qna.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NOTE: 로깅 기록을 휘발되지 않도록 저장하기 위해 Repository 라고 네이밍을 했다.
 * 저장할 가치가 있는 것은 서버 장애를 추적하기 위한 에러 로깅 기록들이다.
 * 디버깅용 로깅은 LoggerRepository 를 사용하지 않도록 하자.
 * TODO: ELK 혹은 슬랙봇과 연동을 해야한다.
 */
public class LoggerRepository {
    private final Logger logger;

    public LoggerRepository(Class clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public void saveError(Throwable throwable) {
        logger.error(throwable.getMessage());
    }
}
