package de.th.koeln.archilab.fae.faeteam2service.zone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets ZonenTyp
 */
public enum ZonenTyp {
    GEWOHNT("GEWOHNT"),
    UNGEWOHNT("UNGEWOHNT");

    private String value;

    ZonenTyp(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ZonenTyp fromValue(String text) {
        for (ZonenTyp b : ZonenTyp.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
