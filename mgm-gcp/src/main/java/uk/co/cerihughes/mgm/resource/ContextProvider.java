package uk.co.cerihughes.mgm.resource;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import uk.co.cerihughes.mgm.data.DateTimeFormatterFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ContextProvider implements ContextResolver<ObjectMapper> {
    private final ObjectMapper objectMapper;

    public ContextProvider() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatterFactory.dateFormatter));

        objectMapper = new ObjectMapper()
                .registerModule(javaTimeModule)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .setSerializationInclusion( JsonInclude.Include.NON_NULL )
                .setDateFormat(new SimpleDateFormat(DateTimeFormatterFactory.dateFormat))
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @Override
    public ObjectMapper getContext(Class<?> objectType) {
        return objectMapper;
    }
}