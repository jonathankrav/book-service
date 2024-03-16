package telran.java51.book.dao;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java51.book.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public Set<Book> findBooksByAuthor(String authorName) {
		return em.createQuery("SELECT DISTINCT FROM Book b Join b.authors a WHERE a.name =: authorName", Book.class)
				.setParameter("authorName", authorName)
				.getResultStream()
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Book> findBooksByPublisherNameIgnoreCase(String publisherName) {
		return	em.createQuery("SELECT DISTINCT FROM Book b JOIN publisher p WHERE p.publisherName =: publisherName", Book.class)
			.setParameter("publisherName", publisherName)
			.getResultStream()
			.collect(Collectors.toSet());

	}

	@Override
	public void deleteByAuthorsName(String name) {
		em.createQuery("DELETE FROM Book b WHERE b.author.name = :name")
        .setParameter("name", name)
        .executeUpdate();

	}

	@Override
	public boolean existsById(String isbn) {
		return em.find(Book.class, isbn) != null;
	}

	@Override
	public Optional<Book> findById(String isbn) {
		return Optional.ofNullable(em.find(Book.class, isbn));
	}



	@Override
	public void delete(Book book) {
		em.remove(book);

	}

	@Override
	public Book save(Book book) {
		em.persist(book);
		return book;
	}

}
