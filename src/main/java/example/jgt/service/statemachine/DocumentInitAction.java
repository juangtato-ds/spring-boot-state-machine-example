package example.jgt.service.statemachine;

import example.jgt.model.domain.document.TextMineDocument;
import example.jgt.model.domain.document.TextMineDocumentType;
import example.jgt.model.domain.document.state.Events;
import example.jgt.model.domain.document.state.States;
import org.springframework.statemachine.StateContext;

public class DocumentInitAction extends AbstractDocumentStateAction {

    @Override
    protected void executeAction(StateContext<States, Events> stateContext) {
        System.out.println("Now I must check the type and run particular logic");
        // We can use variables in the state machine
        TextMineDocument actualDoc;
        Object temp = stateContext.getExtendedState().getVariables().get("doc");
        if (temp instanceof TextMineDocument) {
            actualDoc = (TextMineDocument) temp;
        }
        // Or we can use service
        actualDoc = service.get(stateContext.getStateMachine().getId());

        if (actualDoc.getType() == TextMineDocumentType.LEGACY) {
            System.out.println("Operation for Legacy flow: call endpoint for prediction, and wait for callback");
            var result = stateContext.getStateMachine().sendEvent(Events.START_LEGACY);
            System.out.println("START_LEGACY sent: " + result);
        } else {
            System.out.println("Operation for New flow: call endpoint for extracting text, and wait for callback");
            var result = stateContext.getStateMachine().sendEvent(Events.EXTRACT_TEXT);
            System.out.println("EXTRACT_TEXT sent: " + result);
        }

    }
}
