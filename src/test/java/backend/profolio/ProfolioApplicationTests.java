package backend.profolio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import backend.profolio.domain.ProjectRepository;

@SpringBootTest
class ProfolioApplicationTests {

	@Autowired
	private ProjectRepository prepository;

	@Test
	void contextLoads() {
	}

	//yhteys
	@Test
	public void testDatabaseConnection(){
		assertThat(prepository).isNotNull();
		assertThat(prepository.count()).isNotNull();

	}

}
