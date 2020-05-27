package co.edu.usbcali.bank.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.DocumentType;
import co.edu.usbcali.bank.repository.DocumentTypeRepository;


@Service
@Scope("singleton")
public class DocumentTypeServiceImpl implements DocumentTypeService {

	
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	
	@Autowired
	Validator validator;
	
	@Override
	@Transactional(readOnly = true)
	public List<DocumentType> findAll() {
		
		return documentTypeRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<DocumentType> findById(Long id) {
		
		return documentTypeRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		
		return documentTypeRepository.count();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DocumentType save(DocumentType entity) throws Exception {
		validate(entity);
		documentTypeRepository.save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DocumentType update(DocumentType entity) throws Exception {
		validate(entity);
		if(documentTypeRepository.findById(entity.getDotyId()).isPresent() == false) {
			throw new Exception("El documentType con id:"+entity.getDotyId()+" no existe");
		}
		documentTypeRepository.save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(DocumentType entity) throws Exception {
		if(documentTypeRepository.findById(entity.getDotyId()).isPresent() == false) {
			throw new Exception("El documentType con id:"+entity.getDotyId()+" no existe");
		}
		entity = documentTypeRepository.findById(entity.getDotyId()).get();
		if(entity.getClients().size() > 0) {
			throw new Exception("El documetType tiene clientes asociados");
		}
		documentTypeRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		if(id == null || id <1) {
			throw new Exception("El id es obligatorio");
		}
		if(documentTypeRepository.findById(id).isPresent() == false) {
			throw new Exception("El documentType con id:"+id+" no existe");
		}
		delete(documentTypeRepository.findById(id).get());
	}

	@Override
	public void validate(DocumentType entity) throws Exception {
		if(entity==null) {
			throw new Exception("El documentType es nulo");
		}
		
		Set<ConstraintViolation<DocumentType>> constraintViolations = validator.validate(entity);

        if (constraintViolations.size() > 0) {
            StringBuilder strMessage = new StringBuilder();

            for (ConstraintViolation<DocumentType> constraintViolation : constraintViolations) {
                strMessage.append(constraintViolation.getPropertyPath()
                                                     .toString());
                strMessage.append(" - ");
                strMessage.append(constraintViolation.getMessage());
                strMessage.append(". \n");
            }

            throw new Exception(strMessage.toString());
        }


	}

}
