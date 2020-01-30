package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import de.th.koeln.archilab.fae.faeteam2service.positionssender.PositionssenderDTO;
import io.swagger.annotations.ApiModelProperty;

/**
 * DemenziellErkrankterDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-01-30T16:33:21.467Z[GMT]")
public class DemenziellErkrankterDTO {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("vorname")
  private String vorname = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("positionssender")
  @Valid
  private List<PositionssenderDTO> positionssender = null;

  public DemenziellErkrankterDTO id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   **/
  @ApiModelProperty(example = "f33c6fa8-1697-11ea-8d71-362b9e155667", required = true, value = "")
  @NotNull

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DemenziellErkrankterDTO vorname(String vorname) {
    this.vorname = vorname;
    return this;
  }

  /**
   * Get vorname
   *
   * @return vorname
   **/
  @ApiModelProperty(example = "Kutz", required = true, value = "")
  @NotNull

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
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
  @ApiModelProperty(example = "Löhler", value = "")

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DemenziellErkrankterDTO positionssender(List<PositionssenderDTO> positionssender) {
    this.positionssender = positionssender;
    return this;
  }

  public DemenziellErkrankterDTO addPositionssenderItem(PositionssenderDTO positionssenderItem) {
    if (this.positionssender == null) {
      this.positionssender = new ArrayList<PositionssenderDTO>();
    }
    this.positionssender.add(positionssenderItem);
    return this;
  }

  /**
   * Get positionssender
   *
   * @return positionssender
   **/
  @ApiModelProperty(value = "")
  @Valid
  public List<PositionssenderDTO> getPositionssender() {
    return positionssender;
  }

  public void setPositionssender(List<PositionssenderDTO> positionssender) {
    this.positionssender = positionssender;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DemenziellErkrankterDTO demenziellErkrankterDTO = (DemenziellErkrankterDTO) o;
    return Objects.equals(this.id, demenziellErkrankterDTO.id) &&
            Objects.equals(this.vorname, demenziellErkrankterDTO.vorname) &&
            Objects.equals(this.name, demenziellErkrankterDTO.name) &&
            Objects.equals(this.positionssender, demenziellErkrankterDTO.positionssender);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, vorname, name, positionssender);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DemenziellErkrankterDTO {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    vorname: ").append(toIndentedString(vorname)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    positionssender: ").append(toIndentedString(positionssender)).append("\n");
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
