package example.jgt.service;

import example.jgt.model.domain.document.state.Events;
import example.jgt.model.domain.document.state.States;
import example.jgt.service.exception.DocumentException;
import example.jgt.service.persistence.DocumentStorageAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private DocumentStorageAdapter documentStorage;

    @Autowired
    private DocumentStateMachineManager stateMachineManager;

    public void processEvent(String id, Events event) {
        var doc = documentStorage.get(id).orElseThrow(() -> new DocumentException("Document does not exist: " + id));

        if (!stateMachineManager.hasDocument(id)) {
            if (doc.getState() != States.FINAL) {
                stateMachineManager.initWithState(doc);
            } else {
                throw new RuntimeException("Document is already in final state");
            }

        }
        var nextState = stateMachineManager.event(id, event).orElseThrow(() -> new RuntimeException("Internal error"));
        doc.setState(nextState);
        documentStorage.save(doc);
    }

    public String getState(String id) {
        return stateMachineManager.getState(id);
    }

}
