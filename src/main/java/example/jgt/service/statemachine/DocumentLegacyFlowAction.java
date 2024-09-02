package example.jgt.service.statemachine;

import example.jgt.model.domain.document.state.Events;
import example.jgt.model.domain.document.state.States;
import org.springframework.statemachine.StateContext;

public class DocumentLegacyFlowAction extends AbstractDocumentStateAction {

    @Override
    protected void executeAction(StateContext<States, Events> stateContext) {
        System.out.printf(
                "This is LEGACY flow for doc %s%n This will call the old code, where predict and custom-tags are concatenated",
                stateContext.getStateMachine().getId());
    }
}
