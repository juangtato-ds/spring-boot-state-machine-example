package example.jgt.service.statemachine;

import example.jgt.model.domain.document.state.Events;
import example.jgt.model.domain.document.state.States;
import org.springframework.statemachine.StateContext;

public class DocumentSendCustomTagsAction extends AbstractDocumentStateAction {

    @Override
    protected void executeAction(StateContext<States, Events> stateContext) {
        System.out.printf("We must send custom-tags to service for %s%n", stateContext.getStateMachine().getId());
    }
}
