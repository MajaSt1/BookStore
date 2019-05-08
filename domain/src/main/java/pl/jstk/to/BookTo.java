package pl.jstk.to;

import java.util.Set;

import pl.jstk.enumerations.BookCategory;
import pl.jstk.enumerations.BookStatus;

public class BookTo {

    private Long id;
    private String title;
    private Set<String> authors;
    private String description;
    private BookStatus status;
    private Set<BookCategory> categories;

    public BookTo() {
    }

    public BookTo(
            Long id,
            String title,
            Set<String> authors,
            String description,
            BookStatus status,
            Set<BookCategory> categories) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.setStatus(status);
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<String> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Set<BookCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<BookCategory> categories) {
        this.categories = categories;
    }
}
