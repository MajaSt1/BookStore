package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.jstk.Validation.BooksFormValidator;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@Controller
public class BookController {

    @Autowired
    BooksFormValidator booksFormValidator;

    private BookService bookService;
    // logger.debug?
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/showBooks")
    public String showAllBooks(Model model) {
        model.addAttribute("bookList", bookService.findAllBooks());

        return ViewNames.BOOKS;
    }

    @GetMapping(value = "/books/book")
    public String showBook(@PathVariable("id") Long id, Model model) {
        //validator
        BookTo book = bookService.findById(id);
        model.addAttribute("book", book);

        return ViewNames.BOOK;
    }

    @GetMapping(value = "/books/add")
    public ModelAndView showAddBookForm() {

        return new ModelAndView(ViewNames.ADDBOOK, "newBook", new BookTo());
    }

    @PostMapping(value = "/books/add")
    public String addBook(@ModelAttribute("newBook") @Validated BookTo book, BindingResult result, Model model) {
        //validation
        BookTo bookTo=bookService.saveBook(book);
       // model.addAttribute("newBook", new BookTo()); //?

        return "redirect:/showBooks/book?id=" + bookTo.getId();
    }


}
