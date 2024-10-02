import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.builders.StateMachineConfigurer;
import org.springframework.statemachine.config.builders.StateMachineConfigurers;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends StateMachineConfigurer<States, Events> {

    @Override
    public void configure(StateMachineConfigurer<States, Events> config) throws Exception {
        config
            .withStates()
                .initial(States.INITIAL)
                .state(States.PROCESSING, new ProcessingAction(), null)
                .end(States.COMPLETED)
                .state(States.FAILED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
            .withExternal()
                .source(States.INITIAL).target(States.PROCESSING).event(Events.START).action(new InitialAction())
            .and()
            .withExternal()
                .source(States.PROCESSING).target(States.COMPLETED).event(Events.COMPLETE)
            .and()
            .withExternal()
                .source(States.INITIAL).target(States.FAILED).event(Events.DATA_NOT_FOUND)
            .and()
            .withExternal()
                .source(States.PROCESSING).target(States.FAILED).event(Events.DATA_NOT_FOUND);
    }
}



import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class InitialAction implements Action<States, Events> {
    @Override
    public void execute(StateContext<States, Events> context) {
        // Logic to find data
        boolean dataFound = findData();
        if (!dataFound) {
            context.getStateMachine().sendEvent(Events.DATA_NOT_FOUND);
        } else {
            context.getStateMachine().sendEvent(Events.START);
        }
    }

    private boolean findData() {
        // Simulate data fetching logic
        return false; // Simulate failure
    }
}

public class ProcessingAction implements Action<States, Events> {
    @Override
    public void execute(StateContext<States, Events> context) {
        // Perform processing logic
        boolean success = performProcessing();
        if (success) {
            context.getStateMachine().sendEvent(Events.COMPLETE);
        } else {
            context.getStateMachine().sendEvent(Events.DATA_NOT_FOUND);
        }
    }

    private boolean performProcessing() {
        // Simulate processing logic
        return true; // Simulate success
    }
}