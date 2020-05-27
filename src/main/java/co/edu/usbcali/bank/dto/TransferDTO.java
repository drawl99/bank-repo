package co.edu.usbcali.bank.dto;

import java.math.BigDecimal;

public class TransferDTO {

	private String accoIdOrigin;
	private String accoIdDestination;
	private BigDecimal amount;
	private String userEmail;
	
	public TransferDTO(String accoIdOrigin, String accoIdDestination, BigDecimal amount, String userEmail) {
		super();
		this.accoIdOrigin = accoIdOrigin;
		this.accoIdDestination = accoIdDestination;
		this.amount = amount;
		this.userEmail = userEmail;
	}
	
	public TransferDTO() {
		super();
	}
	
	
	public String getAccoIdOrigin() {
		return accoIdOrigin;
	}
	public void setAccoIdOrigin(String accoIdOrigin) {
		this.accoIdOrigin = accoIdOrigin;
	}
	public String getAccoIdDestination() {
		return accoIdDestination;
	}
	public void setAccoIdDestination(String accoIdDestination) {
		this.accoIdDestination = accoIdDestination;
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
