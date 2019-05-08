package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;

@Controller
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/showBooks")
    public String welcome(Model model) {
        model.addAttribute("bookList", bookService.findAllBooks());
        return ViewNames.BOOKS;
    }

}
