package ptithcm.tttn.config;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BooleanToCharConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return null; // Xử lý giá trị null nếu cần
        }
        return attribute ? "t" : "f"; // true -> 'T', false -> 'F'
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null; // Xử lý giá trị null nếu cần
        }
        return dbData.equalsIgnoreCase("t"); // 'T' -> true, 'F' -> false
    }
}
