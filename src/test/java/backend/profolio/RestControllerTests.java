package backend.profolio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class RestControllerTests {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    // Mock pystytys
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    // Endpoint status 200 testi
    @Test
    public void shouldReturnStatusOk() throws Exception {
        mockMvc.perform(get("/projects"))
               .andExpect(status().isOk());  // Assert that the status code is 200 OK
    }

    // JSON content-testi
    @Test
    public void shouldReturnContentTypeApplicationJson() throws Exception {
        mockMvc.perform(get("/projects"))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Assert the content type is JSON
               .andExpect(status().isOk());  // Assert that the status code is 200 OK
    }
   
}
