package backend.profolio.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository <Project, Long> {

    List <Project> findByProjectName (String projectName);
    List <Project> findByStatus_StatusName (String statusName);
    List <Project> findByTypes_TypeName (String typeName);
    
       
}
