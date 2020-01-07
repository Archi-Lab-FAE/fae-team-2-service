package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

/**
 * DemenziellErkrankterDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-19T10:48:55.616846300+01:00[Europe/Berlin]")
public class DemenziellErkrankterDTO {
    @JsonProperty("demenziellErkrankterId")
    private String demenziellErkrankterId = null;

    @JsonProperty("name")
    private String name = null;

    public DemenziellErkrankterDTO demenziellErkrankterId(String demenziellErkrankterId) {
        this.demenziellErkrankterId = demenziellErkrankterId;
        return this;
    }

    /**
     * Get demenziellErkrankterId
     *
     * @return demenziellErkrankterId
     **/
    @ApiModelProperty(example = "f33c6fa8-1697-11ea-8d71-362b9e155667", value = "")

    public String getDemenziellErkrankterId() {
        return demenziellErkrankterId;
    }

    public void setDemenziellErkrankterId(String demenziellErkrankterId) {
        this.demenziellErkrankterId = demenziellErkrankterId;
    }

    public DemenziellErkrankterDTO name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(example = "K. Löhler", value = "")

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DemenziellErkrankterDTO demenziellErkrankter = (DemenziellErkrankterDTO) o;
        return Objects.equals(this.demenziellErkrankterId, demenziellErkrankter.demenziellErkrankterId) &&
                Objects.equals(this.name, demenziellErkrankter.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(demenziellErkrankterId, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DemenziellErkrankterDTO {\n");

        sb.append("    demenziellErkrankterId: ").append(toIndentedString(demenziellErkrankterId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
