package uk.co.cerihughes.mgm.data;

import java.time.format.DateTimeFormatter;

public interface DateTimeFormatterFactory {
    String dateFormat = "dd/MM/yyyy";
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
}
