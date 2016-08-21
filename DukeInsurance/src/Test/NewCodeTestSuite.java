package Test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.duke.AdminFeeInterface;
import com.duke.DukeOnlineInsuranceBroker;
import com.duke.InsuranceBroker;
import com.duke.NewAdminFee;
import com.duke.search.Policy;
import com.duke.search.Quote;

public class NewCodeTestSuite {
	AdminFeeInterface testObj = new NewAdminFee();
	DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(new FakeTimeManager(), testObj);
	private Map<UUID, Quote> quotes =insuranceBroker.getQuotes();
	BigDecimal adminFee;
	List<Policy> searchResults = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014);
    Policy policy = searchResults.get(0);
        
	@Test
	public void whenPolicyConfirmedWithInThreeMinuesThenNoAdminFee() {
		long timePassed = (3*60*1000);
		insuranceBroker.confirmPurchase(policy.id, "test@yahoo.com");
        adminFee = new BigDecimal(0);
        assertEquals(testObj.generateAdminFee(timePassed, policy.premium ), adminFee);
	}
	
	@Test
	public void whenPolicyConfirmedBetweenFourAndTenMinuesThenFivePercentOrFifteenPoundAdminFeeWhichEverIsGreater() {
		long timePassed = (10*60*1000);
		insuranceBroker.confirmPurchase(policy.id, "test@yahoo.com");
		BigDecimal adminFeeInPercentage = policy.premium.multiply(BigDecimal.valueOf(0.05));
		BigDecimal adminFeeInPound = new BigDecimal(15);
		if(adminFeeInPercentage.compareTo(adminFeeInPound) > 0 ){
			adminFee = adminFeeInPercentage;
		}
		else {
			adminFee = adminFeeInPound;
		}
        assertEquals(testObj.generateAdminFee(timePassed, policy.premium ), adminFee);
	}
	
		
	@Test
	public void policyConfirmedBetweenElevenAndFifteenMinuesHasTenPercentAdminFeeORFourtyPoundAdminFeeWhichEverIsGreater() {
		long timePassed = (15*60*1000);
		insuranceBroker.confirmPurchase(policy.id, "test@yahoo.com");
		BigDecimal adminFeeInPercentage = policy.premium.multiply(BigDecimal.valueOf(0.10));
		BigDecimal adminFeeInPound = new BigDecimal(40);
		if(adminFeeInPercentage.compareTo(adminFeeInPound) > 0 ){
			adminFee = adminFeeInPercentage;
		}
		else {
			adminFee = adminFeeInPound;
		}
        assertEquals(testObj.generateAdminFee(timePassed, policy.premium ), adminFee);
	}
	
	

}
