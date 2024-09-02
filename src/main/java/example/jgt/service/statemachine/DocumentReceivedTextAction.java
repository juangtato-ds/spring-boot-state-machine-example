package example.jgt.service.statemachine;

import example.jgt.model.domain.document.state.Events;
import example.jgt.model.domain.document.state.States;
import org.springframework.statemachine.StateContext;

public class DocumentReceivedTextAction extends AbstractDocumentStateAction {

    private int count = 0;

    @Override
    protected void executeAction(StateContext<States, Events> stateContext) {
        System.out.printf("We have the text for %s%n We must check if there are custom-tags to send",
                stateContext.getStateMachine().getId());
        count++;
        if (count % 2 == 0) {
            System.out.println("Document has custom-tags to answer");
            stateContext.getStateMachine().sendEvent(Events.SEND_CUSTOM_TAGS);
        } else {
            System.out.println("No custom-tags to answer");
            stateContext.getStateMachine().sendEvent(Events.END_FLOW);
        }
    }
}
