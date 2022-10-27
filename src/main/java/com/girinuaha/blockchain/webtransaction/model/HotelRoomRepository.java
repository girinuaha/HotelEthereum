package com.girinuaha.blockchain.webtransaction.model;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {

}
