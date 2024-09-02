package example.jgt.rest.model;

public class DocumentResponse {
    private String id;
    private String type;
    private String state;

    public DocumentResponse(String id, String type, String state) {
        this.id = id;
        this.type = type;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
