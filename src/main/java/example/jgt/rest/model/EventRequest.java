package example.jgt.rest.model;

import example.jgt.model.domain.document.state.Events;

import java.io.Serializable;

public class EventRequest implements Serializable {

    private String id;
    private Events event;

    public String getId() {
        return id;
    }

    public Events getEvent() {
        return event;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEvent(Events event) {
        this.event = event;
    }
}
