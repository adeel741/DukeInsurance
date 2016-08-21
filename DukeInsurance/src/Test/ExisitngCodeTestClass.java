package Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.duke.DukeOnlineInsuranceBroker;
import com.duke.InsuranceBroker;
import com.duke.PurchaseService;
import com.duke.insurance.Purchase;
import com.duke.search.Policy;
import com.duke.search.ProductionQuotingSystem;
import com.duke.search.Quote;

@RunWith(MockitoJUnitRunner.class)
public class ExisitngCodeTestClass {

	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock
	PurchaseService sObj;
	
	
	@Spy
	InsuranceBroker insuranceBrokerSpy= new DukeOnlineInsuranceBroker(new FakeTimeManager()); 


	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSearchCarInsuranceWhenMakeIsNotListed() {
		String make = "Toyota";
		String model = "Corola";
		int year = 2015;

		FakeTimeManager fakeTimeManager = new FakeTimeManager();
		InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(fakeTimeManager);
		assertEquals(insuranceBroker.searchForCarInsurance(make,model,year).size(), 0);
	}
	
	@Test
	public void testSearchCarInsuranceWhenModelIsNotListed() {
		String make = "Audi";
		String model = "Unknown";
		int year = 2015;

		FakeTimeManager fakeTimeManager = new FakeTimeManager();
		InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(fakeTimeManager);
		assertEquals(insuranceBroker.searchForCarInsurance(make,model,year).size(), 0);
	}
	
	@Test
	public void testSearchForCarInsuranceWhenMakeAndModelIsListed() {
		String make = "Audi";
		String model = "A1";
		int year = 2013;

		FakeTimeManager fakeTimeManager = new FakeTimeManager();
		InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(fakeTimeManager);
		assertEquals(insuranceBroker.searchForCarInsurance(make,model,year).size(), 1);
	}
	
	@Test() 
	public void validQuoteConfirmationTest(){
		Policy policy=new Policy("Test Policy",new BigDecimal(123.0028212));
		List<Policy> policyList= new ArrayList<Policy>();
		policyList.add(policy);
		String userAuthToken = "testing@yahoo.com";
		when(insuranceBrokerSpy.searchForCarInsurance("Audi", "A1", 2014)).thenReturn(policyList);
		long timeStamp= System.currentTimeMillis();
		Quote quote= new Quote(policy,timeStamp);
		((DukeOnlineInsuranceBroker) insuranceBrokerSpy).getQuotes().put(policyList.get(0).id,quote);
		insuranceBrokerSpy.confirmPurchase(policyList.get(0).id, userAuthToken);
		((DukeOnlineInsuranceBroker) verify(insuranceBrokerSpy, atLeast(1))).purchaseComplete(any(Purchase.class));
	}

	@Test(expected = NoSuchElementException.class) 
	public void emptyQuotePurchaseThrowExceptionTest() {
		FakeTimeManager fakeTimeManager = new FakeTimeManager();
       	UUID id = UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8");
    	String userAuthToken = "testing@yahoo.com";
    	InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(fakeTimeManager);	
    	insuranceBroker.confirmPurchase(id, userAuthToken);
	}

	@Test(expected = IllegalStateException.class) 
	public void expiredQuotePurchaseThrowExceptionTest() {

		FakeTimeManager fakeTimeManager = new FakeTimeManager();
       	String userAuthToken = "testing@yahoo.com";
    	InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(fakeTimeManager);	
    	List<Policy> searchResults1 = insuranceBroker.searchForCarInsurance("Audi", "A1",  2014);
        Policy policy = searchResults1.get(0);
        fakeTimeManager.AddSimulatedElapsedMinutes(16);
        insuranceBroker.confirmPurchase(policy.id, userAuthToken);
	}
}
