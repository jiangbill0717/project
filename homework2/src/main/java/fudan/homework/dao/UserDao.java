package fudan.homework.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fudan.homework.pojo.User;

@Repository
public class UserDao {

	private final static String GET_ALL_USER = "SELECT * FROM book_management_sys.b_user;"; 
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		jdbcTemplate.query(GET_ALL_USER, (RowCallbackHandler)rs -> {
			users.add(new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("user_priority")));
		});
		return users;
	}
}
