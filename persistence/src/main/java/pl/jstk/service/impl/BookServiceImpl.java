package pl.jstk.service.impl;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import pl.jstk.Validation.BooksFormValidator;
import pl.jstk.entity.Book;
import pl.jstk.exception.BusinessException;
import pl.jstk.mapper.BookMapper;
import pl.jstk.repository.BookRepository;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    private BookMapper bookMapper;

    private BooksFormValidator booksFormValidator;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper, BooksFormValidator booksFormValidator) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.booksFormValidator=booksFormValidator;
    }

    @Override
    public List<BookTo> findAllBooks() {
        return bookMapper.map2To(bookRepository.findAll());
    }

    @Override
    public BookTo findById(Long id) {
        booksFormValidator.checkIfBookIdIsNull(id);
        booksFormValidator.checkIfCollectionIsEmpty();

        Optional<Book> book = bookRepository.findById(id);
        if(!book.isPresent()) {
            throw new BusinessException();
        }
        return bookMapper.map2To(book.get());
    }
    @Override
    public List<BookTo> findBooksByTitle(String title) {
        booksFormValidator.checkIfCollectionIsEmpty();

        return bookMapper.map2To(bookRepository.findBookByTitle(title));
    }

    @Override
    public List<BookTo> findByBooksByParam(BookTo bookTo) {
        booksFormValidator.checkIfBookIsNull(bookTo);
        booksFormValidator.checkIfCollectionIsEmpty();

        Book book = bookMapper.map(bookTo);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withMatcher("title", contains().ignoreCase())
                .withMatcher("description",contains().ignoreCase())
                .withMatcher("status",exact()); //exact

        Example<Book> example = Example.of(book, matcher);
        return bookMapper.map2To(bookRepository.findAll(example));
    }

    @Override
    @Transactional
    public BookTo saveBook(BookTo book) {
        booksFormValidator.checkIfBookIsNull(book);
        booksFormValidator.checkIfBookComponentIsNull(book);

        Book entity = bookMapper.map(book);
        entity = bookRepository.save(entity);
        return bookMapper.map2To(entity);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        booksFormValidator.checkIfBookIdIsNull(id);
        booksFormValidator.checkIfIdExists(id);

        bookRepository.deleteById(id);
    }
}

