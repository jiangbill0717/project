package fudan.homework2.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fudan.homework2.pojo.Book;

@Repository
public class BookDao {

	private final static String GET_ALL_BOOKS = "SELECT id, bookName, press, author FROM book_management_sys.b_book"; 
	
	private final static String INSERT_BOOK = "INSERT INTO `book_management_sys`.`b_book`\n" + 
			"(`bookName`,\n" + 
			"`press`,\n" + 
			"`author`,\n" + 
			"`insert_ts`,\n" + 
			"`insert_user`,\n" + 
			"`change_ts`,\n" + 
			"`change_user`)\n" + 
			"VALUES\n" + 
			"(:bookName,\n" + 
			":press,\n" + 
			":author,\n" + 
			":number,\n" + 
			":status,\n" + 
			":insert_user,\n" + 
			":change_user)"; 
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Book> getAllBooks(){
		List<Book> users = new ArrayList<>();
		jdbcTemplate.query(GET_ALL_BOOKS, (RowCallbackHandler)rs -> {
			users.add(new Book(rs.getInt("id"), rs.getString("bookName"), rs.getString("press"), rs.getString("author"), rs.getInt("number"), rs.getString("status")));
		});
		return users;
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
}
