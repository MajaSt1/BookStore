package pl.jstk.rest;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
public class BookRestController {

    private BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookTo>> findAllBooks() {
        List<BookTo> allBooks = bookService.findAllBooks();
        return ResponseEntity.ok().body(allBooks);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookTo> findBook(@PathVariable("id") Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().body(null);
        }
        BookTo book = bookService.findById(id);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping("/books")
    public ResponseEntity<BookTo> addBook(@RequestBody BookTo bookTo) {
        BookTo book = bookService.saveBook(bookTo);
        return ResponseEntity.ok().body(book);
    }

    @RequestMapping(value = "/books-by-title")
    public List<BookTo> finBooksByTitle(@RequestParam("titlePrefix") String titlePrefix) {
        return bookService.findBooksByTitle(titlePrefix);
    }

    @RequestMapping(value = "/books-by-title/{titlePrefix}")
    public List<BookTo> findBooksByTitle(@PathVariable("titlePrefix") String titlePrefix) {
        return bookService.findBooksByTitle(titlePrefix);
    }
}
