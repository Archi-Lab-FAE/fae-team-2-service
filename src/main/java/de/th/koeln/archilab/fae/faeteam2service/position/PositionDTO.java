package de.th.koeln.archilab.fae.faeteam2service.position;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * PositionDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-01-30T16:33:21.467Z[GMT]")
public class PositionDTO {
    @JsonProperty("laengengrad")
    private Double laengengrad = null;

    @JsonProperty("breitengrad")
    private Double breitengrad = null;

    public PositionDTO laengengrad(Double laengengrad) {
        this.laengengrad = laengengrad;
        return this;
    }

    /**
     * Get laengengrad
     *
     * @return laengengrad
     **/
    @ApiModelProperty(example = "51.042755", required = true, value = "")
    @NotNull

    public Double getLaengengrad() {
        return laengengrad;
    }

    public void setLaengengrad(Double laengengrad) {
        this.laengengrad = laengengrad;
    }

    public PositionDTO breitengrad(Double breitengrad) {
        this.breitengrad = breitengrad;
        return this;
    }

    /**
     * Get breitengrad
     *
     * @return breitengrad
     **/
    @ApiModelProperty(example = "7.287333", required = true, value = "")
    @NotNull

    public Double getBreitengrad() {
        return breitengrad;
    }

    public void setBreitengrad(Double breitengrad) {
        this.breitengrad = breitengrad;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PositionDTO positionDTO = (PositionDTO) o;
        return Objects.equals(this.laengengrad, positionDTO.laengengrad) &&
                Objects.equals(this.breitengrad, positionDTO.breitengrad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(laengengrad, breitengrad);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PositionDTO {\n");

        sb.append("    laengengrad: ").append(toIndentedString(laengengrad)).append("\n");
        sb.append("    breitengrad: ").append(toIndentedString(breitengrad)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
