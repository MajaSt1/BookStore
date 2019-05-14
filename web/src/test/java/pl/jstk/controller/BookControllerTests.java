package pl.jstk.controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import pl.jstk.Validation.BooksFormValidator;
import pl.jstk.constants.ViewNames;
import pl.jstk.enumerations.BookCategory;
import pl.jstk.enumerations.BookStatus;
import pl.jstk.service.BookService;
import pl.jstk.service.impl.BookServiceImpl;
import pl.jstk.to.BookTo;

import java.util.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookControllerTests {
// exception handling
//test as a specific user
private MockMvc mockMvc;

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
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        bookList = generateList();
    }

    @Test
    public void shouldReturnViewWhenFindAllBooks() throws Exception{
        //given
        // when
        when(bookServiceMock.findAllBooks()).thenReturn(bookList);
        // then
        Mockito.verify(bookServiceMock, times(1)).findAllBooks();
        mockMvc.perform(get("/books/showBooks"))//
                .andExpect(status().isOk())
                .andExpect(model().attribute("bookList", bookServiceMock.findAllBooks()))
                .andExpect(view().name(ViewNames.BOOKS));

        verifyNoMoreInteractions(bookServiceMock);
    }

    @Test
    public void shouldReturnViewWhenGetBook() throws Exception{
        //given
        //when
        when(bookServiceMock.findById(1L)).thenReturn(bookList.get(0));

        //then
        mockMvc.perform(get("/book?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.BOOK))
                .andExpect(model().attributeExists("book"));

        verify(bookServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(bookServiceMock);
    }

    @Test
    public void shouldNotGetViewWhenBookFail404NotFound() throws Exception{
        //given
        //when
        when(bookServiceMock.findById(1L)).thenReturn(null);
        //then
        mockMvc.perform(get("/book?id=10"))
                .andExpect(status().isNotFound());

        verify(bookServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(bookServiceMock);
    }

    @Test
    public void shouldReturnViewWhenAdd() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists())
                .andExpect(view().name(ViewNames.ADDBOOK));

        verify(bookServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(bookServiceMock);

    }
    @Test
    public void shouldReturnViewWhenAddBook() throws Exception{
        //given
        BookTo bookTo= new BookTo();
        //when
        when(bookServiceMock.saveBook(bookTo)).thenReturn(bookTo);
        //then
        mockMvc.perform(post("/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists())
                .andExpect(view().name(ViewNames.ADDBOOK));

        verify(bookServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(bookServiceMock);
    }


    @Test
    public void testShouldNotAddBook_fail_409_conflict() throws Exception{}

    @Test
    public void shouldReturnViewWhenDeleteBook() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(
                get("/delete?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.WELCOME));
        verify(bookServiceMock, times(1)).deleteBook(1L);
        verifyNoMoreInteractions(bookServiceMock);
    }

    @Test
    public void testShouldNotDeleteBook_404_not_found() throws Exception{
        //given
        BookTo book1= new BookTo();
        //when
        when(bookServiceMock.findById(book1.getId())).thenReturn(null);
        //then
        mockMvc.perform(
                delete("/books/delete",book1))
                .andExpect(status().isNotFound());
        verify(bookServiceMock, times(1)).findById(book1.getId());
        verifyNoMoreInteractions(bookServiceMock);
    }

    @Test
    public void shouldReturnViewWhenGetSearchForm() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get("/search"))
        .andExpect(status().isOk())
        .andExpect(view().name(ViewNames.FINDBOOK));
    }

    @Test
    public void shouldReturnViewWhenSearch() throws Exception{
        //given
        //when
        when(bookServiceMock.findByBooksByParam(any(BookTo.class))).thenReturn(bookList);
        //then
        mockMvc.perform(post("/books/search"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists())
                .andExpect(view().name(ViewNames.BOOKS));
    }

    @Test
    public void shouldNotFindById() throws Exception {
        mockMvc.perform(get("/books/book/{id}", -9))
                .andExpect(status().isBadRequest())
                .andDo(print()).andExpect(
                content().string(isEmptyString()));
    }

    @Test
    public void shouldFindById() throws Exception {
        mockMvc.perform(get("/books/book/{id}", 1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Java dla początkujących")));
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
        book2.setCategories(new LinkedHashSet<>(
                Arrays.asList(BookCategory.ACTION_AND_ADVENTURE,
                        BookCategory.HORROR)
        ));

        bookList.add(book1);
        bookList.add(book2);

        return bookList;
    }
}

//@WithMockUser(roles = {"ADMIN"}