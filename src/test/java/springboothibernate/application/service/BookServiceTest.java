package springboothibernate.application.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboothibernate.application.model.Book;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void testWhenApplicationStart() {
        List<Book> bookList = bookService.list();
        Assertions.assertEquals(bookList.size(), 3);
    }

    @Test
    public void testSaveOneNewBook() {
        Book bookToSave = new Book("Design Pattern in Guru");
        Book returnBook = bookService.saveOneBook(bookToSave);

        Assertions.assertEquals(bookToSave.getTitle(), returnBook.getTitle());
        Assertions.assertNotNull(returnBook.getId());
    }

    @Test
    public void testSaveMultipleBooks() {
        List<Book> booksList = List.of(
                new Book("Microservice Patterns"),
                new Book("Java Generics"));

        List<Book> returnBookList = bookService.saveAllBooks(booksList);
        Assertions.assertEquals(returnBookList.size(), 2);
        Assertions.assertTrue(returnBookList.stream()
                                            .map(Book::getTitle)
                                            .anyMatch(title -> "Microservice Patterns".equals(title)));
    }
}
