package com.girinuaha.blockchain.webtransaction.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.girinuaha.blockchain.webtransaction.model.AuditLog;
import com.girinuaha.blockchain.webtransaction.model.AuditLogRepository;
import com.girinuaha.blockchain.webtransaction.model.HotelRoom;
import com.girinuaha.blockchain.webtransaction.model.HotelRoomRepository;
import com.girinuaha.blockchain.webtransaction.model.Invoice;
import com.girinuaha.blockchain.webtransaction.model.InvoiceRepository;
import com.girinuaha.blockchain.webtransaction.service.RoomPaymentService;

@Controller
public class MainController implements ErrorController {

	@Autowired
	private HotelRoomRepository hotelRoomRepository;

	@Autowired
	private RoomPaymentService roomPaymentService;

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private AuditLogRepository auditLogRepository;
	
	@Value("${roompayment.contract.address}")
	private String contractAddress;
	
	@Value("${roompayment.contract.owner-address}")
	private String ownerAddress;

	@Value("${transaction.id.prefix}")
	private String transactionIdPrefix;

	@GetMapping(value = "/")
	public String index(Model model) {

		List<HotelRoom> rooms = hotelRoomRepository.findAll();

		model.addAttribute("rooms", rooms);

		return "index";
	}
	
	@GetMapping(value = "/admin")
	public String indexAdmin(Model model) {

		List<Invoice> invoices = invoiceRepository.findAll();

		model.addAttribute("contractAddress", contractAddress);
		model.addAttribute("invoices", invoices);

		return "index-admin";
	}

	@GetMapping(value = "/elements")
	public String elements() {
		return "elements";
	}

	@GetMapping(value = "/accomodation")
	public String acomodation() {
		return "accomodation";
	}

	@GetMapping(value = "/gallery")
	public String gallery() {
		return "gallery";
	}

	@GetMapping(value = "/reservations/{roomTypeId}/step-1")
	public String reservationStep1(Model model, @PathVariable long roomTypeId) {

		Optional<HotelRoom> room = hotelRoomRepository.findById(roomTypeId);

		model.addAttribute("room", room.get());
		model.addAttribute("step", "step-1");
		model.addAttribute("invoice", new Invoice());

		return "reservation";
	}

	@PostMapping(value = "/reservations/{roomTypeId}/step-2")
	public String reservationStep2(@ModelAttribute Invoice invoice, Model model, @PathVariable long roomTypeId) {

		Optional<HotelRoom> room = hotelRoomRepository.findById(roomTypeId);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
		String transactionId = transactionIdPrefix.concat(dtf.format(LocalDateTime.now()));
		invoice.setInvoiceId(transactionId);

		DecimalFormat format = new DecimalFormat("#.##");

		int numberOfRoom = Integer.parseInt(invoice.getNumberOfRoom());

		int roomPriceDollar = Integer.parseInt(room.get().getRoomPriceDollar()) * numberOfRoom;
		String roomPriceEther = format.format(Float.parseFloat(room.get().getRoomPriceEther()) * numberOfRoom);

		int depositDollar = roomPriceDollar / 2;
		String depositEther = format.format(Float.parseFloat(roomPriceEther) / 2);

		int totalDollar = roomPriceDollar + depositDollar;
		String totalEther = format.format(Float.parseFloat(roomPriceEther) + Float.parseFloat(depositEther));

		invoice.setRoomPriceDollar(String.valueOf(roomPriceDollar));
		invoice.setRoomPriceEther(roomPriceEther);
		invoice.setDepositDollar(String.valueOf(depositDollar));
		invoice.setDepositEther(depositEther);
		invoice.setHotelRoom(room.get());

		model.addAttribute("room", room.get());
		model.addAttribute("step", "step-2");
		model.addAttribute("totalDollar", totalDollar);
		model.addAttribute("totalEther", totalEther);
		model.addAttribute("invoice", invoice);

		return "reservation";
	}

	@PostMapping(value = "/payment/{transactionId}")
	public String payment(@ModelAttribute Invoice invoice, Model model, @PathVariable String transactionId)
			throws JsonProcessingException {

		// Save Invoice
		invoice.setStatus("ONGOING");
		invoiceRepository.saveAndFlush(invoice);
		
		HotelRoom room = invoice.getHotelRoom();
		room.setTotalBookedRoom(String.valueOf(Integer.parseInt(room.getTotalBookedRoom()) + Integer.parseInt(invoice.getNumberOfRoom())));
		hotelRoomRepository.saveAndFlush(room);
		
		DecimalFormat format = new DecimalFormat("#.##");
		int totalDollar = Integer.parseInt(invoice.getRoomPriceDollar()) + Integer.parseInt(invoice.getDepositDollar());
		String totalEther = format.format(Float.parseFloat(invoice.getRoomPriceEther()) + Float.parseFloat(invoice.getDepositEther()));
		
		model.addAttribute("room", room);
		model.addAttribute("invoice", invoice);
		model.addAttribute("totalDollar", totalDollar);
		model.addAttribute("totalEther", totalEther);
		model.addAttribute("contractAddress", contractAddress);
		
		return "summary";
	}

