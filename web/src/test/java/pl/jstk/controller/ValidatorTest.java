package pl.jstk.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jstk.Validation.BooksFormValidator;
import pl.jstk.enumerations.BookCategory;
import pl.jstk.exception.BookAdditionException;
import pl.jstk.exception.BookDeletionException;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ValidatorTest {

    private BookTo bookTo = new BookTo();

    @Mock
    private BookService bookService;

    @InjectMocks
    private BooksFormValidator bookValidator;

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
    public void shouldNotThrowAnExceptionValidAdditionTitle() {
        // given
        bookTo.setTitle("Title");
        //when
        //then
        bookValidator.validateBookAddition(bookTo);

    }

    @Test(expected = BookAdditionException.class)
    public void shouldThrowAnExceptionNullTitle() {
        // given
        bookTo.setTitle(null);
        //when
        //then
        bookValidator.validateBookAddition(bookTo);
    }

    @Test(expected = BookAdditionException.class)
    public void shouldThrowAnExceptionEmptyTitle() {
        // given
        bookTo.setTitle("");
        //when
        //then
        bookValidator.validateBookAddition(bookTo);
    }

    @Test
    public void shouldNotThrowAnExceptionValidAdditionAuthors() {
        // given
        bookTo.setAuthors(new HashSet<>(Collections.singletonList("Author")));
        //when
        //then
        bookValidator.validateBookAddition(bookTo);

    }

    @Test(expected = BookAdditionException.class)
    public void shouldThrowAnExceptionNullAuthors() {
        // given
        bookTo.setAuthors(null);
        //when
        //then
        bookValidator.validateBookAddition(bookTo);
    }

    @Test(expected = BookAdditionException.class)
    public void shouldThrowAnExceptionEmptyAuthors() {
        // given
        bookTo.setAuthors(new HashSet<>());
        //when
        //then
        bookValidator.validateBookAddition(bookTo);
    }

    @Test
    public void shouldNotThrowAnExceptionValidAdditionDescription() {
        // given
        bookTo.setDescription("Description");
        //when
        //then
        bookValidator.validateBookAddition(bookTo);
    }

    @Test(expected = BookAdditionException.class)
    public void shouldThrowAnExceptionNullDescription() {
        // given
        bookTo.setDescription(null);
        //when
        //then
        bookValidator.validateBookAddition(bookTo);
    }

    @Test(expected = BookAdditionException.class)
    public void shouldThrowAnExceptionEmptyDescription() {
        // given
        bookTo.setDescription("");
        //when
        //then
        bookValidator.validateBookAddition(bookTo);
    }

    @Test
    public void shouldNotThrowAnExceptionValidDeletion() {
        // given
        //when
        //then
        bookValidator.validateBookDeletion(1L);

    }

}
