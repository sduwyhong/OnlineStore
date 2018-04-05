package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import util.DBCPUtil;
import dao.CategoryDao;
import domain.Category;

public class CategoryDaoImpl implements CategoryDao {
	
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	@Override
	public void addCategory(Category c) {
		try {
			qr.update("insert into categories (id,name,description) values(?,?,?)", c.getId(),c.getName(),c.getDescription());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Category findById(String categoryId) {
		Category category = null;
		try {
			category = qr.query("select * from categories where id=?", new BeanHandler<Category>(Category.class), categoryId);
			if(category==null){
				throw new RuntimeException("the category does not exist!");
			}else{
				return category;
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public List<Category> findAll() {
		List<Category> categories = new ArrayList<Category>();
		try {
			categories = qr.query("select * from categories", new BeanListHandler<Category>(Category.class));
			if(categories==null){
				throw new RuntimeException("the category does not exist!");
			}else{
				return categories;
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

}
