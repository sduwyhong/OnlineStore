package domain;

import java.io.Serializable;
/*
 * BOOKS
ID(PK)
NAME
AUTHOR
PRICE
PATH
FILENAME
DESCRIPTION
CATEGORYID(FK)

 */
public class Book implements Serializable {

	private String id;
	private String name;
	private String author;
	private float price;
	private String path;
	private String fileName;
	private String description;
	private Category category;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author
				+ ", price=" + price + ", path=" + path + ", fileName="
				+ fileName + ", description=" + description + ", category="
				+ category + "]";
	}
	
	
}
