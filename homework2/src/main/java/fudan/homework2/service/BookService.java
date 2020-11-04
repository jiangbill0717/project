package fudan.homework2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fudan.homework2.dao.BookDao;
import fudan.homework2.pojo.Book;

@Service
public class BookService {

	@Autowired
	private BookDao bookDao;
	
	public List<Book> getAllBooks(){
		return bookDao.getAllBooks();
	}
	
	public void insertBook(Book book) {
		bookDao.insertBook(book);
	}
	
}
