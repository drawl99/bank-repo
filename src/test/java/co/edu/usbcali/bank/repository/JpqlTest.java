package co.edu.usbcali.bank.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.RegisteredAccount;
import co.edu.usbcali.bank.domain.Transaction;


@SpringBootTest
class JpqlTest {

	@PersistenceContext
	EntityManager entityManager;
	
	final static Logger log = LoggerFactory.getLogger(JpqlTest.class);
	
	@Test
	@Transactional(readOnly = true)
	void selectAll() {
		String jpql = "SELECT cli FROM Client cli";
		List<Client> clients = entityManager.createQuery(jpql, Client.class).getResultList();
		
		for (Client client : clients) {
			log.info("ID:"+client.getClieId());
			log.info("NAME:"+client.getName());
		}
	}
		
	@Test
	@Transactional(readOnly = true)
	void selectWhere() {
		String jpql = "SELECT cli FROM Client cli WHERE cli.documentType.dotyId= :dotyId";
		List<Client> clients = entityManager.createQuery(jpql, Client.class).
				setParameter("dotyId", 1L).getResultList();
		
		clients.forEach(client->{
			log.info("ID:"+client.getClieId());
			log.info("NAME:"+client.getName());
		});
	}

	@Test
	@Transactional(readOnly = true)
	void selectLike() {
		String jpql = "SELECT cli FROM Client cli WHERE cli.name LIKE :name AND "
				+ "cli.documentType.dotyId= :dotyId";
		List<Client> clients = entityManager.createQuery(jpql, Client.class).
				setParameter("name", "A%").
				setParameter("dotyId", 1L).
				getResultList();
		
		clients.forEach(client->{
			log.info("ID:"+client.getClieId());
			log.info("NAME:"+client.getName());
		});
	}
	
	@Test
	@Transactional(readOnly = true)
	void selectAritmeticas() {
		String jpql = "SELECT count(acco) FROM Account acco";
		Long count=entityManager.createQuery(jpql, Long.class).getSingleResult();
		log.info("Count:"+count);
		
		jpql = "SELECT count(acco), "
				+ "AVG(acco.balance), "
				+ "MIN(acco.balance), "
				+ "MAX(acco.balance) FROM Account acco";
		
		Object[]  object=(Object[])entityManager.createQuery(jpql).getSingleResult();
		log.info("COUNT:"+object[0]);
		log.info("AVG:"+object[1]);
		log.info("MIN:"+object[2]);
		log.info("MAX:"+object[3]);
	}
	
	@Test
	@Transactional(readOnly=true)
	void selectSize() {
		String jpql="SELECT cli FROM Client cli WHERE size(cli.accounts)>3";
		List<Client> clients=entityManager.
				createQuery(jpql,Client.class).
				getResultList();
		
		clients.forEach(client->{
			log.info("ID"+client.getClieId());
			log.info("NAME:"+client.getName());
		});		
	}
	//----------------------------------------------------------------------
	@Test
	@Transactional(readOnly = true)
	void selectClienteConMasDinero() {
		String jpql = "SELECT acco "
				+ "FROM Account acco "
				+ "WHERE acco.balance = (SELECT MAX(acco.balance) FROM Account acco)";
		
	
		Account account = entityManager.createQuery(jpql, Account.class).getSingleResult();
		log.info("NAME: "+account.getClient().getName());
		log.info("BALANCE: "+account.getBalance().toString());
		
		
		
		
		
	}
	
	@Test
	@Transactional(readOnly = true)
	void selectSumaDineroCuentas() {
		String jpql = "SELECT cli.name, SUM(acco.balance) "
				+ "FROM Account acco JOIN acco.client cli "
				+ "WHERE size(cli.accounts)>3"
				+ "GROUP BY cli.clieId";
		
	
		List<Object[]> objects = entityManager.createQuery(jpql,Object[].class).getResultList();
		//objects.forEach(o ->log.info(Arrays.toString(o)));
		
		for (Object[] object : objects) {
			log.info("NAME: "+object[0]);
			log.info("TOTAL BALANCE: "+object[1]);
		}
		
		
	}
	
