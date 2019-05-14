package pl.jstk.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jstk.Validation.BooksValidator;
import pl.jstk.enumerations.BookCategory;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.LinkedHashSet;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ValidatorTest {

    private BookTo bookTo = new BookTo();

    @Mock
    private BookService bookService;

    @InjectMocks
    private BooksValidator bookValidator;

    @Before
    public void setUp() {
        bookTo.setId(1L);
        bookTo.setTitle("Title");
        bookTo.setAuthors(new LinkedHashSet<String>() {{
            add("Author1");
            add("Author2");
        }});
        bookTo.setDescription("Description");
        bookTo.setCategories(new LinkedHashSet<BookCategory>() {{
            add(BookCategory.SATIRE);
            add(BookCategory.ACTION_AND_ADVENTURE);
        }});
    }

    @Test
    public void shouldNotThrowAnExceptionValidAddition() {
    }

    @Test
    public void shouldThrowAnExceptionNullTitle() {
    }

    @Test
    public void shouldThrowAnExceptionEmptyTitle() {
        // given
        bookTo.setTitle("");
        //when
        //then
        bookValidator.validateBookAddition(bookTo);
    }
}
