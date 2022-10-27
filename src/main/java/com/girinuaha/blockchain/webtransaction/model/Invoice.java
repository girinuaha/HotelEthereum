package com.girinuaha.blockchain.webtransaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "table_invoice")
public class Invoice {

	@Id
	@Column(name = "invoice_id")
	private String invoiceId;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_email")
	private String customerEmail;
	
	@Column(name = "customer_phone_number")
	private String customerPhoneNumber;
	
	@Column(name = "check_in_date")
	private String checkinDate;
	
	@Column(name = "check_out_date")
	private String checkoutDate;
	
	@Column(name = "number_of_adult")
	private String numberOfAdult;
	
	@Column(name = "number_of_child")
	private String numberOfChild;
	
	@Column(name = "number_of_room")
	private String numberOfRoom;
	
	@Column(name = "room_price_dollar")
	private String roomPriceDollar;
	
	@Column(name = "room_price_ether")
	private String roomPriceEther;
	
	@Column(name = "deposit_dollar")
	private String depositDollar;
	
	@Column(name = "deposit_ether")
	private String depositEther;
	
	@Column(name = "payment_method")
	private String paymentMethod;
	
	@Column(name = "status")
	private String status;
	
	@OneToOne
	private HotelRoom hotelRoom;

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public String getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public String getNumberOfAdult() {
		return numberOfAdult;
	}

	public void setNumberOfAdult(String numberOfAdult) {
		this.numberOfAdult = numberOfAdult;
	}

	public String getNumberOfChild() {
		return numberOfChild;
	}

	public void setNumberOfChild(String numberOfChild) {
		this.numberOfChild = numberOfChild;
	}

	public String getNumberOfRoom() {
		return numberOfRoom;
	}

	public void setNumberOfRoom(String numberOfRoom) {
		this.numberOfRoom = numberOfRoom;
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

	public String getDepositDollar() {
		return depositDollar;
	}

	public void setDepositDollar(String depositDollar) {
		this.depositDollar = depositDollar;
	}

	public String getDepositEther() {
		return depositEther;
	}

	public void setDepositEther(String depositEther) {
		this.depositEther = depositEther;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HotelRoom getHotelRoom() {
		return hotelRoom;
	}

	public void setHotelRoom(HotelRoom hotelRoom) {
		this.hotelRoom = hotelRoom;
	}
	
}
