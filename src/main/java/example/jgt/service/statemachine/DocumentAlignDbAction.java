package example.jgt.service.statemachine;

import example.jgt.model.domain.document.state.Events;
import example.jgt.model.domain.document.state.States;
import org.springframework.statemachine.StateContext;

public class DocumentAlignDbAction extends AbstractDocumentStateAction{
    @Override
    protected void executeAction(StateContext<States, Events> stateContext) {
        // noop - current update is made in parent
    }
}
