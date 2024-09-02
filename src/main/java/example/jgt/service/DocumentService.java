package example.jgt.service;

import example.jgt.model.domain.document.TextMineDocumentType;
import example.jgt.model.domain.document.TextMineDocument;
import example.jgt.model.domain.document.state.States;
import example.jgt.service.persistence.DocumentStorageAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private DocumentStorageAdapter documentStorage;

    public TextMineDocument createDocument(TextMineDocumentType type) {
        TextMineDocument doc = new TextMineDocument(UUID.randomUUID().toString(), type, States.INITIAL);
        return documentStorage.save(doc);
    }

    public List<TextMineDocument> list() {
        return this.documentStorage.list();
    }

    public TextMineDocument get(String id) {
        return this.documentStorage.get(id).orElseThrow();
    }

    public void update(TextMineDocument doc) {
        this.documentStorage.update(doc);
    }

    public void delete(String id) {
        if (documentStorage.get(id).isEmpty()) {
            throw new RuntimeException("Document does not exist: " + id);
        }
        documentStorage.delete(id);
    }

    public void setState(String id, States state) {
        var doc = this.get(id);
        doc.setState(state);
        this.update(doc);
    }

}