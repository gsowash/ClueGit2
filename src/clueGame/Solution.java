package clueGame;

import clueGame.Card.CardType;

public class Solution {
	
	private String person;
	private String room;
	private String weapon;
	private Card personCard;
	private Card weaponCard;
	private Card roomCard;
	
	public Solution() {
		person =" ";
		room =" ";
		weapon =" ";
		personCard = new Card(person, CardType.PERSON);
		weaponCard = new Card(weapon, CardType.WEAPON);
		roomCard = new Card(room, CardType.ROOM);
	}
	
	public Solution(String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
		personCard = new Card(person, CardType.PERSON);
		weaponCard = new Card(weapon, CardType.WEAPON);
		roomCard = new Card(room, CardType.ROOM);
	}
	
	public Solution(Card personCard, Card weaponCard, Card roomCard) {
		this.personCard = personCard;
		this.weaponCard = weaponCard;
		this.roomCard = roomCard;
		person = personCard.getCardName();
		room = roomCard.getCardName();
		weapon = weaponCard.getCardName();
	}
	
	public Boolean equals(Solution solutionIn) {
		if (this.personCard.getCardName() == solutionIn.getPerson() && this.roomCard.getCardName() == solutionIn.getRoom() && this.weaponCard.getCardName() == solutionIn.getWeapon()) {
			return true;
		}
		else return false;
	}
	
	public void setSolution(String p, String r, String w){
		this.person = p;
		this.room = r;
		this.weapon = w;
	}

	public Card getPersonCard() {
		return personCard;
	}

	public void setPersonCard(Card personCard) {
		this.personCard = personCard;
	}

	public Card getWeaponCard() {
		return weaponCard;
	}

	public void setWeaponCard(Card weaponCard) {
		this.weaponCard = weaponCard;
	}

	public Card getRoomCard() {
		return roomCard;
	}

	public void setRoomCard(Card roomCard) {
		this.roomCard = roomCard;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
	
}
