package example.jgt.model.domain.document;

import example.jgt.model.domain.document.state.States;


public class TextMineDocument {
    private String id;
    private TextMineDocumentType type;
    private States state;

    public TextMineDocument(String id, TextMineDocumentType type, States state) {
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

    public TextMineDocumentType getType() {
        return type;
    }

    public void setType(TextMineDocumentType type) {
        this.type = type;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }
}
