import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine(name = "resourceStateMachine")
public class ResourceStateMachineConfig extends StateMachineConfigurerAdapter<ResourceState, ResourceEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<ResourceState, ResourceEvent> states) throws Exception {
        states
            .withStates()
            .initial(ResourceState.AVAILABLE)
            .state(ResourceState.IN_USE)
            .state(ResourceState.MAINTENANCE)
            .state(ResourceState.DECOMMISSIONED)
            .end(ResourceState.FINAL);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ResourceState, ResourceEvent> transitions) throws Exception {
        transitions
            .withExternal()
                .source(ResourceState.AVAILABLE).target(ResourceState.IN_USE).event(ResourceEvent.CHECKOUT)
            .and()
            .withExternal()
                .source(ResourceState.IN_USE).target(ResourceState.AVAILABLE).event(ResourceEvent.RETURN)
            .and()
            .withExternal()
                .source(ResourceState.IN_USE).target(ResourceState.MAINTENANCE).event(ResourceEvent.MAINTAIN)
            .and()
            .withExternal()
                .source(ResourceState.MAINTENANCE).target(ResourceState.DECOMMISSIONED).event(ResourceEvent.DECOMMISSION);
    }

    @Bean
    public StateMachine<ResourceState, ResourceEvent> resourceStateMachine() {
        return super.build(); // Spring will manage this
    }
}