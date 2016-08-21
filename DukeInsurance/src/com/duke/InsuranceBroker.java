package com.duke;

import com.duke.insurance.Purchase;
import com.duke.search.Policy;
import com.duke.search.Quote;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface InsuranceBroker {

    List<Policy> searchForCarInsurance(String make, String model, int year);

    void confirmPurchase(UUID id, String userAuthToken);
    
}
