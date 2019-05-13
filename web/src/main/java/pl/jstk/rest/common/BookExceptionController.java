package pl.jstk.rest.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.jstk.exception.NoSuchBookException;

@ControllerAdvice
public class BookExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookExceptionController.class);

    @ResponseBody
    @ExceptionHandler(NoSuchBookException.class)
    @ResponseStatus( HttpStatus.BAD_REQUEST)
    public Error businessExceptionHandler(Exception ex) {
        LOGGER.error("Book not found",ex);
        return new Error(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus( HttpStatus.BAD_REQUEST)
    public Error businessExceptionHandler1(Exception ex) {
        LOGGER.error("",ex);
        return new Error(ex.getMessage());
    }


}
