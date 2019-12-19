package de.th.koeln.archilab.fae.faeteam2service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import java.util.Objects;

import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;

/**
 * Positionssender
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-19T10:48:55.616846300+01:00[Europe/Berlin]")
public class Positionssender {
    @JsonProperty("positionssenderId")
    private String positionssenderId = null;

    @JsonProperty("letztesSignal")
    private OffsetDateTime letztesSignal = null;

    @JsonProperty("batterieStatus")
    private Float batterieStatus = null;

    @JsonProperty("genauigkeit")
    private Float genauigkeit = null;

    @JsonProperty("position")
    private Position position = null;

    public Positionssender positionssenderId(String positionssenderId) {
        this.positionssenderId = positionssenderId;
        return this;
    }

    /**
     * Get positionssenderId
     *
     * @return positionssenderId
     **/
    @ApiModelProperty(example = "f33c6cd8-1697-11ea-8d71-362b9e155667", value = "")

    public String getPositionssenderId() {
        return positionssenderId;
    }

    public void setPositionssenderId(String positionssenderId) {
        this.positionssenderId = positionssenderId;
    }

    public Positionssender letztesSignal(OffsetDateTime letztesSignal) {
        this.letztesSignal = letztesSignal;
        return this;
    }

    /**
     * Get letztesSignal
     *
     * @return letztesSignal
     **/
    @ApiModelProperty(value = "")

    @Valid
    public OffsetDateTime getLetztesSignal() {
        return letztesSignal;
    }

    public void setLetztesSignal(OffsetDateTime letztesSignal) {
        this.letztesSignal = letztesSignal;
    }

    public Positionssender batterieStatus(Float batterieStatus) {
        this.batterieStatus = batterieStatus;
        return this;
    }

    /**
     * Get batterieStatus
     *
     * @return batterieStatus
     **/
    @ApiModelProperty(example = "0.8", value = "")

    public Float getBatterieStatus() {
        return batterieStatus;
    }

    public void setBatterieStatus(Float batterieStatus) {
        this.batterieStatus = batterieStatus;
    }

    public Positionssender genauigkeit(Float genauigkeit) {
        this.genauigkeit = genauigkeit;
        return this;
    }

    /**
     * Get genauigkeit
     *
     * @return genauigkeit
     **/
    @ApiModelProperty(example = "1.0", value = "")

    public Float getGenauigkeit() {
        return genauigkeit;
    }

    public void setGenauigkeit(Float genauigkeit) {
        this.genauigkeit = genauigkeit;
    }

    public Positionssender position(Position position) {
        this.position = position;
        return this;
    }

    /**
     * Get position
     *
     * @return position
     **/
    @ApiModelProperty(value = "")

    @Valid
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Positionssender positionssender = (Positionssender) o;
        return Objects.equals(this.positionssenderId, positionssender.positionssenderId) &&
                Objects.equals(this.letztesSignal, positionssender.letztesSignal) &&
                Objects.equals(this.batterieStatus, positionssender.batterieStatus) &&
                Objects.equals(this.genauigkeit, positionssender.genauigkeit) &&
                Objects.equals(this.position, positionssender.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionssenderId, letztesSignal, batterieStatus, genauigkeit, position);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Positionssender {\n");

        sb.append("    positionssenderId: ").append(toIndentedString(positionssenderId)).append("\n");
        sb.append("    letztesSignal: ").append(toIndentedString(letztesSignal)).append("\n");
        sb.append("    batterieStatus: ").append(toIndentedString(batterieStatus)).append("\n");
        sb.append("    genauigkeit: ").append(toIndentedString(genauigkeit)).append("\n");
        sb.append("    position: ").append(toIndentedString(position)).append("\n");
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
