package de.th.koeln.archilab.fae.faeteam2service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;

/**
 * Zone
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-19T10:48:55.616846300+01:00[Europe/Berlin]")
public class Zone {
  @JsonProperty("zoneId")
  private String zoneId = null;

  @JsonProperty("toleranz")
  private Float toleranz = null;

  @JsonProperty("typ")
  private ZonenTyp typ = null;

  @JsonProperty("positionen")
  @Valid
  private List<Position> positionen = null;

  public Zone zoneId(String zoneId) {
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

  public Zone toleranz(Float toleranz) {
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

  public Zone typ(ZonenTyp typ) {
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

  public Zone positionen(List<Position> positionen) {
    this.positionen = positionen;
    return this;
  }

  public Zone addPositionenItem(Position positionenItem) {
    if (this.positionen == null) {
      this.positionen = new ArrayList<Position>();
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
  public List<Position> getPositionen() {
    return positionen;
  }

  public void setPositionen(List<Position> positionen) {
    this.positionen = positionen;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Zone zone = (Zone) o;
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
    sb.append("class Zone {\n");

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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
