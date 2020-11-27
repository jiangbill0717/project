package fudan.homework.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fudan.homework.pojo.Book;

@Repository
public class BookDao {

	private final static String GET_ALL_BOOKS = "SELECT id, bookName, press, author, number, status FROM book_management_sys.b_book"; 
	
	private final static String GET_BOOKS_WITH_NORMAL_STATUS = "SELECT id, bookName, press, author, number, status FROM book_management_sys.b_book where status = 1"; 
	
	private final static String GET_BOOK_BY_ID = "SELECT id, bookName, press, author, number, status FROM book_management_sys.b_book where id = :id"; 
	
	private final static String BORROW_BOOK_BY_ID = "update book_management_sys.b_book set number = :number where id = :id"; 
	
	private final static String DELETE_BOOK_BY_ID = "update book_management_sys.b_book set status = 3 where id = :id"; 
	
	private final static String UPDATE_BOOK_BY_ID = "update book_management_sys.b_book set bookName = :bookName, press = :press, author = :author, number = :number, status = :status, change_ts=current_timestamp where id = :id"; 
	
	private final static String INSERT_BOOK = "INSERT INTO `book_management_sys`.`b_book`\n" + 
			"(`bookName`,\n" + 
			"`press`,\n" + 
			"`author`,\n" + 
			"`number`,\n" + 
			"`status`,\n" + 
			"`insert_user`,\n" + 
			"`change_user`)\n" + 
			"VALUES\n" + 
			"(:bookName,\n" + 
			":press,\n" + 
			":author,\n" + 
			":number,\n" + 
			":status,\n" + 
			":insert_user,\n" + 
			":change_user)"; 
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public List<Book> getAllBooks(){
		List<Book> users = new ArrayList<>();
		jdbcTemplate.query(GET_ALL_BOOKS, (RowCallbackHandler)rs -> {
			users.add(new Book(rs.getInt("id"), rs.getString("bookName"), rs.getString("press"), rs.getString("author"), rs.getInt("number"), rs.getString("status")));
		});
		return users;
	}
	
	public List<Book> getBooksWithNormalStatus(){
		List<Book> users = new ArrayList<>();
		jdbcTemplate.query(GET_BOOKS_WITH_NORMAL_STATUS, (RowCallbackHandler)rs -> {
			users.add(new Book(rs.getInt("id"), rs.getString("bookName"), rs.getString("press"), rs.getString("author"), rs.getInt("number"), rs.getString("status")));
		});
		return users;
	}
	
	public Optional<Book> getBookById(int id) {
		List<Book> result = new ArrayList<>();
		jdbcTemplate.query(GET_BOOK_BY_ID, Collections.singletonMap("id", id), (RowCallbackHandler) rs -> {
			Book book = new Book();
			book.setId(rs.getInt("id"));
			book.setBookName(rs.getString("bookName"));
			book.setPress(rs.getString("press"));
			book.setAuthor(rs.getString("author"));
			book.setNumber(rs.getInt("number"));
			book.setStatus(rs.getString("status"));
			result.add(book);
		});
		return result.stream().findFirst();
	}
	
	public void borrowBook(Book book) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("number", book.getNumber() - 1);
		paramMap.put("id", book.getId());
		jdbcTemplate.update(BORROW_BOOK_BY_ID, paramMap);
	}
	
	public void insertBook(Book book) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("bookName", book.getBookName());
		paramMap.put("press", book.getPress());
		paramMap.put("author", book.getAuthor());
		paramMap.put("number", book.getNumber());
		paramMap.put("status", 1);
		paramMap.put("insert_user", "admin");
		paramMap.put("change_user", "admin");
		jdbcTemplate.update(INSERT_BOOK, paramMap);
	}
	
	public void deleteBook(int id) {
		jdbcTemplate.update(DELETE_BOOK_BY_ID, Collections.singletonMap("id", id));
	}
	
	public void editBook(Book book) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", book.getId());
		paramMap.put("bookName", book.getBookName());
		paramMap.put("press", book.getPress());
		paramMap.put("author", book.getAuthor());
		paramMap.put("number", book.getNumber());
		paramMap.put("status", book.getStatus());
		jdbcTemplate.update(UPDATE_BOOK_BY_ID, paramMap);
	}
}
