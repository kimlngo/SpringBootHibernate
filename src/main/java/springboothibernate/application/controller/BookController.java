package springboothibernate.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboothibernate.application.model.Book;
import springboothibernate.application.service.BookService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "listAll")
    public ResponseEntity<List<Book>> listAllBooks() {
        return new ResponseEntity<>(this.bookService.list(), HttpStatus.OK);
    }

    @GetMapping(value = "getAllBookMap")
    public ResponseEntity<Map<Long, Book>> getAllBookMap() {
        return new ResponseEntity<>(this.bookService.list()
                                                    .stream()
                                                    .collect(Collectors.toMap(Book::getId, Function.identity())), HttpStatus.OK);
    }

    @GetMapping(value = "find")
    public ResponseEntity<List<Book>> findByName(@RequestParam String title){
        return new ResponseEntity<>(this.bookService.findByTitle(title), HttpStatus.OK);
    }

    @PostMapping(value = "saveOneBook")
    public ResponseEntity<Book> saveOneBook(@RequestBody Book bookToSave) {
        return new ResponseEntity<>(this.bookService.saveOneBook(bookToSave), HttpStatus.CREATED);
    }

    @PostMapping(value = "saveMultipleBooks")
    public ResponseEntity<List<Book>> saveMultipleBooks(@RequestBody List<Book> booksList) {
        return new ResponseEntity<>(this.bookService.saveMultipleBooks(booksList), HttpStatus.CREATED);
    }

    @PutMapping(value = "updateBook/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book bookToUpdate, @PathVariable Long id) {
        Optional<Book> bookOpt = this.bookService.updateBookById(bookToUpdate, id);
        if (bookOpt.isPresent()) {
            return new ResponseEntity<>(bookOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "deleteBook/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
