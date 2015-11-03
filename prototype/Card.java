package prototype;

public class Card {
	private String type,
				   color,
				   number;
	public Card(String type, String color ,String number){
		this.type = type;
		this.color = color;
		this.number = number;
	}
	
	public Card(String type){
		this(type, null, null);
	}
	
	public Card(String type, String color){
		this(type, color, null);
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getColor(){
		return this.color;
	}
	
	public String getNumber(){
		return this.number;
	}
	
	@Override
	public String toString(){
		return "Type : " + this.type +
				" Color : " + this.color +
				" Number : " + this.number;
	}

}
