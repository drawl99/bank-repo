package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.usbcali.bank.domain.UserType;
import co.edu.usbcali.bank.dto.UserTypeDTO;

@Mapper
public interface UserTypeMapper {

	@Mapping(source = "ustyId", target = "ustyId")
	UserTypeDTO toUserTypeDTO(UserType userType);
	
	@Mapping(source = "ustyId", target = "ustyId")
	UserType toUserType(UserTypeDTO userTypeDTO);
	
	List<UserTypeDTO>toUserTypeDTOs(List<UserType>userTypes);
	List<UserType>toUserTypes(List<UserTypeDTO>userTypeDTOs);
}
