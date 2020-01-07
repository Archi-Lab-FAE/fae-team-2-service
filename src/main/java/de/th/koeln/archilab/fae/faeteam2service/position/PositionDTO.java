package de.th.koeln.archilab.fae.faeteam2service.position;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

/**
 * PositionDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-19T10:48:55.616846300+01:00[Europe/Berlin]")
public class PositionDTO {
    @JsonProperty("positionsId")
    private String positionsId = null;

    @JsonProperty("laengengrad")
    private Double laengengrad = null;

    @JsonProperty("breitengrad")
    private Double breitengrad = null;

    public PositionDTO positionsId(String positionsId) {
        this.positionsId = positionsId;
        return this;
    }

    /**
     * Get positionsId
     *
     * @return positionsId
     **/
    @ApiModelProperty(example = "65cf7091-e3ac-48e7-8553-c329490aae5c", value = "")

    public String getPositionsId() {
        return positionsId;
    }

    public void setPositionsId(String positionsId) {
        this.positionsId = positionsId;
    }

    public PositionDTO laengengrad(Double laengengrad) {
        this.laengengrad = laengengrad;
        return this;
    }

    /**
     * Get laengengrad
     *
     * @return laengengrad
     **/
    @ApiModelProperty(example = "51.042755", value = "")

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
    @ApiModelProperty(example = "7.287333", value = "")

    public Double getBreitengrad() {
        return breitengrad;
    }

    public void setBreitengrad(Double breitengrad) {
        this.breitengrad = breitengrad;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PositionDTO position = (PositionDTO) o;
        return Objects.equals(this.positionsId, position.positionsId) &&
                Objects.equals(this.laengengrad, position.laengengrad) &&
                Objects.equals(this.breitengrad, position.breitengrad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionsId, laengengrad, breitengrad);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PositionDTO {\n");

        sb.append("    positionsId: ").append(toIndentedString(positionsId)).append("\n");
        sb.append("    laengengrad: ").append(toIndentedString(laengengrad)).append("\n");
        sb.append("    breitengrad: ").append(toIndentedString(breitengrad)).append("\n");
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
