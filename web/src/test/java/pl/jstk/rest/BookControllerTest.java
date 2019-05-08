package pl.jstk.rest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import pl.jstk.service.impl.BookServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private BookServiceImpl bookService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BookRestController(bookService)).build();
    }

    @Test
    public void shouldFindAll() throws Exception {
        mockMvc.perform(get("/books"))
               .andExpect(status().isOk())
               .andDo(print())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(jsonPath("$[0].title", is("Java dla początkujących")));
        // and others check for right json
    }

    @Test
    public void shouldNotFindById() throws Exception {
        mockMvc.perform(get("/books/{id}", -9))
                .andExpect(status().isBadRequest())
                .andDo(print()).andExpect(
                content().string(isEmptyString()));
    }

}
