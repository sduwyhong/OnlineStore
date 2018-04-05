package beans;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	//key:bookId value:cart (item-->book:one-->many)
	Map<String,CartItem> items = new HashMap<String, CartItem>();//购物项
	private int quantity;//购物车总数量
	private float price;//购物车总价格
	public Map<String, CartItem> getItems() {
		return items;
	}
	public void setItems(Map<String, CartItem> items) {
		this.items = items;
	}
	public int getQuantity() {
		quantity = 0;
		for(Map.Entry<String, CartItem> item:items.entrySet()){
			quantity += item.getValue().getQuantity();
		}
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		price = 0;
		for(Map.Entry<String, CartItem> item:items.entrySet()){
			price += item.getValue().getPrice();
		}
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Cart [items=" + items + ", quantity=" + quantity + ", price="
				+ price + "]";
	}
	
	
}
