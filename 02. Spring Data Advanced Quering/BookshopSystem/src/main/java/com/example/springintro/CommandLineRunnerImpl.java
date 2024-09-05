package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final Scanner sc;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, Scanner sc) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.sc = sc;
    }

    @Override
    public void run(String... args) throws Exception {

        //   SPRING DATA INTRO:
        //   seedData();
        //   printAllBooksAfterYear(2000);
        //   printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
        //   printAllAuthorsAndNumberOfTheirBooks();
        //   printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");


        //   SPRING DATA ADVANCE QUERYING:
        System.out.println("Please choose a task (1-14):");
        int task = Integer.parseInt(sc.nextLine());

        switch (task) {
            case 1 -> {
                System.out.println("TASK 1");
                System.out.println("Enter age restriction (MINOR, TEEN, ADULT):");
                String inputAgeRestriction = sc.nextLine();

                AgeRestriction ageRestriction =
                        AgeRestriction.valueOf(inputAgeRestriction.toUpperCase());

                bookService.findAllByAgeRestriction(ageRestriction)
                        .forEach(System.out::println);
            }

            case 2 -> {
                System.out.println("TASK 2");
                System.out.println("Enter copies count:");
                int copiesCount = Integer.parseInt(sc.nextLine());

                bookService.findAllByCopiesLessThan(copiesCount)
                        .forEach(System.out::println);
            }

            case 3 -> {
                System.out.println("TASK 3");

                System.out.println("Enter a price as lower bound:");
                BigDecimal lowerBound = new BigDecimal(sc.nextLine());

                System.out.println("Enter a price as upper bound:");
                BigDecimal upperBound = new BigDecimal(sc.nextLine());

                bookService.findAllByPriceLessThanOrPriceGreaterThan(lowerBound, upperBound)
                        .forEach(System.out::println);
            }

            case 4 -> {
                System.out.println("TASK 4");
                System.out.println("Enter an year:");

                int year = Integer.parseInt(sc.nextLine());

                bookService.findAllByReleaseDateBeforeOrReleaseDateAfter(year)
                        .forEach(System.out::println);
            }

            case 5 -> {
                System.out.println("TASK 5");
                System.out.println("Enter date in format dd-MM-yyyy:");

                String inputReleaseDate = sc.nextLine();

                try {
                    LocalDate releaseDate = LocalDate.parse(inputReleaseDate,
                            DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                    bookService.findAllByReleaseDateBefore(releaseDate)
                            .forEach(System.out::println);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid date format.");
                }
            }

            case 6 -> {
                System.out.println("TASK 6");
                System.out.println("Enter some string which author's first name should ends with:");

                String str = sc.nextLine();

                authorService.findAllByFirstNameEndsWith(str)
                        .forEach(System.out::println);
            }

            case 7 -> {
                System.out.println("TASK 7");
                System.out.println("Enter some string contained by book's title:");

                String str = sc.nextLine();

                bookService.findAllByTitleContains(str)
                        .forEach(System.out::println);
            }

            case 8 -> {
                System.out.println("TASK 8");
                System.out.println("Enter some string which author's last name ends with:");

                String str = sc.nextLine();

                bookService.findAllByAuthorLastNameStartsWith(str)
                        .forEach(System.out::println);
            }

            case 9 -> {
                System.out.println("TASK 9");
                System.out.println("Enter book's title length:");

                int length = Integer.parseInt(sc.nextLine());

                int booksCount = bookService.findCountOfBooksWhichTitleIsLongerThan(length);
                System.out.println(booksCount);
            }

            case 10 -> {
                System.out.println("TASK 10");
                authorService.findAuthorsByTotalCopies()
                        .forEach(System.out::println);
            }

            case 11 -> {
                System.out.println("TASK 11");
                System.out.println("Enter book's title:");

                String title = sc.nextLine();

                System.out.println(bookService.findBookByTitle(title));
            }

            case 12 -> {
                System.out.println("TASK 12");
                System.out.println("Enter date in format dd MMM yyyy (ex: 12 Oct 2005):");
                String date = sc.nextLine();
                System.out.println("Enter copies count for increment:");
                int copiesToAdd = Integer.parseInt(sc.nextLine());

                try {
                    LocalDate afterDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMM yyyy"));
                    System.out.println(bookService.getTotalCopiesAdded(afterDate, copiesToAdd));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid date format");
                }
            }

            case 13 -> {
                System.out.println("TASK 13");
                System.out.println("Enter copies count:");

                int copies = Integer.parseInt(sc.nextLine());

                System.out.println(bookService.deleteByCopiesLessThan(copies) + " rows removed from DB.");
            }

            case 14 -> {
                System.out.println("Enter author full name:");

                String[] authorFullName = sc.nextLine().split(" ");
                String firstName = authorFullName[0];
                String lastName = authorFullName[1];

                int booksCount = bookService.getBooksCountByAuthor(firstName, lastName);

                System.out.printf("%s %s has written %d books", firstName, lastName, booksCount);
            }
        }
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
