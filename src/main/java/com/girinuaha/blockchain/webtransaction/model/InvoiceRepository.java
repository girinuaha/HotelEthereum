package com.girinuaha.blockchain.webtransaction.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

	@Query("SELECT i FROM Invoice i WHERE i.invoiceId = :invoiceId")
	public Invoice findByInvoiceId(String invoiceId);
}
