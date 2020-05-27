package co.edu.usbcali.bank.dto;

import java.math.BigDecimal;

public class DepositDTO {

	private String accoId;
	private BigDecimal amount;
	private String userEmail;
	
	public DepositDTO(String accoId, BigDecimal amount, String userEmail) {
		super();
		this.accoId = accoId;
		this.amount = amount;
		this.userEmail = userEmail;
	}

	public DepositDTO() {
		super();
	}

	public String getAccoId() {
		return accoId;
	}

	public void setAccoId(String accoId) {
		this.accoId = accoId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
}
