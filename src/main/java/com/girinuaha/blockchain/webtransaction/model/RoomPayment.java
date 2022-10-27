package com.girinuaha.blockchain.webtransaction.model;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.json.JSONObject;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
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
public class RoomPayment extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_CREATETRANSACTION = "createTransaction";

    public static final String FUNC_FINISHTRANSACTION = "finishTransaction";

    public static final String FUNC_GETDETAILTRANSACTION = "getDetailTransaction";

    public static final String FUNC_OWNER = "owner";

    @Deprecated
    protected RoomPayment(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RoomPayment(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected RoomPayment(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected RoomPayment(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> createTransaction(String _transactionId, BigInteger _rentCost, BigInteger _rentDeposit, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CREATETRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_transactionId), 
                new org.web3j.abi.datatypes.generated.Uint256(_rentCost), 
                new org.web3j.abi.datatypes.generated.Uint256(_rentDeposit)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> finishTransaction(String _transactionId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_FINISHTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_transactionId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<Tuple6<String, BigInteger, BigInteger, String, String, String>> getDetailTransaction(String param0) {
        final Function function = new Function(FUNC_GETDETAILTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple6<String, BigInteger, BigInteger, String, String, String>>(function,
                new Callable<Tuple6<String, BigInteger, BigInteger, String, String, String>>() {
                    @Override
                    public Tuple6<String, BigInteger, BigInteger, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, BigInteger, BigInteger, String, String, String>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }
    
    public RemoteFunctionCall<String> getDetailTransactionJson(String param0) {
        final Function function = new Function(FUNC_GETDETAILTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<String>(function,
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        
                        JSONObject object = new JSONObject();
                        object.put("transactionID", (String) results.get(0).getValue());
                        object.put("rentCost", (BigInteger) results.get(1).getValue());
                        object.put("deposit", (BigInteger) results.get(2).getValue());
                        object.put("ownerAddress", (String) results.get(3).getValue());
                        object.put("currentTenant", (String) results.get(4).getValue());
                        object.put("status", (String) results.get(5).getValue());
                        
                        return object.toString();
                    }
                });
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }
    
    public static RemoteCall<RoomPayment> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RoomPayment.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<RoomPayment> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RoomPayment.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<RoomPayment> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RoomPayment.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<RoomPayment> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RoomPayment.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RoomPayment load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RoomPayment(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static RoomPayment load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RoomPayment(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RoomPayment load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new RoomPayment(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RoomPayment load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new RoomPayment(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
