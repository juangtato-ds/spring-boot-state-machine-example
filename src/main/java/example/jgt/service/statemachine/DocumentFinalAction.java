package example.jgt.service.statemachine;

import example.jgt.model.domain.document.state.Events;
import example.jgt.model.domain.document.state.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.service.StateMachineService;

public class DocumentFinalAction extends AbstractDocumentStateAction {

    @Autowired
    @Lazy
    private StateMachineService<States, Events> service;

    @Override
    protected void executeAction(StateContext<States, Events> stateContext) {
        System.out.printf("This is the final state for doc %s, only notification may be pending%n",
                stateContext.getStateMachine().getId());
        service.releaseStateMachine(stateContext.getStateMachine().getId());
    }
}
