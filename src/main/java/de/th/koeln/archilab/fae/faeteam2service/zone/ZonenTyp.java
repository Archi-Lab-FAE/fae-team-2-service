package de.th.koeln.archilab.fae.faeteam2service.zone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Representation of the type of a {@link Zone}.
 *
 * @see <a href="https://fae.archi-lab.io/glossary/2019/12/02/Glossary-gewohnte-Zone.html">Glossary Definition "Gewohnt"</a>
 * @see <a href="https://fae.archi-lab.io/glossary/2019/12/02/Glossary-ungewohnte-Zone.html">Glossary Definition "Ungewohnt"</a>
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
