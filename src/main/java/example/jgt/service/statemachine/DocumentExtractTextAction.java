package example.jgt.service.statemachine;

import example.jgt.model.domain.document.state.Events;
import example.jgt.model.domain.document.state.States;
import org.springframework.statemachine.StateContext;

public class DocumentExtractTextAction extends AbstractDocumentStateAction {

    @Override
    protected void executeAction(StateContext<States, Events> stateContext) {
        System.out.printf("Must extract text from %s%n We will wait for callback",
                stateContext.getStateMachine().getId());
    }
}
