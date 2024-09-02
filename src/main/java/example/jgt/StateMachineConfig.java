package example.jgt;

import example.jgt.model.domain.document.state.Events;
import example.jgt.model.domain.document.state.States;
import example.jgt.service.statemachine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Bean
    public StateMachineService<States, Events> stateMachineService(
            @Autowired StateMachineFactory<States, Events> stateMachineFactory) {
        return new DefaultStateMachineService<>(stateMachineFactory);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        // @formatter:off
        states.withStates()
            .initial(
                States.INITIAL, stateContext -> System.out.println("Initial state for " + stateContext.getStateMachine().getId()
                        + " fetch document and populate extended variables")
            )
            .state(States.PENDING, initialAction())
            .state(States.LEGACY_FLOW, legacyFlowAction())
            .state(States.EXTRACTING_TEXT, extractTextAction())
            .state(States.TEXT_EXTRACTED, receivedText())
            .state(States.ANSWERING_CUSTOM_TAGS, sendCustomTagsAction())
            .state(States.FINAL, finalAction())
            .end(States.FINAL);
        // @formatter:on
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        // @formatter:off
        transitions
                .withExternal()
                    .source(States.INITIAL)
                    .target(States.PENDING)
                    .event(Events.START)
                .and()
                // Legacy flow
                .withExternal()
                    .source(States.PENDING)
                    .target(States.LEGACY_FLOW)
                    .event(Events.START_LEGACY)
                .and()
                .withExternal()
                    .source(States.LEGACY_FLOW)
                    .target(States.FINAL)
                    .event(Events.END_FLOW)
                // New flow
                .and()
                .withExternal()
                    .source(States.PENDING)
                    .target(States.EXTRACTING_TEXT)
                    .event(Events.EXTRACT_TEXT)
                .and()
                .withExternal()
                    .source(States.EXTRACTING_TEXT)
                    .target(States.TEXT_EXTRACTED)
                    .event(Events.TEXT_RECEIVED)
                .and()
                .withExternal()
                    .source(States.TEXT_EXTRACTED)
                    .target(States.ANSWERING_CUSTOM_TAGS)
                    .event(Events.SEND_CUSTOM_TAGS)
                .and()
                .withExternal()
                    .source(States.TEXT_EXTRACTED)
                    .target(States.FINAL)
                    .event(Events.END_FLOW)
                .and()
                .withExternal()
                    .source(States.ANSWERING_CUSTOM_TAGS)
                    .target(States.FINAL)
                    .event(Events.END_FLOW)
                ;
        // @formatter:on
    }

    @Bean
    public DocumentInitAction initialAction() {
        return new DocumentInitAction();
    }

    @Bean
    DocumentExtractTextAction extractTextAction() {
        return new DocumentExtractTextAction();
    }

    @Bean
    DocumentLegacyFlowAction legacyFlowAction() {
        return new DocumentLegacyFlowAction();
    }

    @Bean
    DocumentSendCustomTagsAction sendCustomTagsAction() {
        return new DocumentSendCustomTagsAction();
    }

    @Bean
    DocumentFinalAction finalAction() {
        return new DocumentFinalAction();
    }

    @Bean
    DocumentReceivedTextAction receivedText() {
        return new DocumentReceivedTextAction();
    }

    /* No use
    @Bean
    DocumentAlignDbAction alignDbAction() {
        return new DocumentAlignDbAction();
    }
     */

}
