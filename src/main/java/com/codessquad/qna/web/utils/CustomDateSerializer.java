package com.codessquad.qna.web.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.codessquad.qna.web.utils.DateConstants.MY_DATE_FORMATTER;

public class CustomDateSerializer extends StdSerializer<LocalDateTime> {
    public CustomDateSerializer() {
        this(null);
    }

    public CustomDateSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.format(MY_DATE_FORMATTER));
    }
}
