package dao;

import java.util.List;

import commons.Page;

import domain.Book;

public interface BookDao {

	void addBook(Book b);
	
	void deleteBook(String bookId);
	
	Book findById(String bookId);

	List<Book> findAll();
	
	int getTotalRecordsNum();
	Page getPage(int currentPageNum, int totalRecordsNum, int recordsPerPage);

	int getTotalRecordsNum(String categoryId);

	Page getPage(String categoryId, int num, int totalRecordsNum,
			int recordsPerPage);

	void updateBook(Book newBook);

}
