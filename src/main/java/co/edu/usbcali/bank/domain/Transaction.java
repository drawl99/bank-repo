package co.edu.usbcali.bank.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "tran_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tranId;

	@NotNull
	private BigDecimal amount;

	@NotNull
	private Timestamp date;

	// bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name = "acco_id")
	@NotNull
	private Account account;

	// bi-directional many-to-one association to TransactionType
	@ManyToOne
	@JoinColumn(name = "trty_id")
	@NotNull
	private TransactionType transactionType;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "user_email")
	@NotNull
	private User user;

	public Transaction() {
	}

	public Long getTranId() {
		return this.tranId;
	}

	public void setTranId(Long tranId) {
		this.tranId = tranId;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public TransactionType getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}