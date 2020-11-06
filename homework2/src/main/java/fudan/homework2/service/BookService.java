package fudan.homework2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fudan.homework2.dao.BookDao;
import fudan.homework2.pojo.Book;

@Service
public class BookService {

	@Autowired
	private BookDao bookDao;
	
	public List<Book> getAllBooks(){
		return bookDao.getAllBooks();
	}
	
	public List<Book> getBooksWithNormalStatus(){
		return bookDao.getBooksWithNormalStatus();
	}
	
	public void insertBook(Book book) {
		bookDao.insertBook(book);
	}
	
	public Book getBookById(int id) {
		return bookDao.getBookById(id).orElse(null);
	}
	
	@Transactional
	public Book borrowBook(int id) {
		Optional<Book> bookOpt = bookDao.getBookById(id);
		if (bookOpt.isPresent()) {
			Book book = bookOpt.get();
			if("1".equals(book.getStatus()) && book.getNumber() > 0) {
				bookDao.borrowBook(book);
				return book;
			}else{
				throw new RuntimeException("书籍状态不正常或书籍数量不足");
			}
		}else {
			throw new RuntimeException("未找到相关书籍");
		}
	}
	
	@Transactional
	public void editBook(Book book) {
		Optional<Book> bookOpt = bookDao.getBookById(book.getId());
		if (bookOpt.isPresent()) {
			bookDao.editBook(book);
		}else {
			throw new RuntimeException("未找到相关书籍");
		}
	}
	
	@Transactional
	public void deleteBook(int id) {
		Optional<Book> bookOpt = bookDao.getBookById(id);
		if (bookOpt.isPresent()) {
			bookDao.deleteBook(id);
		}else {
			throw new RuntimeException("未找到相关书籍");
		}
	}
}
