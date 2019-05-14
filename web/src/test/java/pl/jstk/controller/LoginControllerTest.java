package pl.jstk.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LoginControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new LoginController())
                .build();
    }

    @Test
    public void shouldReturnLoginView() throws Exception {
        //given
        //when
        ResultActions resultActions= mockMvc.perform(get("/logon"));
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.LOGIN));
    }

    @Test
    public void shouldReturnAccessDeniedView() throws Exception {
        //given
        //when
        ResultActions resultActions= mockMvc.perform(get("/error403")); //post?
        //then
        String expectedMessage =  "You do not have permission to access this page!";
        resultActions.andExpect(view().name(ViewNames.ERROR403))
                .andExpect(model().attribute(ModelConstants.MESSAGE, expectedMessage));

    }
}
