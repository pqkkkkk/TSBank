import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.entity.BankAccount;
import com.example.entity.Customer;
import com.example.usecase.BankAccountService;
import com.example.usecase.dao.IBankAccountDao;
import com.example.usecase.dao.ICustomerDao;


public class BankAccountServiceTest {
    @Mock
    private IBankAccountDao bankAccountDao;
    @Mock
    private ICustomerDao customerDao;

    @InjectMocks
    private BankAccountService bankAccountService;

    @BeforeEach
    void SetUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateAccount_Success() throws Exception {
        // Arrange
        Customer customer = new Customer();
        BankAccount account = new BankAccount();
        when(customerDao.CreateCustomer(customer)).thenReturn(1);
        when(bankAccountDao.AddAccount(account)).thenReturn("12345");

        // Act
        String result = bankAccountService.CreateAccount(customer, account);

        // Assert
        assertEquals("12345", result);
        verify(customerDao, times(1)).CreateCustomer(customer);
        verify(bankAccountDao, times(1)).AddAccount(account);
        assertEquals(1, account.getCustomerId());
        assertEquals(0.0, account.getBalance());
        assertEquals("VND", account.getCurrency());
        assertFalse(account.getIsLocked());
    }

    @Test
    void testCreateAccount_FailedToCreateCustomer() throws Exception {
        // Arrange
        Customer customer = new Customer();
        BankAccount account = new BankAccount();
        when(customerDao.CreateCustomer(customer)).thenReturn(-1);

        // Act
        String result = bankAccountService.CreateAccount(customer, account);

        // Assert
        assertEquals("-1", result);
        verify(customerDao, times(1)).CreateCustomer(customer);
        verify(bankAccountDao, never()).AddAccount(any());
    }

    @Test
    void testCreateAccount_ExceptionThrown() throws Exception {
        // Arrange
        Customer customer = new Customer();
        BankAccount account = new BankAccount();
        when(customerDao.CreateCustomer(customer)).thenThrow(new RuntimeException("Database error"));

        // Act
        String result = bankAccountService.CreateAccount(customer, account);

        // Assert
        assertEquals("-1", result);
        verify(customerDao, times(1)).CreateCustomer(customer);
        verify(bankAccountDao, never()).AddAccount(any());
    }
}
