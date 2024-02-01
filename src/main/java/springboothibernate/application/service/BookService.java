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

    public List<Book> findByTitle(String name) {
        return bookRepository.findByTitle(name);
    }

    public Book saveOneBook(Book bookToSave) {
        return bookRepository.saveAndFlush(bookToSave);
    }

    public List<Book> saveAllBooks(List<Book> booksList) {
        return bookRepository.saveAllAndFlush(booksList);
    }
}
