package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import org.springframework.data.repository.query.Param;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<String> findAllByCopiesLessThan(int copiesCount);

    List<String> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal higher);

    List<String> findAllByReleaseDateBeforeOrReleaseDateAfter(int year);

    List<String> findAllByReleaseDateBefore(LocalDate releaseDate);

    List<String> findAllByTitleContains(String str);

    List<String> findAllByAuthorLastNameStartsWith(String str);

    int findCountOfBooksWhichTitleIsLongerThan(int length);

    String findBookByTitle(String title);

    int getTotalCopiesAdded(LocalDate afterDate, int copiesToAdd);

    int deleteByCopiesLessThan(int copies);

    int getBooksCountByAuthor(String firstName, String lastName);

}
