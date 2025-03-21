package backend.profolio;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.profolio.domain.Project;
import backend.profolio.domain.ProjectRepository;
import backend.profolio.domain.Status;
import backend.profolio.domain.StatusRepository;

@SpringBootApplication
public class ProfolioApplication {

	private static final Logger log = LoggerFactory.getLogger(ProfolioApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProfolioApplication.class, args);
	}

	@Bean
	public CommandLineRunner profolio(ProjectRepository prepository, StatusRepository srepository) {
		return (args) -> {

			if (srepository.count() == 0) {
				log.info("Save statuses if empty");

				Status status1 =  new Status("Planned");
				Status status2 =  new Status("In Progress");
				Status status3 =  new Status("Completed");
				Status status4 =  new Status("On Hold");
				Status status5 =  new Status("Cancelled");

				srepository.save(status1);
				srepository.save(status2);
				srepository.save(status3);
				srepository.save(status4);
				srepository.save(status5);

				if (prepository.count() == 0) {
					log.info("Save projects if empty");
					prepository.save(new Project("Projekti A", LocalDate.of(2024, 10, 26), LocalDate.of(2025, 5, 7), status2));
					prepository.save(new Project("Projekti C", LocalDate.of(2025, 1, 13), LocalDate.of(2026, 7, 31), status4));
					prepository.save(new Project("Projekti 123", LocalDate.of(2023, 4, 3), LocalDate.of(2024, 10, 2), status3));
					prepository.save(new Project("Projekti F", LocalDate.of(2025, 10, 26), LocalDate.of(2027, 8, 30), status1));
				}
			}
			
				
			//pRepository.save(new Project("Project A", LocalDate.of(2024, 10, 5), LocalDate.of(2025, 04, 30)));
        	//pRepository.save(new Project("Project H", LocalDate.of(2023, 11, 27), LocalDate.of(2024, 9, 17)));
        	//pRepository.save(new Project("Project M", LocalDate.of(2025, 3, 16), LocalDate.of(2025, 6, 5)));

			log.info("Fetch all projects");
			for(Project project : prepository.findAll()) {
				log.info(project.toString());
			}			
		};
	
	}
	
}
