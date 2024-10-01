import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/statemachines/resource")
public class ResourceStateMachineController {

    @Autowired
    private StateMachine<ResourceState, ResourceEvent> resourceStateMachine;

    @PostMapping("/checkout/{resourceId}")
    public ResponseEntity<String> checkout(@PathVariable String resourceId) {
        resourceStateMachine.getExtendedState().getVariables().put("resourceId", resourceId);
        boolean accepted = resourceStateMachine.sendEvent(ResourceEvent.CHECKOUT);
        return accepted ? ResponseEntity.ok("Resource checked out, current state: " + resourceStateMachine.getState().getId()) :
                          ResponseEntity.badRequest().body("Checkout failed, current state: " + resourceStateMachine.getState().getId());
    }

    @PostMapping("/return/{resourceId}")
    public ResponseEntity<String> returnResource(@PathVariable String resourceId) {
        resourceStateMachine.getExtendedState().getVariables().put("resourceId", resourceId);
        boolean accepted = resourceStateMachine.sendEvent(ResourceEvent.RETURN);
        return accepted ? ResponseEntity.ok("Resource returned, current state: " + resourceStateMachine.getState().getId()) :
                          ResponseEntity.badRequest().body("Return failed, current state: " + resourceStateMachine.getState().getId());
    }

    @PostMapping("/maintain/{resourceId}")
    public ResponseEntity<String> maintain(@PathVariable String resourceId) {
        resourceStateMachine.getExtendedState().getVariables().put("resourceId", resourceId);
        boolean accepted = resourceStateMachine.sendEvent(ResourceEvent.MAINTAIN);
        return accepted ? ResponseEntity.ok("Resource maintenance started, current state: " + resourceStateMachine.getState().getId()) :
                          ResponseEntity.badRequest().body("Maintenance failed, current state: " + resourceStateMachine.getState().getId());
    }

    @PostMapping("/decommission/{resourceId}")
    public ResponseEntity<String> decommission(@PathVariable String resourceId) {
        resourceStateMachine.getExtendedState().getVariables().put("resourceId", resourceId);
        boolean accepted = resourceStateMachine.sendEvent(ResourceEvent.DECOMMISSION);
        return accepted ? ResponseEntity.ok("Resource decommissioned, current state: " + resourceStateMachine.getState().getId()) :
                          ResponseEntity.badRequest().body("Decommissioning failed, current state: " + resourceStateMachine.getState().getId());
    }

    @GetMapping("/state/{resourceId}")
    public ResponseEntity<String> getCurrentState(@PathVariable String resourceId) {
        // Assuming we can get the state from the service
        return ResponseEntity.ok("Current resource state: " + resourceStateMachine.getState().getId());
    }
}