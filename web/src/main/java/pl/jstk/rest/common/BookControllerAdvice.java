package pl.jstk.rest.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.jstk.exception.BookAdditionException;
import pl.jstk.exception.BookDeletionException;
import pl.jstk.exception.InvalidIdException;

@ControllerAdvice
public class BookControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookControllerAdvice.class);

    @ExceptionHandler(BookAdditionException.class)
    public ResponseEntity<Object> BookAdditionBusinessExceptionHandler() {
        LOGGER.error("Error while adding book");
        return new ResponseEntity<>("Error while adding book", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookDeletionException.class)
    public ResponseEntity<Object> BookDeletionBusinessExceptionHandler2() {
        LOGGER.error("Error while deleting book");
        return new ResponseEntity<>("Error while deleting book",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<Object> InvalidIdBusinessExceptionHandler1() {
        return new ResponseEntity<>("Invalid id",HttpStatus.BAD_REQUEST) ;
    }
}
