package co.edu.usbcali.bank.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.bank.domain.DocumentType;
import co.edu.usbcali.bank.dto.DocumentTypeDTO;
import co.edu.usbcali.bank.mapper.DocumentTypeMapper;
import co.edu.usbcali.bank.service.DocumentTypeService;

@RestController
@RequestMapping("/api/documentType/")
@CrossOrigin("*")
public class DocumentTypeController {

	private final static Logger log = LoggerFactory.getLogger(DocumentTypeController.class);
	
	@Autowired
	DocumentTypeService documentTypeService;
	
	@Autowired
	DocumentTypeMapper documentTypeMapper;
	
	@GetMapping("findAll")
	public ResponseEntity<?> findAll(){
		log.info("call findAll");
		List<DocumentType> documentTypes = documentTypeService.findAll();
		List<DocumentTypeDTO> documentTypeDTOs = documentTypeMapper.toDocumentTypeDTOs(documentTypes);
		return ResponseEntity.ok().body(documentTypeDTOs);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		log.info("call findById with id:{}", id);
		Optional<DocumentType> documenTypeOptional = documentTypeService.findById(id);
		if (documenTypeOptional.isPresent() == false) {
			return ResponseEntity.badRequest().body("El documentType no existe");
		}

		DocumentType documentType = documenTypeOptional.get();
		
		DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDocumentTypeDTO(documentType);

		return ResponseEntity.ok().body(documentTypeDTO);
	}
	
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody DocumentTypeDTO documentTypeDTO){
		try {
			DocumentType documentType = documentTypeMapper.toDocumentType(documentTypeDTO);
			documentType = documentTypeService.save(documentType);
			documentTypeDTO = documentTypeMapper.toDocumentTypeDTO(documentType);
			return ResponseEntity.ok().body(documentTypeDTO);
		} catch (Exception e) {
			log.error("save {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody DocumentTypeDTO documentTypeDTO){
		try {
			DocumentType documentType = documentTypeMapper.toDocumentType(documentTypeDTO);
			documentType = documentTypeService.update(documentType);
			documentTypeDTO = documentTypeMapper.toDocumentTypeDTO(documentType);
			return ResponseEntity.ok().body(documentTypeDTO);
		} catch (Exception e) {
			log.error("save {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		log.info("call findById with id:{}", id);
		try {
			documentTypeService.deleteById(id);
			return ResponseEntity.ok().body("");
		} catch (Exception e) {
			log.error("delete {}",e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	
	}
	
	@GetMapping("count")
	public ResponseEntity<?> count(){
		return ResponseEntity.ok().body(documentTypeService.count());
	}
}
