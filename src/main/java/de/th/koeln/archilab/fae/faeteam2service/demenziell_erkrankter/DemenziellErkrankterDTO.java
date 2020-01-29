package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZoneDTO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The DTO object is used to receive data of a DemenziellErkrankter, which is published by Team-1
 * TODO nach event durch team-1 anpassen. &
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-01-07T14:10:32.528Z[GMT]")
public class DemenziellErkrankterDTO {
    @JsonProperty("demenziellErkrankterId")
    private String demenziellErkrankterId = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("zonen")
    @Valid
    private List<ZoneDTO> zonen = null;

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
     * @return name
     **/
    @ApiModelProperty(example = "K. Löhler", value = "")

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DemenziellErkrankterDTO zonen(List<ZoneDTO> zonen) {
        this.zonen = zonen;
        return this;
    }

    public DemenziellErkrankterDTO addZonenItem(ZoneDTO zonenItem) {
        if (this.zonen == null) {
            this.zonen = new ArrayList<ZoneDTO>();
        }
        this.zonen.add(zonenItem);
        return this;
    }

    /**
     * Get zonen
     *
     * @return zonen
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<ZoneDTO> getZonen() {
        return zonen;
    }

    public void setZonen(List<ZoneDTO> zonen) {
        this.zonen = zonen;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DemenziellErkrankterDTO demenziellErkrankter = (DemenziellErkrankterDTO) o;
        return Objects.equals(this.demenziellErkrankterId, demenziellErkrankter.demenziellErkrankterId) &&
                Objects.equals(this.name, demenziellErkrankter.name) &&
                Objects.equals(this.zonen, demenziellErkrankter.zonen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(demenziellErkrankterId, name, zonen);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DemenziellErkrankterDTO {\n");

        sb.append("    demenziellErkrankterId: ").append(toIndentedString(demenziellErkrankterId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    zonen: ").append(toIndentedString(zonen)).append("\n");
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
