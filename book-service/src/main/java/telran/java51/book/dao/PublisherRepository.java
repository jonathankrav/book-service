package telran.java51.book.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java51.book.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String>{
	
	List <String> findByPublishersAuthor(String authorName);

}
