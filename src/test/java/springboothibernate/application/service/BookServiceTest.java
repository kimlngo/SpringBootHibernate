package springboothibernate.application.service;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboothibernate.application.model.Book;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    @Order(1)
    public void testWhenApplicationStart() {
        List<Book> bookList = bookService.list();
        Assertions.assertEquals(3, bookList.size());
    }

    @Test
    @Order(2)
    public void testSaveOneNewBook() {
        Book bookToSave = new Book("Design Pattern in Guru");
        Book returnBook = bookService.saveOneBook(bookToSave);

        Assertions.assertEquals(bookToSave.getTitle(), returnBook.getTitle());
        Assertions.assertNotNull(returnBook.getId());
    }

    @Test
    @Order(2)
    public void testSaveMultipleBooks() {
        List<Book> booksList = List.of(
                new Book("Microservice Patterns"),
                new Book("Java Generics"));

        List<Book> returnBookList = bookService.saveMultipleBooks(booksList);
        Assertions.assertEquals(returnBookList.size(), 2);
        Assertions.assertTrue(returnBookList.stream()
                                            .map(Book::getTitle)
                                            .anyMatch(title -> "Microservice Patterns".equals(title)));
    }

    @Test
    @Order(2)
    public void testUpdateBook() {
        final String typoTitle = "How To Test Your Coed Effectivelyy";
        final String correctTitle = "How to Test Your Code Effectively";
        Book savedBook = bookService.saveOneBook(new Book(typoTitle));

        //correct the savedBook name
        savedBook.setTitle(correctTitle);

        Optional<Book> bookOptional = bookService.updateBookById(savedBook, savedBook.getId());
        Assertions.assertTrue(bookOptional.isPresent());
        Assertions.assertEquals(correctTitle, bookOptional.get().getTitle());
        Assertions.assertEquals(savedBook.getId(), bookOptional.get().getId());
    }

    @Test
    @Order(2)
    public void testDeleteBook() {
        List<Book> beforeDeletionList = bookService.list();
        bookService.deleteBook(beforeDeletionList.get(0).getId());
        List<Book> afterDeletionList = bookService.list();

        Assertions.assertEquals(1, beforeDeletionList.size() - afterDeletionList.size());
    }
}
