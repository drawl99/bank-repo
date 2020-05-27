package co.edu.usbcali.bank.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.repository.ClientRepository;
import co.edu.usbcali.bank.repository.DocumentTypeRepository;

@Service
@Scope("singleton")
public class ClientServiceImpl implements ClientService {
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	AccountService accountService;

	@Autowired
	DocumentTypeRepository documentTypeRepository;
	
	@Autowired
	Validator validator;
	
	@Autowired
	SendEmailServiceImpl sendEmailServiceImpl;
	
	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		
		return clientRepository.findAll(Sort.by("clieId"));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Client> findById(Long id) {
		
		return clientRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		
		return clientRepository.count();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Client save(Client entity) throws Exception {
		validate(entity);
		if(clientRepository.findById(entity.getClieId()).isPresent() == true) {
			throw new Exception("El client con id:"+entity.getClieId()+" ya existe");
		}
		if(entity.getDocumentType()== null || 
				documentTypeRepository.findById(entity.getDocumentType().getDotyId()).isPresent()==false) {
			throw new Exception("El documentype no existe");
		}
		if(entity.getClieId() <= 0) {
			throw new Exception("El Id tiene que ser mayor a 0");
		}
		clientRepository.save(entity);
		Account account = new Account();
		account.setClient(entity);
		account.setEnable("N");
		account.setAccoId("");
		account.setBalance(new BigDecimal(0));
		account.setVersion(1L);
		account.setPassword("");
		sendEmailServiceImpl.sendOpenAccount(entity.getEmail(), accountService.save(account));
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Client update(Client entity) throws Exception {
		validate(entity);
		if(clientRepository.findById(entity.getClieId()).isPresent() == false) {
			throw new Exception("El client con id:"+entity.getClieId()+" no existe");
		}
		if(entity.getDocumentType()== null || 
				documentTypeRepository.findById(entity.getDocumentType().getDotyId()).isPresent()==false) {
			throw new Exception("El documentType no existe");
		}
		clientRepository.save(entity);
		return entity;
		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Client entity) throws Exception {
		if(clientRepository.findById(entity.getClieId()).isPresent() == false) {
			throw new Exception("El client con id:"+entity.getClieId()+" no existe");
		}
		entity = clientRepository.findById(entity.getClieId()).get();
		
		if(this.tieneCuentasActivas(entity)) {
			throw new Exception("El client tiene accounts asociadas");
		}
		
		if(entity.getRegisteredAccounts().size()>0) {
			throw new Exception("El client tiene accounts registradas");
		}
		
		entity.setEnable("N");
		clientRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		if(id == null || id <1) {
			throw new Exception("El id es obligatorio");
		}
		if(clientRepository.findById(id).isPresent()==false) {
			throw new Exception("El client con id:"+id+" no existe");
		}
		delete(clientRepository.findById(id).get());

	}

	@Override
	public void validate(Client entity) throws Exception {
		if(entity==null) {
			throw new Exception("El client es nulo");
		}
		
		Set<ConstraintViolation<Client>> constraintViolations = validator.validate(entity);

        if (constraintViolations.size() > 0) {
            StringBuilder strMessage = new StringBuilder();

            for (ConstraintViolation<Client> constraintViolation : constraintViolations) {
                strMessage.append(constraintViolation.getPropertyPath()
                                                     .toString());
                strMessage.append(" - ");
                strMessage.append(constraintViolation.getMessage());
                strMessage.append(". \n");
            }

            throw new Exception(strMessage.toString());
        }

	}
	
	public boolean tieneCuentasActivas(Client entity) {
		List<Account> listaCuentas = entity.getAccounts();
		
		for(Account account: listaCuentas) {
			if(account.getEnable().equals("S")) {
				return true;
			}
		}
		return false;
	}

}
