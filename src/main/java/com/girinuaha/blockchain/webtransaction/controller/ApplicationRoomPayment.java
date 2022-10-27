package com.girinuaha.blockchain.webtransaction.controller;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;

import com.girinuaha.blockchain.webtransaction.model.RoomPayment;
import com.girinuaha.blockchain.webtransaction.service.RoomPaymentService;

import okhttp3.OkHttpClient;

@SuppressWarnings("unused")
public class ApplicationRoomPayment {

	private static String contractAddress = "0x60EEA186F47F3aD52152d3e23D3b2D5D90646c2E";
	private static String clientAddress = "http://127.0.0.1:8545";

	private static String ownerAddress = "UTC--2022-09-07T02-41-20.658703000Z--eef338632de465ddb42e8a68335e172d743cf235";
	private static String User1 = "UTC--2022-09-07T02-41-46.392402000Z--97387ba118b5e9be94a0bc2b191d9210d229164e";
	private static String User2 = "UTC--2022-09-07T02-42-08.999322000Z--d65e405037a1f7f86dbbdced0b2dbe086b4b95a5";

	private static final Logger log = LoggerFactory.getLogger(ApplicationRoomPayment.class);

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {

		log.info("Load Contract");

		Web3j web3j = Web3j.build(new HttpService(clientAddress, new OkHttpClient.Builder().build()));

//		Credentials credentials = WalletUtils.loadCredentials("kosonginaja",
//				"/Users/girinuaha/Documents/Ethereum/test-network/keystore/" + ownerAddress);

		Credentials credentials = Credentials.create("0x97387bA118b5E9BE94a0bc2b191d9210d229164E");

		TransactionManager txManager = new RawTransactionManager(web3j, credentials, 12345L);

		RoomPayment contract = RoomPayment.load(contractAddress, web3j, txManager, ManagedTransaction.GAS_PRICE,
				Contract.GAS_LIMIT);

//		try {
//
////			TransactionReceipt receipt = contract.createTransaction("TRX2215", BigInteger.valueOf(200L), BigInteger.valueOf(200L), BigInteger.valueOf(40000000000000000L)).send();
//			TransactionReceipt receipt = contract.finishTransaction("TRX2212", BigInteger.valueOf(4000000000000000000L)).send();
//
//			System.out.println(receipt);
//		} catch (Exception e) {
////			System.out.println(e.getMessage().substring(e.getMessage().indexOf("Revert reason:"), e.getMessage().length()));
//			System.out.println(e.getMessage());
//		}
//
//		System.out.println(getBalanceWei(web3j, "0x97387bA118b5E9BE94a0bc2b191d9210d229164E"));

		System.out.println(contract.getDetailTransaction("TRX2212").send());
//		System.out.println(contract.getDetailTransaction("TRX2213").send());
//		System.out.println(contract.getDetailTransaction("TRX2214").send());
	}
	
	public static BigInteger getBalanceWei(Web3j web3j, String address)
			throws InterruptedException, ExecutionException {
		EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
		return balance.getBalance();
	}

}
