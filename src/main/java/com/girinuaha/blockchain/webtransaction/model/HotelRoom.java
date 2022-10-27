package com.girinuaha.blockchain.webtransaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "table_hotel_room")
public class HotelRoom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "room_type")
	private String roomType;
	
	@Column(name = "room_price_dollar")
	private String roomPriceDollar;
	
	@Column(name = "room_price_ether")
	private String roomPriceEther;
	
	@Column(name = "total_room")
	private String totalRoom;
	
	@Column(name = "total_booked_room")
	private String totalBookedRoom;
	
	@Column(name = "room_image")
	private String roomImage;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomPriceDollar() {
		return roomPriceDollar;
	}

	public void setRoomPriceDollar(String roomPriceDollar) {
		this.roomPriceDollar = roomPriceDollar;
	}

	public String getRoomPriceEther() {
		return roomPriceEther;
	}

	public void setRoomPriceEther(String roomPriceEther) {
		this.roomPriceEther = roomPriceEther;
	}

	public String getTotalRoom() {
		return totalRoom;
	}

	public void setTotalRoom(String totalRoom) {
		this.totalRoom = totalRoom;
	}

	public String getTotalBookedRoom() {
		return totalBookedRoom;
	}

	public void setTotalBookedRoom(String totalBookedRoom) {
		this.totalBookedRoom = totalBookedRoom;
	}

	public String getRoomImage() {
		return roomImage;
	}

	public void setRoomImage(String roomImage) {
		this.roomImage = roomImage;
	}

}
