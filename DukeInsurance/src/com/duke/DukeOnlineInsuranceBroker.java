package com.duke;

import com.duke.insurance.Purchase;
import com.duke.insurance.ProductionPurchaseCompletionSystem;
import com.duke.search.Policy;
import com.duke.search.Quote;
import com.duke.search.ProductionQuotingSystem;

import java.math.BigDecimal;
import java.util.*;

public class DukeOnlineInsuranceBroker implements InsuranceBroker {
	
	AdminFeeInterface newAdminFee = new NewAdminFee();

    private static final long MAX_QUOTE_AGE_MILLIS = 15 * 60 * 1000;

   	private Map<UUID, Quote> quotes = new HashMap<UUID, Quote>();
   	TimeManager timeManager;
    
/*    private long timeForAdminCharge;
    
    
    public static BigDecimal getStandardAdminCharge() {
    	return STANDARD_ADMIN_CHARGE;
    }*/
   	
   	public DukeOnlineInsuranceBroker(TimeManager pTimeManager){
   		timeManager = pTimeManager;
   	}


	@Override
    public List<Policy> searchForCarInsurance(String make, String model, int year) {

        List<Policy> searchResults = ProductionQuotingSystem.getInstance().searchFor(make, model, year);
        for (Policy policy : searchResults) {
            quotes.put(policy.id, new Quote(policy, timeManager.getCurrentMilliseconds()));
        }
        return searchResults;
    }

	
   @Override
    public void confirmPurchase(UUID id, String userAuthToken) {
	    quoteIdCheck(id);
        Quote quote = quotes.get(id);
        long timeNow = timeManager.getCurrentMilliseconds();
        long timeDifference = timeNow - quote.timestamp;
        quoteValidiatyCheck(quote, timeNow);
        BigDecimal adminFee = newAdminFee.generateAdminFee(timeDifference, quote.policy.premium);
        Purchase completePurchase = new Purchase(quote.policy.premium.add(adminFee), quote, timeNow, userAuthToken);
        purchaseComplete(completePurchase);
       
    }


public Map<UUID, Quote> getQuotes() {
		return quotes;
	}   

private void quoteValidiatyCheck(Quote quote, long timeNow) {
	if (timeNow - quote.timestamp > MAX_QUOTE_AGE_MILLIS) {
	    throw new IllegalStateException("Quote expired, please search again.");
	}
}

private void quoteIdCheck(UUID id) {
	if (!quotes.containsKey(id)) {
	    throw new NoSuchElementException("Offer ID is invalid");
	}
}

   public void purchaseComplete(Purchase completePurchase) {
	   ProductionPurchaseCompletionSystem.getInstance().process(completePurchase);
   }


}