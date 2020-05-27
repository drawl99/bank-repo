package co.edu.usbcali.bank.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

public class TransactionDTO {

	@NotNull
	private Long tranId;

	@NotNull
	private BigDecimal amount;

	@NotNull
	private Timestamp date;

	@NotNull
	private String accoId;
	
	@NotNull
	private Long trtyId;
	
	@NotNull
	private String userEmail;

	public TransactionDTO(@NotNull Long tranId, @NotNull BigDecimal amount, @NotNull Timestamp date,
			@NotNull String accoId, @NotNull Long trtyId, @NotNull String userEmail) {
		super();
		this.tranId = tranId;
		this.amount = amount;
		this.date = date;
		this.accoId = accoId;
		this.trtyId = trtyId;
		this.userEmail = userEmail;
	}

	public TransactionDTO() {
		super();
	}

	public Long getTranId() {
		return tranId;
	}

	public void setTranId(Long tranId) {
		this.tranId = tranId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getAccoId() {
		return accoId;
	}

	public void setAccoId(String accoId) {
		this.accoId = accoId;
	}

	public Long getTrtyId() {
		return trtyId;
	}

	public void setTrtyId(Long trtyId) {
		this.trtyId = trtyId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
	
}
