package com.girinuaha.blockchain.webtransaction.service;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.girinuaha.blockchain.webtransaction.model.RoomPayment;
import com.girinuaha.blockchain.webtransaction.properties.RoomPaymentPropperties;

public class RoomPaymentService {

	private final String contractAddress;
    private final Web3j web3j;
    private final RoomPaymentPropperties config;
    
    
	public RoomPaymentService(String contractAddress, Web3j web3j, RoomPaymentPropperties config) {
		super();
		this.contractAddress = contractAddress;
		this.web3j = web3j;
		this.config = config;
	}
	
	public BigInteger getBalance(String userAddress) throws IOException {
        return web3j.ethGetBalance(userAddress, DefaultBlockParameterName.LATEST).send().getBalance();
    }
	
	public TransactionReceipt createTransaction(String customerAddress, String invoiceId, Long roomPrice, Long roomDeposit, String amount) throws Exception {
		RoomPayment roomPayment = loadContract(customerAddress);
		return roomPayment.createTransaction(invoiceId, BigInteger.valueOf(roomPrice), BigInteger.valueOf(roomDeposit), Convert.toWei(amount, Unit.ETHER).toBigInteger()).send();
	}
	
	public TransactionReceipt finishTransaction(String ownerAddress, String invoiceId, String amount) throws Exception {
		RoomPayment roomPayment = loadContract(ownerAddress);
		return roomPayment.finishTransaction(invoiceId, Convert.toWei(amount, Unit.ETHER).toBigInteger()).send();
	}
	
	public String getDetailTransaction(String customerAddress, String invoiceId) throws Exception {
		RoomPayment roomPayment = loadContract(customerAddress);
		return roomPayment.getDetailTransaction(invoiceId).send().toString();
	}
	
	public String getDetailTransactionJson(String customerAddress, String invoiceId) throws Exception {
		RoomPayment roomPayment = loadContract(customerAddress);
		return roomPayment.getDetailTransactionJson(invoiceId).send().toString();
	}
	
	private RoomPayment loadContract(String accountAddress) {
        return RoomPayment.load(contractAddress, web3j, txManager(accountAddress), config.gas());
    }

    private TransactionManager txManager(String accountAddress) {
        return new ClientTransactionManager(web3j, accountAddress);
    }
    
    
}
