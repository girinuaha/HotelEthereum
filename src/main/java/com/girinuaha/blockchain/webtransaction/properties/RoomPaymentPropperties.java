package com.girinuaha.blockchain.webtransaction.properties;

import java.math.BigInteger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.web3j.tx.gas.StaticGasProvider;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "roompayment.contract")
@Getter
@Setter
public class RoomPaymentPropperties {

	private BigInteger gasPrice;
    private BigInteger gasLimit;

    public StaticGasProvider gas() {
        return new StaticGasProvider(gasPrice, gasLimit);
    }
}
