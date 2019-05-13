package pl.jstk.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import pl.jstk.Validation.BooksFormValidator;
import pl.jstk.enumerations.BookCategory;
import pl.jstk.enumerations.BookStatus;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookControllerMockTest {

    private MockMvc mockMvc;

    private static ThymeleafViewResolver viewResolver;

    @Mock
    private BookService bookServiceMock;

    @Mock
    private BooksFormValidator booksFormValidator;

    @InjectMocks
    private BookController bookController;

    private List<BookTo> bookList;

    @Before
    public void setup() {
        // given
        viewResolver = new ThymeleafViewResolver();
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setViewResolvers(viewResolver)
                .build();
        bookList = generateList();
    }

    private List<BookTo> generateList(){
        List<BookTo> bookList= new ArrayList<>();


        BookTo book1 = new BookTo();
        book1.setId(1L);
        book1.setTitle("Title 1");
        book1.setAuthors(new HashSet<>(
                Collections.singletonList("FirstAuthor")
        ));
        book1.setDescription("FirstDescription");
        book1.setStatus(BookStatus.FREE);
        book1.setCategories(new HashSet<>(
                Arrays.asList(BookCategory.DRAMA)
        ));

        BookTo book2 = new BookTo();
        book2.setId(2L);
        book2.setTitle("Title 2");
        book2.setAuthors(new HashSet<>(
                Arrays.asList("SecondAuthor", "Author")
        ));
        book2.setDescription("SecondDescription");
        book2.setStatus(BookStatus.LOAN);
        book2.setCategories(new HashSet<>(
                Arrays.asList(BookCategory.ACTION_AND_ADVENTURE,
                        BookCategory.HORROR)
        ));

        bookList.add(book1);
        bookList.add(book2);

        return bookList;
    }

}
