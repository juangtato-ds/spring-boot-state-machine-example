package example.jgt.service.persistence;

import example.jgt.model.domain.document.TextMineDocument;
import example.jgt.model.domain.document.TextMineDocumentType;
import example.jgt.model.domain.document.state.States;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DocumentStorageAdapter {

    private final Map<String, TextMineDocument> storage = new HashMap<>();


    public DocumentStorageAdapter() {
        for (var status : States.values()) {
            var doc = this.dummyDocument(TextMineDocumentType.LEGACY, status);
            this.storage.put(doc.getId(), doc);
            doc = this.dummyDocument(TextMineDocumentType.NO_DEFAULT_FIELDS, status);
            this.storage.put(doc.getId(), doc);
        }
    }

    public List<TextMineDocument> list() {
        return new ArrayList<>(this.storage.values());
    }

    public TextMineDocument save(TextMineDocument document) {
        storage.put(document.getId(), document);
        return document;
    }

    public Optional<TextMineDocument> get(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    private TextMineDocument dummyDocument(TextMineDocumentType type, States state) {
        return new TextMineDocument(UUID.randomUUID().toString(), type, state);
    }

    public void update(TextMineDocument doc) {
        this.storage.put(doc.getId(), doc);
    }

    public void delete(String id){
        storage.remove(id);
    }
}
