package co.edu.usbcali.bank.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The persistent class for the registered_account database table.
 * 
 */
@Entity
@Table(name = "registered_account")
@NamedQuery(name = "RegisteredAccount.findAll", query = "SELECT r FROM RegisteredAccount r")
public class RegisteredAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "reac_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reacId;

	@NotNull
	@Size(min=1, max=1)
	private String enable;

	// bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name = "acco_id")
	@NotNull
	private Account account;

	// bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name = "clie_id")
	@NotNull
	private Client client;

	public RegisteredAccount() {
	}

	public Long getReacId() {
		return this.reacId;
	}

	public void setReacId(Long reacId) {
		this.reacId = reacId;
	}

	public String getEnable() {
		return this.enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}