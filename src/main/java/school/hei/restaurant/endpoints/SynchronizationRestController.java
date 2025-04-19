package school.hei.restaurant.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.restaurant.service.SynchronizationService;
import school.hei.restaurant.service.exception.ServerException;

@RestController
@RequiredArgsConstructor
public class SynchronizationRestController {
    private final SynchronizationService synchronizationService;

    @PostMapping("/synchronization")
    public ResponseEntity<Object> synchronizeDatabase() {
        try {
            synchronizationService.synchronizeData();
            return ResponseEntity.ok().body("Synchronization done");
        } catch (ServerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

