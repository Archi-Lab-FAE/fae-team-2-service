package de.th.koeln.archilab.fae.faeteam2service.kafka.events;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CrudDomainEventParser<T> {

    // read and save data --> needed because you can't get the class of a generic typed class
    // alternative would be new DomainEvent classes for every entity
    public T parse(String crudDomainEventMessage, Class type) throws IOException {
        String json = new ObjectMapper().readTree(crudDomainEventMessage).get("payload").toString();
        return new ObjectMapper()
                .readerFor(type)
                .readValue(json);
    }
}
