package pl.jstk.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.jstk.enumerations.BookCategory;
import pl.jstk.exception.BookAdditionException;
import pl.jstk.exception.BookDeletionException;
import pl.jstk.exception.BusinessException;
import pl.jstk.exception.InvalidIdException;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.Set;

@Component
public class BooksFormValidator implements BooksValidator{

    private final BookService bookService;

    @Autowired
    public BooksFormValidator(BookService bookService) {
       this.bookService = bookService;
   }

    public void validateBookAddition(BookTo bookTo) {

        validateTitle(bookTo.getTitle());
        validateAuthors(bookTo.getAuthors());

        validateDescription(bookTo.getDescription());

        validateCategories(bookTo.getCategories());
    }

    public void validateBookDeletion(Long id) {
        try {
            bookService.findById(id);
        } catch (BusinessException ex) {
            throw new BookDeletionException("There is no such book to delete!");
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new BookAdditionException("There is an error in the title!");
        }
    }

    private void validateAuthors(Set<String> authors) {
        if (authors == null || authors.isEmpty()) {
            throw new BookAdditionException("There is an error in the authors!");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new BookAdditionException("There is an error related in the description!");
        }
    }

    private void validateCategories(Set<BookCategory> categories) {
        if (categories == null || categories.isEmpty()) {
            throw new BookAdditionException("There is an error related in the categories!");
        }
    }
}
