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

import co.edu.usbcali.bank.domain.Transaction;
import co.edu.usbcali.bank.repository.AccountRepository;
import co.edu.usbcali.bank.repository.TransactionRepository;
import co.edu.usbcali.bank.repository.TransactionTypeRepository;
import co.edu.usbcali.bank.repository.UserRepository;

@Service
@Scope("singleton")
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	TransactionTypeRepository transactionTypeRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Validator validator;
	
	@Override
	@Transactional(readOnly = true)
	public List<Transaction> findAll() {
		return transactionRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Transaction> findById(Long id) {
		return transactionRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return transactionRepository.count();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Transaction save(Transaction entity) throws Exception {
		validate(entity);
		transactionRepository.save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Transaction update(Transaction entity) throws Exception {
		validate(entity);
		if(transactionRepository.findById(entity.getTranId()).isPresent() == false) {
			throw new Exception("El transaction con id:"+entity.getTranId()+" no existe");
		}
		transactionRepository.save(entity);
		return entity;
		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Transaction entity) throws Exception {
		if(transactionRepository.findById(entity.getTranId()).isPresent() == false) {
			throw new Exception("El transaction con id:"+entity.getTranId()+" no existe");
		}
		entity = transactionRepository.findById(entity.getTranId()).get();
		
		transactionRepository.delete(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		if(id == null || id <1) {
			throw new Exception("El id es obligatorio");
		}
		if(transactionRepository.findById(id).isPresent() == false) {
			throw new Exception("El transaction con id:"+id+" no existe");
		}
		delete(transactionRepository.findById(id).get());

	}

	@Override
	public void validate(Transaction entity) throws Exception {
		if(entity==null) {
			throw new Exception("El Transaction es nulo");
		}
		
		Set<ConstraintViolation<Transaction>> constraintViolations = validator.validate(entity);

        if (constraintViolations.size() > 0) {
            StringBuilder strMessage = new StringBuilder();

            for (ConstraintViolation<Transaction> constraintViolation : constraintViolations) {
                strMessage.append(constraintViolation.getPropertyPath()
                                                     .toString());
                strMessage.append(" - ");
                strMessage.append(constraintViolation.getMessage());
                strMessage.append(". \n");
            }

            throw new Exception(strMessage.toString());
        }


	}



	@Override
	public List<Transaction> findTransactionsByAccounts(String accoId) {
		return this.transactionRepository.findTransactionsByAccounts(accoId);
		
	}

	

	

}
