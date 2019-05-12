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

import java.util.List;

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

    @GetMapping("books/book")
    public String getBook(@RequestParam("id") Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return ViewNames.BOOK;
    }

    @GetMapping(value = "/books/add")
    public ModelAndView showAddBookForm() {

        return new ModelAndView(ViewNames.ADDBOOK, "newBook", new BookTo());
    }

    @PostMapping(value = "/books/add")
    public String addBook(@ModelAttribute("newBook") @Validated BookTo book, BindingResult result) {
        BookTo book1= bookService.saveBook(book);

        ModelAndView modelAndView= new ModelAndView(ViewNames.BOOK);

        modelAndView.addObject("newBook", book1);

        return "redirect:/showBooks";
    }

    @PostMapping(value = "/books/delete")
    public String deleteBook(@ModelAttribute("book") BookTo bookTo){
        bookService.deleteBook(bookTo.getId());

        return "redirect:/showBooks";
    }

    @GetMapping(value = "books/search")
    public ModelAndView showFindBookForm(){

        return new ModelAndView(ViewNames.FINDBOOK,"newBook",new BookTo());
    }

    @PostMapping(value = "/books/search")
    public ModelAndView searchBookByParams(@ModelAttribute("newBook") BookTo book) {
       List<BookTo> bookList= bookService.findByBooksByParam(book);

       ModelAndView modelAndView= new ModelAndView();

       modelAndView.addObject("bookList",bookList);
       modelAndView.setViewName(ViewNames.BOOKS);

        return modelAndView;
    }
}
