package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.usbcali.bank.domain.TransactionType;
import co.edu.usbcali.bank.dto.TransactionTypeDTO;

@Mapper
public interface TransactionTypeMapper {

	@Mapping(source = "trtyId", target = "trtyId")
	TransactionTypeDTO toTransactionTypeDTO(TransactionType transactionType);
	
	@Mapping(source = "trtyId", target = "trtyId")
	TransactionType transactionType(TransactionTypeDTO transactionTypeDTO);
	
	List<TransactionTypeDTO> transactionTypeDTOs(List<TransactionType>transactionTypes);
	List<TransactionType>transactionTypes(List<TransactionTypeDTO>transactionTypeDTOs);
	
}
