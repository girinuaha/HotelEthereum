//SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8;

import "./StringUtils.sol";

contract Room {

    address payable public owner = payable(msg.sender);

    struct RoomDetails {
        string transactionId;
        uint rentPerNight;
        uint rentDeposit;
        address payable owner;
        address payable currentTenant;
        string status;
    }

    mapping (string => RoomDetails) public getDetailTransaction;

    modifier validatePrice (uint _roomPrice, uint _roomDeposit) {
        require (msg.value >= ((_roomPrice + _roomDeposit) * 1 ether) / 1000, "Payment can't be proceed, because the nominal you input is less than the price it should be");
        _;
    }

    modifier onlyOwner {
        require(msg.sender == owner, "Only owner can access this function! Go Back!");
        _;
    }

    modifier notOwner {
        require(msg.sender != owner, "This address can't use for payment, please use another address, thank you...");
        _;
    }

    modifier checkStatus(string memory _transactionId) {

        require(getDetailTransaction[_transactionId].currentTenant != address(0), "There is no transaction with that TransactionID!");
        require(!StringUtils.equal(getDetailTransaction[_transactionId].status, "FINISH"), "This transaction already Finished!");
        _;
    }

    modifier validatePriceOwner (string memory _transactionId) {
        require (msg.value >= (getDetailTransaction[_transactionId].rentDeposit * 1 ether) / 1000, "Payment can't be proceed, because the return deposit you input is less than the price it should be");
        _;
    }

}