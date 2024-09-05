package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByAgeRestriction(AgeRestriction ageRestriction) {
        return bookRepository.findAllByAgeRestriction(ageRestriction)
                .stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByCopiesLessThan(int copiesCount) {
        return bookRepository.findAllByCopiesLessThan(copiesCount)
                .stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal higher) {
        return bookRepository.findAllByPriceLessThanOrPriceGreaterThan(lower, higher)
                .stream()
                .map(b -> String.format("%s - $%.2f", b.getTitle(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByReleaseDateBeforeOrReleaseDateAfter(int year) {
        LocalDate lower = LocalDate.of(year, 1, 1);
        LocalDate upper = LocalDate.of(year, 12, 31);

       return bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(lower, upper)
                .stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByReleaseDateBefore(LocalDate releaseDate) {
        return bookRepository.findAllByReleaseDateBefore(releaseDate)
                .stream()
                .map(b -> String .format("%s %s %.2f", b.getTitle(), b.getEditionType().name(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByTitleContains(String str) {
        return bookRepository.findAllByTitleContains(str)
                .stream().map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByAuthorLastNameStartsWith(String str) {
        return bookRepository.findAllByAuthor_LastNameStartsWith(str)
                .stream().map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public int findCountOfBooksWhichTitleIsLongerThan(int length) {
        return bookRepository.findCountOfBooksWhichTitleIsLongerThan(length);
    }

    @Override
    public String findBookByTitle(String title) {
        Book book = bookRepository.findBookByTitle(title);

        return String.format("%s %s %s %.2f", book.getTitle(), book.getEditionType().name(),
                book.getAgeRestriction().name(), book.getPrice());
    }

    @Transactional
    @Override
    public int getTotalCopiesAdded(LocalDate afterDate, int copiesToAdd) {
        List<Book> books = bookRepository.findAllByReleaseDateAfter(afterDate);

        int totalCopiesAdded = books.size() * copiesToAdd;

        int updatedRows = bookRepository.increaseBookCopiesAfterDate(copiesToAdd, afterDate);
        System.out.println(updatedRows + " rows affected.");

        return totalCopiesAdded;
    }

    @Transactional
    @Override
    public int deleteByCopiesLessThan(int copies) {
         return bookRepository.deleteByCopiesLessThan(copies);
    }

    @Override
    public int getBooksCountByAuthor(String firstName, String lastName) {
        return bookRepository.getBooksCountByAuthor(firstName, lastName);
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
