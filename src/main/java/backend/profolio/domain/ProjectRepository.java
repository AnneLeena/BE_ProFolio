package backend.profolio.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository <Project, Long> {

    List<Project> findByStatus_StatusName (String statusName);
       
}
