package telran.java51.book.service;

import java.util.Set;

import telran.java51.book.dto.AuthorDto;
import telran.java51.book.dto.BookDto;

public interface BookService {
	boolean addBook(BookDto bookDto);

	BookDto findBookByIsbn(String isbn);

	BookDto removeBookByIsbn(String isbn);

	BookDto updateBookTitle(String isbn, String newTitle);

	Set<BookDto> findBooksByAuthor(String author);

	Set<BookDto> findBooksByPublisher(String publisher);

	Set<AuthorDto> findBookAuthors(String isbn);
	
	//===== *** ======


	String[] findPublishersByAuthor(String author);
	
	AuthorDto removeAuthor(String author);

}
