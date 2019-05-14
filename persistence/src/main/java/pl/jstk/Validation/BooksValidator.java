package pl.jstk.Validation;

import pl.jstk.to.BookTo;

public interface BooksValidator {
     void validateBookAddition(BookTo bookTo);

    void validateBookDeletion(Long id);
}
