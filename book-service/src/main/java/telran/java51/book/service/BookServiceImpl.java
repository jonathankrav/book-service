package telran.java51.book.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.book.dao.AuthorRepository;
import telran.java51.book.dao.BookRepository;
import telran.java51.book.dao.PublisherRepository;
import telran.java51.book.dto.AuthorDto;
import telran.java51.book.dto.BookDto;
import telran.java51.book.dto.exceptions.EntityNotFoundException;
import telran.java51.book.model.Author;
import telran.java51.book.model.Book;
import telran.java51.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	final BookRepository bookRepository;
	final PublisherRepository publisherRepository;
	final AuthorRepository authorRepository;
	final ModelMapper modelMapper;

	@Transactional
	@Override
	public boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		// Publisher
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
		// Authors
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;

	}

	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public BookDto removeBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		bookRepository.delete(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public BookDto updateBookTitle(String isbn, String newTitle) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		if (newTitle != null) {
			book.setTitle(newTitle);
		}
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public Set<BookDto> findBooksByAuthor(String author) {
		return bookRepository.findBooksByAuthor(author).stream()
				.map(b -> modelMapper.map(b, BookDto.class))
				.collect(Collectors.toSet());
	}

	@Transactional
	@Override
	public Set<BookDto> findBooksByPublisher(String publisher) {
		return bookRepository.findBooksByPublisherNameIgnoreCase(publisher).stream()
				.map(b -> modelMapper.map(b, BookDto.class))
				.collect(Collectors.toSet());
	}

	@Transactional
	@Override
	public Set<AuthorDto> findBookAuthors(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return book.getAuthors().stream()
				.map(a -> modelMapper.map(a, AuthorDto.class))
				.collect(Collectors.toSet());
	}
	
	@Transactional
	@Override
	public String[] findPublishersByAuthor(String author) {
		return	bookRepository.findBooksByAuthor(author).stream()
		.map(b -> b.getPublisher().getPublisherName())
		.distinct() 
		.toArray(String[]::new);
	}

	
	@Override
	public AuthorDto removeAuthor(String author) {
		Author authorEntity =  authorRepository.findById(author).orElseThrow(EntityNotFoundException::new);
		List<Book> list = bookRepository.findBooksByAuthor(author).stream()
				.collect(Collectors.toList());
		for (Book book : list) {
			bookRepository.delete(book);
		}
		authorRepository.delete(authorEntity);
		return modelMapper.map(authorEntity, AuthorDto.class);
	}

}
