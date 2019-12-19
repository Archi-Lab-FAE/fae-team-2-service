package de.th.koeln.archilab.fae.faeteam2service.api;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-19T10:48:55.616846300+01:00[Europe/Berlin]")
public class ApiException extends Exception {
    private int code;

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
