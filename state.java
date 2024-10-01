import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.builders.StateMachineConfig;

@Configuration
@EnableStateMachine(name = "multiTransitionStateMachine")
public class StateMachineConfig extends StateMachineConfigurerAdapter<State, Event> {

    @Override
    public void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
        states
            .withStates()
            .initial(State.CONTROL)
            .state(State.VALIDATE)
            .state(State.SEND)
            .state(State.ERROR)
            .end(State.FINAL);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {
        transitions
            .withExternal()
                .source(State.CONTROL).target(State.VALIDATE).event(Event.PUSH)
            .and()
            .withExternal()
                .source(State.VALIDATE).target(State.SEND).event(Event.VALIDATE)
            .and()
            .withExternal()
                .source(State.SEND).target(State.FINAL).event(Event.SEND)
            .and()
            .withExternal()
                .source(State.VALIDATE).target(State.ERROR).event(Event.ERROR) // From VALIDATE to ERROR
            .and()
            .withExternal()
                .source(State.SEND).target(State.ERROR).event(Event.ERROR); // From SEND to ERROR
    }

    @Bean
    public StateMachine<State, Event> stateMachine() {
        // Create a state machine instance using the state machine factory
        return stateMachineFactory().getStateMachine();
    }

    // Define a StateMachineFactory
    @Bean
    public StateMachineFactory<State, Event> stateMachineFactory() {
        return new StateMachineFactory<State, Event>() {
            @Override
            public StateMachine<State, Event> getStateMachine() {
                return new DefaultStateMachine<>(new StateMachineConfig()); // Adjusted for your setup
            }
        };
    }
}