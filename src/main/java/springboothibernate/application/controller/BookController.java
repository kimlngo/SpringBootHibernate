package springboothibernate.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboothibernate.application.model.Book;
import springboothibernate.application.service.BookService;

import java.util.List;

@RestController
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "listAll")
    public List<Book> listAllBooks() {
        return this.bookService.list();
    }

    @GetMapping(value = "find")
    public List<Book> findByName(@RequestParam String name){
        return this.bookService.findByName(name);
    }
}
