package de.th.koeln.archilab.fae.faeteam2service.zone;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import de.th.koeln.archilab.fae.faeteam2service.position.PositionDTO;
import io.swagger.annotations.ApiModelProperty;

/**
 * The DTO is used to receive and send data from the rest endpoints.
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-01-23T20:04:16.757Z[GMT]")
public class ZoneDTO {
    @JsonProperty("zoneId")
    private String zoneId = null;

    @JsonProperty("toleranz")
    private Float toleranz = null;

    @JsonProperty("typ")
    private ZonenTyp typ = null;

    @JsonProperty("positionen")
    @Valid
    private List<PositionDTO> positionen = null;

    public ZoneDTO zoneId(String zoneId) {
        this.zoneId = zoneId;
        return this;
    }

    /**
     * Get zoneId
     *
     * @return zoneId
     **/
    @ApiModelProperty(example = "8f926d33-27bd-4afd-aabb-b160a6402348", value = "")

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneDTO toleranz(Float toleranz) {
        this.toleranz = toleranz;
        return this;
    }

    /**
     * Get toleranz
     *
     * @return toleranz
     **/
    @ApiModelProperty(example = "1.5", value = "")

    public Float getToleranz() {
        return toleranz;
    }

    public void setToleranz(Float toleranz) {
        this.toleranz = toleranz;
    }

    public ZoneDTO typ(ZonenTyp typ) {
        this.typ = typ;
        return this;
    }

    /**
     * Get typ
     *
     * @return typ
     **/
    @ApiModelProperty(value = "")

    @Valid
    public ZonenTyp getTyp() {
        return typ;
    }

    public void setTyp(ZonenTyp typ) {
        this.typ = typ;
    }

    public ZoneDTO positionen(List<PositionDTO> positionen) {
        this.positionen = positionen;
        return this;
    }

    public ZoneDTO addPositionenItem(PositionDTO positionenItem) {
        if (this.positionen == null) {
            this.positionen = new ArrayList<PositionDTO>();
        }
        this.positionen.add(positionenItem);
        return this;
    }

    /**
     * Get positionen
     *
     * @return positionen
     **/
    @ApiModelProperty(value = "")
    @Valid
    @Size(min = 2, max = 2)
    public List<PositionDTO> getPositionen() {
        return positionen;
    }

    public void setPositionen(List<PositionDTO> positionen) {
        this.positionen = positionen;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ZoneDTO zone = (ZoneDTO) o;
        return Objects.equals(this.zoneId, zone.zoneId) &&
                Objects.equals(this.toleranz, zone.toleranz) &&
                Objects.equals(this.typ, zone.typ) &&
                Objects.equals(this.positionen, zone.positionen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, toleranz, typ, positionen);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ZoneDTO {\n");

        sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
        sb.append("    toleranz: ").append(toIndentedString(toleranz)).append("\n");
        sb.append("    typ: ").append(toIndentedString(typ)).append("\n");
        sb.append("    positionen: ").append(toIndentedString(positionen)).append("\n");
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
