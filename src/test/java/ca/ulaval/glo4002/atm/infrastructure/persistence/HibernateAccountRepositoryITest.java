package ca.ulaval.glo4002.atm.infrastructure.persistence;

import static org.junit.Assert.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.ulaval.glo4002.atm.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.atm.domain.accounts.Account;
import ca.ulaval.glo4002.atm.domain.accounts.StandardAccount;

public class HibernateAccountRepositoryITest {

    private static final int ACCOUNT_NUMBER = 1;
    private static final double BALANCE = 120.11;

    private static EntityManagerFactory entityManagerFactory;

    private HibernateAccountRepository accountRepository;

    private Account account;

    @BeforeClass
    public static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("atm-test");
    }

    @Before
    public void setUp() {
        EntityManagerProvider.setEntityManager(entityManagerFactory.createEntityManager());

        accountRepository = new HibernateAccountRepository();

        account = new StandardAccount(ACCOUNT_NUMBER, BALANCE);
    }

    @Test
    public void canSaveAndRetrieveAccount() {
        Account account = new StandardAccount(ACCOUNT_NUMBER, BALANCE);

        new EntityManagerProvider().executeInTransaction(() -> accountRepository.persist(account));

        Account retrievedAccount = accountRepository.findByNumber(ACCOUNT_NUMBER);
        assertSameAccount(account, retrievedAccount);
    }

    private void assertSameAccount(Account account, Account retrievedAccount) {
        assertEquals(account.getAccountNumber(), retrievedAccount.getAccountNumber());
    }
}
