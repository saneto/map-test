import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ResourceService {

    // Inject your repository or DAO for database operations
    @Autowired
    private ResourceRepository resourceRepository;

    public void checkoutResource(String resourceId) {
        // Logic to checkout resource in database
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new ResourceNotFoundException(resourceId));
        resource.setStatus(ResourceState.IN_USE);
        resourceRepository.save(resource);
    }

    public void returnResource(String resourceId) {
        // Logic to return resource to available status
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new ResourceNotFoundException(resourceId));
        resource.setStatus(ResourceState.AVAILABLE);
        resourceRepository.save(resource);
    }

    public void maintainResource(String resourceId) {
        // Logic to set resource to maintenance
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new ResourceNotFoundException(resourceId));
        resource.setStatus(ResourceState.MAINTENANCE);
        resourceRepository.save(resource);
    }

    public void decommissionResource(String resourceId) {
        // Logic to decommission resource
        resourceRepository.deleteById(resourceId); // or update status to DECOMMISSIONED
    }
}