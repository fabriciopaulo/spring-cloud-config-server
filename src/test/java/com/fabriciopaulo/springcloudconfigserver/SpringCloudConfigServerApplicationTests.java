package com.fabriciopaulo.springcloudconfigserver;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = SpringCloudConfigServerApplication.class
)
@AutoConfigureMockMvc
class SpringCloudConfigServerApplicationTests {
	
	@Autowired
    private MockMvc mvc;

	@Test
	void getConfigFromDefaultProfileToPersonMicrosservice() throws Exception {
		mvc.perform(get("/person-microsservice/default")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$.name", is("person-microsservice")));
	}

	@Test
	void getConfigFromTestProfileToPersonMicrosservice() throws Exception {
		mvc.perform(get("/person-microsservice/test")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$.name", is("person-microsservice")));
	}
	
	@Test
	void getNotFoundWhenTryRetrieveConfigFromMicrosservice() throws Exception {
		mvc.perform(get("/foo/bar")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$.propertySources").isEmpty());
	}
}
