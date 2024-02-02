package springboothibernate.application.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboothibernate.application.model.Book;
import springboothibernate.application.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    private BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    public List<Book> saveMultipleBooks(List<Book> booksList) {
        return bookRepository.saveAllAndFlush(booksList);
    }

    public Optional<Book> updateBookById(Book bookToUpdate, Long id) {
        Optional<Book> searchBookOpt = bookRepository.findById(id);

        if (searchBookOpt.isPresent()) {
            Book saveBook = searchBookOpt.get();
            //fields to save
            saveBook.setTitle(bookToUpdate.getTitle());
            return Optional.of(bookRepository.save(saveBook));
        }
        return Optional.empty();
    }

    public void deleteBook(Long id) {
        Book book = entityManager.find(Book.class, id);

        if(book != null) {
            entityManager.remove(book);
        }
    }
}
