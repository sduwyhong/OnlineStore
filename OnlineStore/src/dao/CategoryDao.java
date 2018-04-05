package dao;

import java.util.List;

import domain.Category;

public interface CategoryDao {

	void addCategory(Category c);

	Category findById(String categoryId);

	List<Category> findAll();

}
