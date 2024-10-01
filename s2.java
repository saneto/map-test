import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.StateMachine;

@Configuration
@EnableStateMachine
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
                .action(resourceActionHandlers.checkoutResource())
            .and()
            .withExternal()
                .source(ResourceState.IN_USE).target(ResourceState.AVAILABLE).event(ResourceEvent.RETURN)
                .action(resourceActionHandlers.returnResource())
            .and()
            .withExternal()
                .source(ResourceState.IN_USE).target(ResourceState.MAINTENANCE).event(ResourceEvent.MAINTAIN)
                .action(resourceActionHandlers.maintainResource())
            .and()
            .withExternal()
                .source(ResourceState.MAINTENANCE).target(ResourceState.DECOMMISSIONED).event(ResourceEvent.DECOMMISSION)
                .action(resourceActionHandlers.decommissionResource());
    }

    @Bean
    public StateMachine<ResourceState, ResourceEvent> resourceStateMachine() throws Exception {
        // Directly create and return the StateMachine instance
        return this.stateMachineFactory().getStateMachine();
    }

    @Bean
    public StateMachineFactory<ResourceState, ResourceEvent> stateMachineFactory() {
        // Create and return a new StateMachineFactory
        return new DefaultStateMachineFactory<>(this.stateMachineConfigurer());
    }

    private StateMachineConfigurer<ResourceState, ResourceEvent> stateMachineConfigurer() {
        // You may implement your own state machine configuration here
        return new ResourceStateMachineConfigurer();
    }
}