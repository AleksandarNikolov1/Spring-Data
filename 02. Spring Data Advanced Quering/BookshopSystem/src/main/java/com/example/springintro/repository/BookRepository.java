package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByCopiesLessThan(int copiesCount);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal higher);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate lower, LocalDate upper);

    List<Book> findAllByTitleContains(String str);

    List<Book> findAllByAuthor_LastNameStartsWith(String str);

    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :length ")
    int findCountOfBooksWhichTitleIsLongerThan(@Param("length") int length);

    Book findBookByTitle(String title);

    @Modifying
    @Query("UPDATE Book b SET b.copies = b.copies + :copies WHERE b.releaseDate > :releaseDate")
    int increaseBookCopiesAfterDate(@Param("copies") int copies, @Param("releaseDate") LocalDate releaseDate);
    @Modifying
    int deleteByCopiesLessThan(int copies);

    @Procedure(procedureName = "GetBooksByAuthor")
    int getBooksCountByAuthor(@Param("firstName") String firstName, @Param("lastName") String lastName);



}