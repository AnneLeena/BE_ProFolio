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

@SpringBootApplication
public class ProfolioApplication {

	private static final Logger log = LoggerFactory.getLogger(ProfolioApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProfolioApplication.class, args);
	}

	@Bean
	public CommandLineRunner profolio(ProjectRepository pRepository) {
		return (args) -> {

			log.info("Save projects if empty");
			pRepository.save(new Project("Project A", LocalDate.of(2024, 10, 5), LocalDate.of(2025, 04, 30)));
        	pRepository.save(new Project("Project H", LocalDate.of(2023, 11, 27), LocalDate.of(2024, 9, 17)));
        	pRepository.save(new Project("Project M", LocalDate.of(2025, 3, 16), LocalDate.of(2025, 6, 5)));

			log.info("Fetch all projects");
			for(Project project : pRepository.findAll()) {
				log.info(project.toString());
			}			
		};
	
	}
	
}
