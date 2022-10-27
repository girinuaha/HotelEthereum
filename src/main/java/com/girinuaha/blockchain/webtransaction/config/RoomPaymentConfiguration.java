package com.girinuaha.blockchain.webtransaction.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;

import com.girinuaha.blockchain.webtransaction.model.RoomPayment;
import com.girinuaha.blockchain.webtransaction.properties.RoomPaymentPropperties;
import com.girinuaha.blockchain.webtransaction.service.RoomPaymentService;

import okhttp3.OkHttpClient;

@Configuration
public class RoomPaymentConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(RoomPaymentConfiguration.class);

    @Value("${roompayment.contract.owner-address}")
    private String ownerAddress;

    @Value("${web3j.client-address}")
    private String clientAddress;

    @Autowired
    private RoomPaymentPropperties config;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(clientAddress, new OkHttpClient.Builder().build()));
    }

    @SuppressWarnings("deprecation")
	@Bean
    public RoomPaymentService contract(Web3j web3j, @Value("${roompayment.contract.address}") String contractAddress)
            throws Exception {
    	
        if (StringUtils.isEmpty(contractAddress)) {
            RoomPayment roomPayment = deployContract(web3j);
            return initLotteryService(roomPayment.getContractAddress(), web3j);
        }

        return initLotteryService(contractAddress, web3j);
    }

    private RoomPaymentService initLotteryService(String contractAddress, Web3j web3j) {
        return new RoomPaymentService(contractAddress, web3j, config);
    }

    private RoomPayment deployContract(Web3j web3j) throws Exception {
        LOG.info("About to deploy new contract...");
        RoomPayment contract = RoomPayment.deploy(web3j, txManager(web3j), config.gas()).send();
        LOG.info("Deployed new contract with address '{}'", contract.getContractAddress());
        return contract;
    }

    private TransactionManager txManager(Web3j web3j) {
        return new ClientTransactionManager(web3j, ownerAddress);
    }
}
