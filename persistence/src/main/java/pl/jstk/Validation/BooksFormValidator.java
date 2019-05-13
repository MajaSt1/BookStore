package pl.jstk.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.jstk.exception.BusinessException;
import pl.jstk.exception.NoSuchBookException;
import pl.jstk.repository.BookRepository;
import pl.jstk.to.BookTo;

@Component
public class BooksFormValidator {

    private BookRepository bookRepository;

    @Autowired
    public BooksFormValidator(BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }

    public void checkIfBookIdIsNull(Long id) {
        if (id == null) {
            throw new NoSuchBookException();
        }
    }

    public void checkIfBookIsNull(BookTo bookTo) {
        if (bookTo == null) {
            throw new NoSuchBookException();
        }
    }

    public void checkIfBookComponentIsNull(BookTo bookTo) {
        if (bookTo.getTitle() == null || bookTo.getAuthors() == null
                || bookTo.getStatus() == null || bookTo.getCategories() == null
                || bookTo.getDescription() == null) {
            throw new NoSuchBookException();
        }
    }

    public void checkIfIdExists(Long id){
        if(!bookRepository.existsById(id)){
            throw new NoSuchBookException();
        }
    }

    public void checkIfCollectionIsEmpty(){
        if(bookRepository.findAll().isEmpty()){
            throw new NoSuchBookException();
        }
    }
}
