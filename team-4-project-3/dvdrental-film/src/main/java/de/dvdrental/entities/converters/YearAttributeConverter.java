package de.dvdrental.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Year;

@Converter(autoApply = true)
public class YearAttributeConverter implements AttributeConverter<Year, Short> {
    @Override
    public Short convertToDatabaseColumn(Year year) {
        return (short) year.getValue();
    }

    @Override
    public Year convertToEntityAttribute(Short aShort) {
        return Year.of(aShort);
    }
}