	@Test
	@Transactional(readOnly = true)
	void selectSumaConsignacionesCuenta() {
		String jpql = "SELECT SUM(tran.amount) "
				+ "FROM Transaction tran "
				+ "WHERE tran.transactionType.trtyId= :trtyId AND tran.account.accoId= :accoId"
				;
		Object total = entityManager.createQuery(jpql,Object.class)
				.setParameter("trtyId", 2L)
				.setParameter("accoId", "2928-4331-8647-0560")
				.getSingleResult();
		
		log.info("Total de la cuenta: "+total);
	}
	
	@Test
	@Transactional(readOnly = true)
	void selectPromedioSaldosDisponibles() {
		String jpql = "SELECT AVG(acco.balance) "
				+ "FROM Account acco ";
				
		Double avg = entityManager.createQuery(jpql,Double.class).getSingleResult();
		log.info("AVERAGE: "+avg);
			
	}
	
	@Test
	@Transactional(readOnly = true)
	void selectRetirosEntre5MY15M() {
		String jpql = "SELECT tran "
				+ "FROM Transaction tran "
				+ "WHERE tran.amount BETWEEN 5000000 AND 15000000 AND tran.transactionType.trtyId= :trtyId";
				
		List<Transaction> transactions = entityManager.createQuery(jpql,Transaction.class).setParameter("trtyId", 1L).getResultList();
		for (Transaction transaction : transactions) {
			log.info("EMAIL: "+transaction.getUser().getUserEmail());
			log.info("VALOR: "+transaction.getAmount());
			log.info("TIPO: "+transaction.getTransactionType().getName());
		}	
	}
	
	@Test
	@Transactional(readOnly = true)
	void selectNombresClientesConA() {
		String jpql = "SELECT cli "
				+ "FROM Client cli "
				+ "WHERE cli.name LIKE '%A%'";
		
		List<Client>clients = entityManager.createQuery(jpql,Client.class).getResultList();
		clients.forEach(c -> log.info("NAME: "+c.getName()));
			
	}
	
	@Test
	@Transactional(readOnly = true)
	void selectClientesConRetiroMenorA500K() {
		String jpql = "SELECT tran "
				+ "FROM Transaction tran "
				+ "WHERE tran.amount<500000 AND tran.transactionType.trtyId= :trtyId";
		
		List<Transaction>transactions = entityManager.createQuery(jpql,Transaction.class).setParameter("trtyId", 1L)
				.getResultList();
		
		for (Transaction transaction : transactions) {
			log.info("NAME: "+transaction.getAccount().getClient().getName());
			log.info("ACCOUNT: "+transaction.getAccount().getAccoId());
			log.info("AMOUNT: "+transaction.getAmount());
		}
			
	}
	
	@Test
	@Transactional(readOnly = true)
	void selectConsignacionesCuenta() {
		String jpql = "SELECT tran "
				+ "FROM Transaction tran "
				+ "WHERE tran.transactionType.trtyId= :trtyId AND tran.account.accoId= :accoId";
		
		List<Transaction>transactions = entityManager.createQuery(jpql,Transaction.class).
				setParameter("trtyId", 2L).
				setParameter("accoId", "2928-4331-8647-0560")
				.getResultList();
		
		for (Transaction transaction : transactions) {
			log.info("NAME: "+transaction.getAccount().getClient().getName());
			log.info("ACCOUNT: "+transaction.getAccount().getAccoId());
			log.info("AMOUNT: "+transaction.getAmount());
		}
			
	}
	
	@Test
	@Transactional(readOnly = true)
	void selectCuentasRegistradas() {
		String jpql = "SELECT regAcco "
				+ "FROM RegisteredAccount regAcco "
				+ "";
		
		List<RegisteredAccount>registeredAccounts = entityManager.createQuery(jpql,RegisteredAccount.class).getResultList();
		
		for (RegisteredAccount registeredAccount : registeredAccounts) {
			log.info("CUENTA: "+registeredAccount.getAccount().getAccoId());
			log.info("CLIENTE: "+registeredAccount.getAccount().getClient().getName());
			log.info("REGISTRADOR: "+registeredAccount.getClient().getName());
			
		}
			
	}
}
