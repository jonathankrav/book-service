package telran.java51.book.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import telran.java51.book.dto.BookDto;
import telran.java51.book.model.Author;
import telran.java51.book.model.Book;

public interface BookRepository extends JpaRepository<Book, String>{
	
	@Query("select b from Book b JOIN b.authors a WHERE a.name = :authorName")
	Set<Book> findBooksByAuthor(@Param("authorName") String authorName);

	@Query("select b from Book b where b.publisher.publisherName = :publisherName")
	Set<Book> findBooksByPublisherNameIgnoreCase(@Param("publisherName") String publisherName);
	
}
