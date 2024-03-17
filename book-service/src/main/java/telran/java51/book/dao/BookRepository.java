package telran.java51.book.dao;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import telran.java51.book.model.Book;

public interface BookRepository {
	
	@Query("select b from Book b JOIN b.authors a WHERE a.name = :authorName")
	Set<Book> findBooksByAuthor(@Param("authorName") String authorName);

	@Query("select b from Book b where b.publisher.publisherName = :publisherName")
	Set<Book> findBooksByPublisherNameIgnoreCase(@Param("publisherName") String publisherName);
	
//	void deleteByAuthorsName(String name);	
//	Stream<Book> findByAuthorsName(String name);
//
//	Stream<Book> findByPublisherPublisherName(String name);

	boolean existsById(String isbn);
	
	Optional  <Book>  findById(String isbn);

	Book save(Book book);

	void delete(Book book);	
}
