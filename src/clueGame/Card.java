package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public enum CardType{ROOM, WEAPON, PERSON, BAD}
	
	public Card(String cardName, CardType cardType) {
		this.cardName = cardName;
		this.cardType = cardType;
	}
	
	public Card(String cardName, String cardTypeStr) {
		this.cardName = cardName;
		if (cardTypeStr.equalsIgnoreCase("Room")) {
			this.cardType = CardType.ROOM;
		} else if (cardTypeStr.equalsIgnoreCase("Person")) {
			this.cardType = CardType.PERSON;
		} else if (cardTypeStr.equalsIgnoreCase("Weapon")) {
			this.cardType = CardType.WEAPON;
		} else {
			this.cardType = CardType.BAD;
		}
	}

	public Card() {
		cardName = "Card!";
		cardType = CardType.PERSON;
	}

	public String getCardName() {
		return cardName;
	}
	
	public CardType getCardType() {
		return cardType;
	}
	
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public void setCardType(CardType cardType){
		this.cardType = cardType;
	}

	public Boolean equals(Card cardIn) {
		if (cardName.equalsIgnoreCase(cardIn.getCardName()) && cardType == cardIn.getCardType()) {
			return true;
		}
		else return false;
	}
}