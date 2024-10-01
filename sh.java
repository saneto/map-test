import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ResourceActionHandlers {

    @Autowired
    private ResourceService resourceService; // Inject your service to interact with the resource table

    public Action<ResourceState, ResourceEvent> checkoutResource() {
        return context -> {
            // Business logic to checkout a resource
            String resourceId = context.getExtendedState().get("resourceId", String.class);
            resourceService.checkoutResource(resourceId);
            System.out.println("Resource checked out: " + resourceId);
        };
    }

    public Action<ResourceState, ResourceEvent> returnResource() {
        return context -> {
            // Business logic to return a resource
            String resourceId = context.getExtendedState().get("resourceId", String.class);
            resourceService.returnResource(resourceId);
            System.out.println("Resource returned: " + resourceId);
        };
    }

    public Action<ResourceState, ResourceEvent> maintainResource() {
        return context -> {
            // Business logic to maintain a resource
            String resourceId = context.getExtendedState().get("resourceId", String.class);
            resourceService.maintainResource(resourceId);
            System.out.println("Resource maintenance started: " + resourceId);
        };
    }

    public Action<ResourceState, ResourceEvent> decommissionResource() {
        return context -> {
            // Business logic to decommission a resource
            String resourceId = context.getExtendedState().get("resourceId", String.class);
            resourceService.decommissionResource(resourceId);
            System.out.println("Resource decommissioned: " + resourceId);
        };
    }
}