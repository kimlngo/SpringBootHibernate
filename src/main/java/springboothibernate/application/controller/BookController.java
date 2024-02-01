package springboothibernate.application.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboothibernate.application.model.Book;
import springboothibernate.application.service.BookService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class BookController {
    @PersistenceContext
    private EntityManager entityManager;

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "listAll")
    public List<Book> listAllBooks() {
        return this.bookService.list();
    }

    @GetMapping(value = "getAllBookMap")
    public Map<Long, Book> getAllBookMap() {
        return this.bookService.list().stream()
                .collect(Collectors.toMap(Book::getId, Function.identity()));
    }

    @GetMapping(value = "find")
    public List<Book> findByName(@RequestParam String title){
        return this.bookService.findByTitle(title);
    }

    @PostMapping(value = "saveOneBook")
    public Book saveOneBook(@RequestBody Book bookToSave) {
        return this.bookService.saveOneBook(bookToSave);
    }

    @PostMapping(value = "saveMultipleBooks")
    public List<Book> saveMultipleBooks(@RequestBody List<Book> booksList) {
        return this.bookService.saveAllBooks(booksList);
    }

    @Transactional
    @DeleteMapping(value = "deleteBook/{id}")
    public void deleteBook(@PathVariable Long id) {
        Book book = entityManager.find(Book.class, id);

        if(book != null) {
            entityManager.remove(book);
        }
    }
}
