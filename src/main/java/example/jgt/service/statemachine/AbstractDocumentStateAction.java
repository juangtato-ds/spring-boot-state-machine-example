package example.jgt.service.statemachine;

import example.jgt.model.domain.document.state.Events;
import example.jgt.model.domain.document.state.States;
import example.jgt.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public abstract class AbstractDocumentStateAction implements Action<States, Events> {
    @Autowired
    protected DocumentService service;

    @Override
    public void execute(StateContext<States, Events> stateContext) {
        this.executeAction(stateContext);
        this.service.setState(stateContext.getStateMachine().getId(), stateContext.getTarget().getId());
    }

    protected abstract void executeAction(StateContext<States, Events> stateContext);
}
