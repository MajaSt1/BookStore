package pl.jstk.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.ui.Model;
import pl.jstk.constants.ViewNames;
import pl.jstk.exception.BookAdditionException;
import pl.jstk.exception.BookDeletionException;
import pl.jstk.to.BookTo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookControllerAdviceTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    @Test
    public void shouldHandleInvalidIdException() throws Exception{
        //given
        //when
        doThrow(BookAdditionException.class).when(bookController).getBook(any(Long.class),any(Model.class));
        ResultActions resultActions = mockMvc.perform(post("/books/book").param("id", "0"));
        //then
        Mockito.verify(bookController,times(1)).getBook(any(Long.class),any(Model.class));
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void shouldHandleBookAdditionException() throws Exception{
        //given
        //when
        doThrow(BookAdditionException.class).when(bookController).addBook(any(BookTo.class));
        ResultActions resultActions = mockMvc.perform(post("/books/add").requestAttr("newBook", new BookTo()));
        //then
        Mockito.verify(bookController,times(1)).addBook(any(BookTo.class));
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void shouldHandleBookDeletionException() throws Exception{
        //given
        //when
        doThrow(BookDeletionException.class).when(bookController).deleteBook(any(BookTo.class));
        ResultActions resultActions = mockMvc.perform(post("/books/delete").requestAttr("newBook", new BookTo()));
        //then
        Mockito.verify(bookController,times(1)).deleteBook(any(BookTo.class));
        resultActions.andExpect(status().isBadRequest());
    }

}
