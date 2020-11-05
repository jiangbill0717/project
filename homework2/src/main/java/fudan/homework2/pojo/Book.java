package fudan.homework2.pojo;

public class Book {

	private int id;
	private String bookName;
	private String press;
	private String author;
	private int number;
	private String status;
	
	
	public Book() {
		super();
	}

	public Book(int id, String bookName, String press, String author, int number, String status) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.press = press;
		this.author = author;
		this.number = number;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
