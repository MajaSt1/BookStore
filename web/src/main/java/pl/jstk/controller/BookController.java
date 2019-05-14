package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.jstk.Validation.BooksFormValidator;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/showBooks")
    public String showAllBooks(Model model) {
        model.addAttribute("bookList", bookService.findAllBooks());

        return ViewNames.BOOKS;
    }

    @GetMapping("/getBook")
    public String getBook(@RequestParam("id") Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));

        return ViewNames.BOOK;
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddBookForm() {
        ModelAndView modelAndView = new ModelAndView(ViewNames.ADDBOOK);
        modelAndView.addObject("newBook", new BookTo());
        return modelAndView;
    }

    @PostMapping(value = "/add")
    public String addBook(@ModelAttribute("newBook") BookTo book) {

        BookTo bookTo= bookService.saveBook(book);

        return ViewNames.BOOKS;
    }

    @GetMapping(value = "/delete")
    public String deleteBook(@RequestParam("id") Long id, Model model) {
        bookService.deleteBook(id);
        model.addAttribute(ModelConstants.INFO, "Book " + id + " deleted");
        return ViewNames.WELCOME;
    }

    @GetMapping(value = "/search")
    public ModelAndView showFindBookForm(Model model) {
        model.addAttribute("searchBook", new BookTo());

        return new ModelAndView(ViewNames.FINDBOOK, "searchBook", new BookTo());
    }

    @PostMapping(value = "/search")
    public ModelAndView searchBookByParams(@ModelAttribute("searchBook") @Validated BookTo book) {

        book.setAuthors(new HashSet<>());
        book.setCategories(new HashSet<>());

        List<BookTo> bookList = bookService.findByBooksByParam(book);

        ModelAndView modelAndView = new ModelAndView(ViewNames.BOOKS);
        modelAndView.addObject("bookList", bookList);

        return modelAndView;
    }
}
