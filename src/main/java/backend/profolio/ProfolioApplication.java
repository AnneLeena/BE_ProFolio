package backend.profolio;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.profolio.domain.AppUser;
import backend.profolio.domain.AppUserRepository;
import backend.profolio.domain.Project;
import backend.profolio.domain.ProjectRepository;
import backend.profolio.domain.Status;
import backend.profolio.domain.StatusRepository;
import backend.profolio.domain.Type;
import backend.profolio.domain.TypeRepository;


@SpringBootApplication
public class ProfolioApplication {
    
	private static final Logger log = LoggerFactory.getLogger(ProfolioApplication.class);

    public static void main(String[] args) {

		SpringApplication.run(ProfolioApplication.class, args);
	}

	@Bean
	public CommandLineRunner profolio(ProjectRepository prepository, StatusRepository srepository, TypeRepository trepository, AppUserRepository urepository) {
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
			
			if (trepository.count() == 0) {

				Type type1 = new Type("Application");
				Type type2 = new Type("Software");
				Type type3 = new Type("Database");
				Type type4 = new Type("Web development");

				trepository.save(type1);
				trepository.save(type2);
				trepository.save(type3);
				trepository.save(type4);
					

				if (prepository.count() == 0) {
					log.info("Save projects if empty");

					prepository.save(new Project("Projekti 123", LocalDate.of(2023, 4, 3), LocalDate.of(2024, 10, 2), status3, type1, type2));
					prepository.save(new Project("Projekti A", LocalDate.of(2024, 10, 26), LocalDate.of(2025, 5, 7), status2, type4, type1));
					prepository.save(new Project("Projekti C", LocalDate.of(2025, 1, 13), LocalDate.of(2026, 7, 31), status4, type2));
					prepository.save(new Project("Projekti testi", LocalDate.of(2023, 4, 3), LocalDate.of(2024, 10, 2), status3, type3, type4));
					prepository.save(new Project("Projekti F", LocalDate.of(2025, 10, 26), LocalDate.of(2027, 8, 30), status1, type2));
				}
			}}
			
			if (urepository.count() == 0) {
				log.info("Save users if empty");
				urepository.save(
						new AppUser("user", "$2a$10$kmHBHgBP6kVMZ.K9Pq3tG.u5ExbcH/RAKPzU5SiGZ4pBPCpQcSWfK", "USER"));
				urepository.save(
						new AppUser("admin", "$2a$10$LSyGuLy6/w8VVbGlTtQ7ne4igDgReUXwEf2ZtvTmFKdJNiaQYfd/O", "ADMIN"));
			}
			
			log.info("Fetch all projects");
			for(Project project : prepository.findAll()) {
				log.info(project.toString());
			}			
		};
	}
}
