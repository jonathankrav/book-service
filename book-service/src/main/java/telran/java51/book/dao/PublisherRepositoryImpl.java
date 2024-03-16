package telran.java51.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java51.book.model.Publisher;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

	@PersistenceContext
	EntityManager em;

	
	@Override
	public Stream<Publisher> findDistinctByBooksAuthorsName(String authorName) {
		return  em.createQuery("SELECT DISTINCT b.publisher FROM Book b JOIN b.authors a WHERE a.name = :authorName", Publisher.class)
				.setParameter("authorName", authorName)
				.getResultStream();
			

	}

	@Override
	public Optional<Publisher> findById(String publisher) {
		return Optional.ofNullable(em.find(Publisher.class, publisher));
	}

	@Override
//	@Transactional
	public Publisher save(Publisher publisher) {
		em.persist(publisher);
//		em.merge(publisher);
		return publisher;
	}

}
