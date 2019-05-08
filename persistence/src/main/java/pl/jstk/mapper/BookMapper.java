package pl.jstk.mapper;

import java.util.List;

import pl.jstk.entity.Book;
import pl.jstk.to.BookTo;

public interface BookMapper {

    BookTo map2To(Book book);

    Book map(BookTo bookTo);

    List<BookTo> map2To(List<Book> bookEntities);

    List<Book> map2Entity(List<BookTo> bookEntities);
}
