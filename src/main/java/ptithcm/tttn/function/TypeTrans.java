package ptithcm.tttn.function;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeTrans {
    IMPORT("IMPORT"),
    EXPORT("EXPORT");

    private final String typeName;
}
