package backend.profolio.web;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backend.profolio.domain.Project;
import backend.profolio.domain.ProjectRepository;
import backend.profolio.domain.Status;
import backend.profolio.domain.StatusRepository;
import backend.profolio.domain.TypeRepository;

@RestController

public class StatusRESTController {

    private static final Logger log = LoggerFactory.getLogger(StatusRESTController.class);

    private final StatusRepository srepository;
    private ProjectRepository prepository;
    private TypeRepository trepository;

    public StatusRESTController(ProjectRepository prepository, StatusRepository srepository,
            TypeRepository trepository) {
        this.prepository = prepository;
        this.srepository = srepository;
        this.trepository = trepository;
    }

    // list of statuses
    @GetMapping("/statuses")
    public Iterable<Status> getStatuses() {
        log.info("fetch & return statuses");
        return srepository.findAll();
    }

    // add a new status
    @PostMapping("/statuses")
    Status newStatus(@RequestBody Status newStatus) {
        log.info("save a new status " + newStatus);
        return srepository.save(newStatus);
    }

    // find specific status
    @GetMapping("/statuses/{statusId}")
    Optional<Status> getStatusById(@PathVariable Long statusId) {
        log.info("Fetching status with ID: " + statusId);
        return srepository.findById(statusId);
    }

    // edit status
    @PutMapping("/statuses/{statusId}")
    Status editStatusById(@RequestBody Status editedStatus, @PathVariable Long statusId) {
        log.info("edit status " + editedStatus);
        editedStatus.setStatusId(statusId);
        return srepository.save(editedStatus);
    }

    // delete status
    @DeleteMapping("/statuses/{statusId}")
    public Iterable<Status> deleteStatus(@PathVariable Long statusId) {
        log.info("delete status, id = " + statusId);
        srepository.deleteById(statusId);
        return srepository.findAll();
    }

    // find status by statusName
    @GetMapping("/statuses/name/{statusName}")
    List<Status> getStatusByName(@PathVariable String statusName) {
        log.info("find statuses, statusName = " + statusName);
        return srepository.findByStatusName (statusName);
    }

}
