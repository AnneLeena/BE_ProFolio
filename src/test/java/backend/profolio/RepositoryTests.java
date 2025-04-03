package backend.profolio;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import backend.profolio.domain.Project;
import backend.profolio.domain.ProjectRepository;
import backend.profolio.domain.Status;
import backend.profolio.domain.StatusRepository;
import backend.profolio.domain.Type;
import backend.profolio.domain.TypeRepository;


@SpringBootTest
//@DataJPATest kun testataan sisäistä, esim. H2-kantaa

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//testit suoritetaan määritellyn tietokannan (esim. PostgreSQL) 
//kanssa sen sijaan, että käytettäisiin sisäistä testitietokantaa (esim. H2)

public class RepositoryTests {

    @Autowired
    private ProjectRepository prepository; 
    @Autowired 
    StatusRepository srepository;
    @Autowired 
    TypeRepository trepository;

    //Etsi projekti nimellä -testi
    @Test
    public void findByProjectNameShouldReturnProject () {
        List<Project> projects = prepository.findByProjectNameIgnoreCase("projekti 123");
        assertThat(projects).hasSize(1);
        assertThat(projects.get(0).getProjectName()).isEqualTo("Projekti 123");
    }

    //Luo uusi projekti -testi
    @Test
    public void createNewProject () {
        Status status = new Status ("TESTI");
        srepository.save(status);
        Type type = new Type ("Testityyppi");
        trepository.save(type);
        Project project = new Project ("Testi-Projekti",  LocalDate.of(2026, 8, 10), LocalDate.of(2027, 2, 02), status, type);
        
        prepository.save(project);
        assertThat(project.getId()).isNotNull();
        assertThat(project.getProjectName()).isEqualTo("Testi-Projekti");
        assertThat(project.getStartDate()).isEqualTo(LocalDate.of(2026, 8, 10));
        assertThat(project.getEndDate()).isEqualTo(LocalDate.of(2027, 2, 2));
    }

    //Poista projekti -testi
    @Test
    public void deleteProject () {
        
		Status status = new Status("COMING");
        status = srepository.save(status);
        Type type1 = new Type("TestiType1");
        Type type2 = new Type("TestiType2");
        trepository.save(type1);
        trepository.save(type2);

        Project project = new Project("Testi2", LocalDate.of(2026, 10, 10), LocalDate.of(2027, 02, 02), status, type1, type2);
        project = prepository.save(project);
    
        assertThat(project.getId()).isNotNull();
    
        prepository.delete(project);
        List<Project> newProjects = prepository.findByProjectNameIgnoreCase("testi2");
        assertThat(newProjects).isEmpty();
     
    }
    // Loppupvm on alkupvm jälkeen -testi
    @Test
    public void endDateShouldBeAfterStartDate() {
        Status status = new Status("COMING");
        status = srepository.save(status);
        Type type = new Type("Testityyppi");
        trepository.save(type);

        Project project = new Project("TestiProject", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), status, type);
        prepository.save(project);

        assertThat(project.isEndDateAfterStartDate()).isTrue();
}
    // Loppupvm ei saa olla ennen alkupvm-testi
    @Test
        public void endDateShouldNotBeBeforeStartDate() {
        Status status = new Status("COMING");
        status = srepository.save(status);
        Type type = new Type("Testityyppi");
        trepository.save(type);

        // Virheellinen projekti!!
        Project project = new Project("InvalidProject", LocalDate.of(2025, 12, 31), LocalDate.of(2025, 1, 1), status, type);
        assertThat(project.isEndDateAfterStartDate()).isFalse();
}

}