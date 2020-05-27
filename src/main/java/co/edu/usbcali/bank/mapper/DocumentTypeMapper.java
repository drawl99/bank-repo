package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.usbcali.bank.domain.DocumentType;
import co.edu.usbcali.bank.dto.DocumentTypeDTO;

@Mapper
public interface DocumentTypeMapper {

	@Mapping(source = "dotyId", target = "dotyId")
	DocumentTypeDTO toDocumentTypeDTO(DocumentType documentType);
	
	@Mapping(source = "dotyId", target = "dotyId")
	DocumentType toDocumentType(DocumentTypeDTO documentTypeDTO);
	
	List<DocumentTypeDTO>toDocumentTypeDTOs(List<DocumentType>documentTypes);
	List<DocumentType>toDocumentTypes(List<DocumentTypeDTO>documentTypeDTOs);
	
}
