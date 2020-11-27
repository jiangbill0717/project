package fudan.homework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fudan.homework.pojo.Book;
import fudan.homework.service.BookService;

@Controller
@PreAuthorize("hasRole('admin')")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private BookService bookService;
	
	@ResponseBody
	@RequestMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks(){
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	@ResponseBody
	@RequestMapping(value="/book", method=RequestMethod.POST)
	public ResponseEntity<String> addBook(@RequestBody Book book){
		bookService.insertBook(book);;
		return ResponseEntity.ok("ok");
	}
	
	@ResponseBody
	@RequestMapping(value="/book", method=RequestMethod.GET)
	public ResponseEntity<Book> addBook(@RequestParam("bookId")int id){
		return ResponseEntity.ok(bookService.getBookById(id));
	}
	
	@ResponseBody
	@RequestMapping(value="/book", method=RequestMethod.PUT)
	public ResponseEntity<String> editBook(@RequestBody Book book){
		bookService.editBook(book);
		return ResponseEntity.ok("ok");
	}
	
	@ResponseBody
	@RequestMapping(value="/book", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteBook(@RequestParam("bookId")int id){
		bookService.deleteBook(id);
		return ResponseEntity.ok("ok");
	}
}
