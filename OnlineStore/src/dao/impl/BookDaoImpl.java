package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import util.DBCPUtil;

import commons.Page;

import dao.BookDao;
import dao.CategoryDao;
import domain.Book;
import domain.Category;

public class BookDaoImpl implements BookDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	private CategoryDao categoryDao = new CategoryDaoImpl();
	@Override
	public void addBook(Book b) {
		try {
			qr.update("insert into books (id,name,author,price,path,fileName,description,categoryId) values(?,?,?,?,?,?,?,?)",
					b.getId(),b.getName(),b.getAuthor(),b.getPrice(),b.getPath(),b.getFileName(),b.getDescription(),b.getCategory().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Book findById(String bookId) {
		try {
			Book b = qr.query("select * from books where id=?", new BeanHandler<Book>(Book.class), bookId);
			Category c = qr.query("select * from categories where id=(select categoryId from books where id=?)", new BeanHandler<Category>(Category.class), bookId);
			b.setCategory(c);
			if(b==null){
				throw new RuntimeException("the book does not exist");
			}else{
				return b;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Book> findAll() {
		try {
			List<Book> books = new ArrayList<Book>();
			books = qr.query("select * from books", new BeanListHandler<Book>(Book.class));
			for(Book book:books){
				Category c = qr.query("select * from categories where id=(select categoryId from books where id=?)", new BeanHandler<Category>(Category.class), book.getId());
				book.setCategory(c);
			}
			if(books==null){
				throw new RuntimeException("the book does not exist");
			}else{
				return books;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Page getPage(int currentPageNum, int totalRecordsNum,
			int recordsPerPage) {
		Page page = new Page(currentPageNum, totalRecordsNum, recordsPerPage);
		int startIndex = page.getStartIndex();
		List<Book> books = new ArrayList<Book>();
		try {
			//limit a,b 从a开始取b条
			books = qr.query("select * from books limit ?,?", new BeanListHandler<Book>(Book.class), startIndex,recordsPerPage);
			for(Book book:books){
				Category c = qr.query("select * from categories where id=(select categoryId from books where id=?)", new BeanHandler<Category>(Category.class),book.getId());
				book.setCategory(c);
			}
			page.setRecords(books);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public int getTotalRecordsNum() {
		try {
			//new ScalarHandler(1)
			Object obj = qr.query("select count(*) from books", new ScalarHandler(1));
			Long num = (Long)obj;
//			System.out.println("num.intValue()="+num.intValue());
			return num.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getTotalRecordsNum(String categoryId) {
		try {
			//new ScalarHandler(1)
			Object obj = qr.query("select count(*) from books where categoryId=?", new ScalarHandler(1),categoryId);
			Long num = (Long)obj;
//			System.out.println("num.intValue()="+num.intValue());
			return num.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Page getPage(String categoryId, int currentPageNum, int totalRecordsNum,
			int recordsPerPage) {
		Page page = new Page(currentPageNum, totalRecordsNum, recordsPerPage);
		int startIndex = page.getStartIndex();
		List<Book> books = new ArrayList<Book>();
		try {
			//limit a,b 从a开始取b条
			books = qr.query("select * from books where categoryId=? limit ?,?", new BeanListHandler<Book>(Book.class), categoryId,startIndex,recordsPerPage);
			for(Book book:books){
				Category c = qr.query("select * from categories where id=(select categoryId from books where id=?)", new BeanHandler<Category>(Category.class),book.getId());
				book.setCategory(c);
			}
			page.setRecords(books);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public void deleteBook(String bookId) {
		try {
			qr.update("delete from books where id=?", bookId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//(id,name,author,price,path,fileName,description,categoryId)
	@Override
	public void updateBook(Book newBook) {
		try {
			qr.update("update books set name=?,author=?,price=?,path=?,fileName=?,description=?,categoryId=? where id=?", 
					newBook.getName(),newBook.getAuthor(),newBook.getPrice(),newBook.getPath(),newBook.getFileName(),newBook.getDescription(),newBook.getCategory().getId(),newBook.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