	@PostMapping(value = "/processPayment/{transactionId}")
	public String processPayment(@ModelAttribute Invoice invoice, @PathVariable String transactionId, @RequestParam String receipt, RedirectAttributes redirectAttributes) throws Exception {

		try {
		
			invoice = invoiceRepository.findByInvoiceId(transactionId);
		
			if (invoice.getPaymentMethod().equalsIgnoreCase("ETHEREUM")) {
				
				// Save Invoice
				invoice.setStatus("PAID");
				invoiceRepository.saveAndFlush(invoice);

				AuditLog auditLog = new AuditLog();
				auditLog.setTransactionId(transactionId);
				auditLog.setTransactionReceipt(receipt);
				auditLog.setCreateDate(String.valueOf(LocalDateTime.now()));
				auditLog.setUpdateDate(String.valueOf(LocalDateTime.now()));
				auditLog.setProcess("PAYMENT");
				auditLogRepository.saveAndFlush(auditLog);

			}
			
			redirectAttributes.addFlashAttribute("transactionId", transactionId);
			
			return "redirect:/success";
			
		} catch (Exception e) {
			return "redirect:/error";
		}
	}
	
	@GetMapping(value = "/finishTransaction/{transactionId}/{receipt}")
	@ResponseBody
	public String finishTransaction(@PathVariable String transactionId, @PathVariable String receipt) throws Exception {

		Invoice invoice = invoiceRepository.findByInvoiceId(transactionId);
		
		try {
			
			invoice.setStatus("DONE");
			invoiceRepository.saveAndFlush(invoice);
			
			HotelRoom room = invoice.getHotelRoom();
			room.setTotalBookedRoom(String.valueOf(Integer.parseInt(room.getTotalBookedRoom()) - Integer.parseInt(invoice.getNumberOfRoom())));
			hotelRoomRepository.saveAndFlush(room);
			
			AuditLog auditLog = new AuditLog();
			auditLog.setTransactionId(transactionId);
			auditLog.setTransactionReceipt(receipt);
			auditLog.setCreateDate(String.valueOf(LocalDateTime.now()));
			auditLog.setUpdateDate(String.valueOf(LocalDateTime.now()));
			auditLog.setProcess("DEPOSITRETURN");
			auditLogRepository.saveAndFlush(auditLog);

			return "success";
			
		} catch (Exception e) {
			return "error";
		}
		
	}
	
	@GetMapping(value = "/success")
	public String successPage(Model model) {
		return "success-page";
	}
	
	@GetMapping(value = "/error")
	public String errorPage() {
		return "error-page";
	}

	@GetMapping(value = "/balance/{address}")
	@ResponseBody
	public BigDecimal getBalance(@PathVariable String address) throws Exception {

		BigInteger balance = roomPaymentService.getBalance(address);
		
		BigDecimal inEther = Convert.fromWei(String.valueOf(balance), Unit.ETHER);

		return inEther;
	}

	@GetMapping(value = "/detail/{trxId}")
	@ResponseBody
	public String getDetail(@PathVariable String trxId) throws Exception {

		String detail = roomPaymentService.getDetailTransactionJson(ownerAddress, trxId);
		
		JSONObject object = new JSONObject(detail);
		return object.getString("status");
	}

	@GetMapping(value = "/create")
	@ResponseBody
	public TransactionReceipt createTransaction() throws Exception {

		TransactionReceipt receipt = roomPaymentService.createTransaction("0xD65e405037a1F7f86DbBdced0B2dBe086B4b95A5",
				"TRX2216", 100L, 200L, "1");

		return receipt;

	}

	@GetMapping(value = "/transactionId")
	@ResponseBody
	public String getTransactionId() throws Exception {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
		String transactionId = transactionIdPrefix.concat(dtf.format(LocalDateTime.now()));

		return transactionId;
	}
	
	@GetMapping(value = "/detail_transaction/{trxId}")
	@ResponseBody
	public Invoice getDetailTransaction(@PathVariable String trxId) throws Exception {

		Invoice invoice = invoiceRepository.findByInvoiceId(trxId);

		return invoice;
	}
	
	@GetMapping(value = "/test")
	public String test() throws Exception {
		return "index-temp";
	}
}
