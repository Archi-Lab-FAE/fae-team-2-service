package de.th.koeln.archilab.fae.faeteam2service.positionssender;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import de.th.koeln.archilab.fae.faeteam2service.position.PositionDTO;
import io.swagger.annotations.ApiModelProperty;

/**
 * PositionssenderDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-01-30T16:33:21.467Z[GMT]")
public class PositionssenderDTO {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("letztesSignal")
    private OffsetDateTime letztesSignal = null;

    @JsonProperty("letzteWartung")
    private OffsetDateTime letzteWartung = null;

    @JsonProperty("position")
    private PositionDTO position = null;

    public PositionssenderDTO id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(example = "f33c6cd8-1697-11ea-8d71-362b9e155667", required = true, value = "")
    @NotNull

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PositionssenderDTO letztesSignal(OffsetDateTime letztesSignal) {
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

    public PositionssenderDTO letztWartung(OffsetDateTime letztWartung) {
        this.letzteWartung = letztWartung;
        return this;
    }

    /**
     * Get letztWartung
     *
     * @return letztWartung
     **/
    @ApiModelProperty(value = "")

    @Valid
    public OffsetDateTime getLetzteWartung() {
        return letzteWartung;
    }

    public void setLetzteWartung(OffsetDateTime letzteWartung) {
        this.letzteWartung = letzteWartung;
    }

    public PositionssenderDTO position(PositionDTO position) {
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
    public PositionDTO getPosition() {
        return position;
    }

    public void setPosition(PositionDTO position) {
        this.position = position;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PositionssenderDTO positionssenderDTO = (PositionssenderDTO) o;
        return Objects.equals(this.id, positionssenderDTO.id) &&
                Objects.equals(this.letztesSignal, positionssenderDTO.letztesSignal) &&
                Objects.equals(this.letzteWartung, positionssenderDTO.letzteWartung) &&
                Objects.equals(this.position, positionssenderDTO.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, letztesSignal, letzteWartung, position);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PositionssenderDTO {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    letztesSignal: ").append(toIndentedString(letztesSignal)).append("\n");
        sb.append("    letztWartung: ").append(toIndentedString(letzteWartung)).append("\n");
        sb.append("    position: ").append(toIndentedString(position)).append("\n");
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
