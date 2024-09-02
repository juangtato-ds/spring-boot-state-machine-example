package example.jgt.service;

import example.jgt.model.domain.document.TextMineDocument;
import example.jgt.model.domain.document.state.Events;
import example.jgt.model.domain.document.state.States;
import example.jgt.service.exception.EventException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DocumentStateMachineManager {

    private static final String DOC_KEY = "doc";

    @Autowired
    private StateMachineService<States, Events> service;

    public void init(TextMineDocument doc) {
        var state = generate(doc.getId());
        state.getExtendedState().getVariables().put(DOC_KEY, doc);
        state.sendEvent(Events.START);
    }

    public void initWithState(TextMineDocument doc) {
        var state = generate(doc.getId(), doc.getState());
        state.getExtendedState().getVariables().put(DOC_KEY, doc);
    }

    public Optional<States> event(String id, Events event) {
        var stateMachine = service.acquireStateMachine(id);

        if (stateMachine == null) {
            throw new RuntimeException("No state machine"); // This is an unexpected error
        }
        boolean eventSent = stateMachine.sendEvent(event);

        if (!eventSent) {
            System.out.println("Invalid event " + event);
            throw new EventException(String.format("Invalid event '%s' for '%s'", event, id));
        }

        var state = stateMachine.getState().getId();

        if (stateMachine.isComplete()) {
            System.out.println("Machine is completed => release machine to reduce usage");
            service.releaseStateMachine(id);
        }
        return Optional.ofNullable(state);
    }

    private StateMachine<States, Events> generate(String id) {
        return this.service.acquireStateMachine(id);
    }

    private StateMachine<States, Events> generate(String id, States currentState) {
        var result = generate(id);
        result.getStateMachineAccessor().doWithAllRegions(access -> access.resetStateMachine(
                new DefaultStateMachineContext<>(currentState, null, null, null, null, id)));
        return result;
    }

    public boolean hasStateMachine(String id) {
        // HACK - we can create or expand our own machine service.
        // Default implementation may not be suitable, but can be used as startup
        return ((DefaultStateMachineService) this.service).hasStateMachine(id);
    }

    public boolean hasDocument(String id) {
        var doc = service.acquireStateMachine(id, false).getExtendedState().getVariables().get(DOC_KEY);
        return (doc != null);
    }

    public String getState(String id) {
        if (hasStateMachine(id)) {
            return this.service.acquireStateMachine(id, false).getState().getId().toString();
        } else {
            return String.format("State machine for %s does not exist", id);
        }
    }
}
