package pl.jstk.rest.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

    @ResponseBody
    @ExceptionHandler(BookAdditionException.class)
    @ResponseStatus( HttpStatus.BAD_REQUEST)
    public Error BookAdditionBusinessExceptionHandler(Exception ex) {
        LOGGER.error("Error while adding book",ex);
        return new Error(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BookDeletionException.class)
    @ResponseStatus( HttpStatus.BAD_REQUEST)
    public Error BookDeletionBusinessExceptionHandler2(Exception ex) {
        LOGGER.error("Error while deleting book",ex);
        return new Error(ex.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(InvalidIdException.class)
    @ResponseStatus( HttpStatus.BAD_REQUEST)
    public Error InvalidIdBusinessExceptionHandler1(Exception ex) {
        LOGGER.error("Invalid id",ex);
        return new Error(ex.getMessage());
    }


}
