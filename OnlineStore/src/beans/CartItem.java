package beans;


import domain.Book;

public class CartItem  {

	private Book book;//购物项所对应书籍（一对一）
	private int quantity;//购物项数量
	private float price;//购物项总价
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		price = book.getPrice()*quantity;
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "CartItem [book=" + book + ", quantity=" + quantity + ", price="
				+ price + "]";
	}

}
