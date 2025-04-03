package backend.profolio.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository <Project, Long> {

    List <Project> findByProjectNameIgnoreCase (String projectName);
    List<Project> findByStatus_StatusNameIgnoreCase(String statusName);
    List <Project> findByTypes_TypeNameIgnoreCase (String typeName);
}
