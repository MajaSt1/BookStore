package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.jstk.Validation.BooksFormValidator;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
// javadoc
// book not found exception!!!!

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

    @GetMapping("/book")
    public String getBook(@ModelAttribute("id") Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));

        return ViewNames.BOOK;
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddBookForm() {

        return new ModelAndView(ViewNames.ADDBOOK, "newBook", new BookTo());
    }

    @PostMapping(value = "/add")
    public String addBook(@ModelAttribute("newBook") @Validated BookTo book) {
        BookTo book1 = bookService.saveBook(book);

        ModelAndView modelAndView = new ModelAndView(ViewNames.BOOK);

        modelAndView.addObject("newBook", book1);

        return "redirect:/showBooks";
    }

    @PostMapping(value = "/delete")
    public String deleteBook(@ModelAttribute("book") BookTo bookTo) {
        bookService.deleteBook(bookTo.getId());

        return "redirect:/showBooks";
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
