package fudan.homework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fudan.homework.pojo.Book;
import fudan.homework.service.BookService;

@Controller
@PreAuthorize("hasRole('user')")
@RequestMapping("/user")
public class UserController {

	@Autowired
	private BookService bookService;
	
	@RequestMapping("/borrow")
	@ResponseBody
	public ResponseEntity<Book> borrowBook(@RequestParam("bookId") int id){
		return ResponseEntity.ok(bookService.borrowBook(id));
	}
	
	@RequestMapping("/books")
	@ResponseBody
	public ResponseEntity<List<Book>> getBookWithNormalStatus(){
		return ResponseEntity.ok(bookService.getBooksWithNormalStatus());
	}
}
