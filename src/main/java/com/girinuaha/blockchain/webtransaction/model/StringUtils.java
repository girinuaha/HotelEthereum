package com.girinuaha.blockchain.webtransaction.model;

import java.math.BigInteger;
import java.util.Arrays;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.9.4.
 */
@SuppressWarnings("rawtypes")
public class StringUtils extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_COMPARE = "compare";

    public static final String FUNC_EQUAL = "equal";

    public static final String FUNC_INDEXOF = "indexOf";

    @Deprecated
    protected StringUtils(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected StringUtils(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected StringUtils(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected StringUtils(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> compare(String _a, String _b) {
        final Function function = new Function(FUNC_COMPARE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_a), 
                new org.web3j.abi.datatypes.Utf8String(_b)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> equal(String _a, String _b) {
        final Function function = new Function(FUNC_EQUAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_a), 
                new org.web3j.abi.datatypes.Utf8String(_b)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> indexOf(String _haystack, String _needle) {
        final Function function = new Function(FUNC_INDEXOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_haystack), 
                new org.web3j.abi.datatypes.Utf8String(_needle)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static StringUtils load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new StringUtils(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static StringUtils load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new StringUtils(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static StringUtils load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new StringUtils(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static StringUtils load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new StringUtils(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
