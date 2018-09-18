package streams;

import java.util.*;
import java.util.stream.Collectors;

public class Task2 {
    static class Author{
        private String name;
        private short age;
        private List<Book> books;

        public Author(String name, short age, List<Book> books) {
            this.name = name;
            this.age = age;
            this.books = books;
        }

        public String getName() {
            return name;
        }

        public short getAge() {
            return age;
        }

        public void setBooks(List<Book> books){
            this.books = books;
        }

        public List<Book> getBooks() {
            return books;
        }

        @Override
        public String toString() {
            return "Author{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    static class Book{
        private String title;
        private List<Author> authors;
        private int numberOfPages;

        public Book(String title, List<Author> authors, int numberOfPages) {
            this.title = title;
            this.authors = authors;
            this.numberOfPages = numberOfPages;
        }

        public String getTitle() {
            return title;
        }

        public List<Author> getAuthors() {
            return authors;
        }

        public int getNumberOfPages() {
            return numberOfPages;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "title='" + title + '\'' +
                    ", authors=" + authors +
                    ", numberOfPages=" + numberOfPages +
                    '}';
        }
    }

    private static List<Book> createBooks() {
        List<Book> books = new ArrayList<>();

        for (int i=1; i<=100; i++){
            int authorNr = new Random().nextInt(5)+1;
            books.add(createBook(i, authorNr));
        }

        return books;
    }

    private static Book createBook(int i, int authorNr) {
        List<Author> authors = createAuthors(authorNr);
        return new Book("Book title " + i, authors, new Random().nextInt(250)+1);
    }

    private static List<Author> createAuthors(int authorNr) {
        List<Author> authors = new ArrayList<>();

        for (int i=1; i<=authorNr; i++){
            short age = ((Integer)(new Random().nextInt(70)+1)).shortValue();
            Author author = new Author("Author " + i, age, null);
            authors.add(author);
        }

        return authors;
    }

    public static void main(String[] args) {
        List<Book> books = createBooks();
        setBookOfAuthors(books);
        int nrOfBooksMoreThan200Pages = getNrOfBooksMoreThan200Pages(books);
        Book bookWithMaxPageNr = getBookWithMaxPageNr(books);
        Book bookWithMinPageNr = getBookWithMinPageNr(books);
        List<Book> booksWithSingleAuthors = getBooksWithSingleAuthors(books);
        List<Book> booksSortedByNumberOfPages = getBooksShortedByNumberOfPages(books);
        List<Book> booksSortedByTitle = getBooksShortedByTitle(books);
        List<String> bookTitles = getBookTitles(books);
        Set<Author> setOfAuthors = getSetOfAuthors(books);

        System.out.println("Number of books with more than 200 pages: " + nrOfBooksMoreThan200Pages);
        System.out.println("Book with maximum page number: " + bookWithMaxPageNr);
        System.out.println("Book with minimum page number: " + bookWithMinPageNr);
        System.out.println("Books with only one author: " + booksWithSingleAuthors);
        System.out.println("Books sorted by number of pages: " + booksSortedByNumberOfPages);
        System.out.println("Books sorted by title: " + booksSortedByTitle);
        System.out.println("Book titles: " + bookTitles);
        printBooksWithForeach(books);
        System.out.println("Authors: " + setOfAuthors);
    }

    private static Set<Author> setBookOfAuthors(List<Book> books) {
        Set<Author> authors = new HashSet<>();
        books.stream()
            .map(Book::getAuthors)
            .peek(System.out::println)
            .forEach(e -> e.forEach(authors::add));
        for (Author author : authors){
            List<Book> authorBooks = books.stream().filter(book -> book.getAuthors().contains(author)).collect(Collectors.toList());
            author.setBooks(authorBooks);
        }

        return authors;
    }


    private static Set<Author> getSetOfAuthors(List<Book> books) {
        Set<Author> authors = new HashSet<>();
        books.stream()
                .map(Book::getAuthors)
                .peek(System.out::println)
                .forEach(e -> e.forEach(authors::add));
        return authors;
    }

    private static void printBooksWithForeach(List<Book> books) {
        books.stream().forEach(System.out::println);
    }

    private static List<String> getBookTitles(List<Book> books) {
        return books.stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }


    private static List<Book> getBooksShortedByTitle(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    private static List<Book> getBooksShortedByNumberOfPages(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getNumberOfPages))
                .collect(Collectors.toList());
    }

    private static List<Book> getBooksWithSingleAuthors(List<Book> books) {
        return books.stream()
                .filter(book -> book.getAuthors().size() == 1)
                .collect(Collectors.toList());
    }

    private static Book getBookWithMinPageNr(List<Book> books) {
        return books.stream()
                .min(Comparator.comparing(Book::getNumberOfPages))
                .get();
    }

    private static Book getBookWithMaxPageNr(List<Book> books) {
        return books.stream()
                .max(Comparator.comparing(Book::getNumberOfPages))
                .get();
    }

    private static int getNrOfBooksMoreThan200Pages(List<Book> books) {
        return (int) books.parallelStream()
                .filter(book -> book.getNumberOfPages() > 200)
                .count();
    }
}
