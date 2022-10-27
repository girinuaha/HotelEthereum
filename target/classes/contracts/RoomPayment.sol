//SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8;

import "./RoomDetails.sol";

contract RoomPayment is Room {

    function createTransaction (string memory _transactionId, uint _rentCost, uint _rentDeposit) public payable validatePrice(_rentCost, _rentDeposit) {
        
        uint totalFee = ((_rentCost + _rentDeposit) * 1 ether)/1000;
        owner.transfer(totalFee);

        uint change = msg.value - totalFee;

        if (change != 0) {
            payable(msg.sender).transfer(change); 
        }

        getDetailTransaction[_transactionId] = RoomDetails(_transactionId, _rentCost, _rentDeposit, owner, payable(msg.sender), "ONGOING");
    }

    function finishTransaction (string memory _transactionId) public payable onlyOwner checkStatus(_transactionId) validatePriceOwner(_transactionId) {

        uint deposit = getDetailTransaction[_transactionId].rentDeposit * 1 ether / 1000;
        address payable _tenantAddress = getDetailTransaction[_transactionId].currentTenant;
        _tenantAddress.transfer(deposit); 

        uint change = msg.value - deposit;

        if (change != 0) {
            payable(msg.sender).transfer(change); 
        }

        getDetailTransaction[_transactionId].status = "FINISH";
    }
}