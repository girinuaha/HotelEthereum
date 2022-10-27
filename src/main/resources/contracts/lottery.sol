//SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8;

contract Lottery {
    address public manager;
    address payable[] players;

    constructor() {
        manager = msg.sender;
    }
    
    function enter() public payable {
        require(msg.value > .01 ether, "Must have at least 0.01 ether");
        
        players.push(payable(msg.sender));
    }
    
    function pickWinner() public restricted {
        uint index = random() % players.length;
        address payable winner = payable(address(players[index]));
        address contest = address(this);
        winner.transfer(contest.balance);
        players = new address payable[](0);
    }
    
    function getPlayers() public restricted view returns (address payable[] memory) {
        return players;
    }
    
    function random() private view returns (uint) {
        return uint(keccak256(abi.encodePacked(block.difficulty, block.timestamp, players)));
    }
    
    modifier restricted() {
        require(manager == msg.sender, "Only Manager can pick the winner");
        _;
    }
}