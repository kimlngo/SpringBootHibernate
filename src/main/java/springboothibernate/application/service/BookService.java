package springboothibernate.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboothibernate.application.model.Book;
import springboothibernate.application.repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> list() {
        return bookRepository.findAll();
    }

    public List<Book> findByName(String name) {
        return bookRepository.findByName(name);
    }
}
