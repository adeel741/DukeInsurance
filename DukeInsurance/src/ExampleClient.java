import com.duke.DukeOnlineInsuranceBroker;
import com.duke.InsuranceBroker;
import com.duke.SystemTimeManager;
import com.duke.search.Policy;
import com.duke.search.Quote;

import java.math.BigDecimal;
import java.util.List;

public class ExampleClient {

    public static void main(String[] args) throws Exception {

        InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(new SystemTimeManager());
        String userAuthToken = "tom@example.com";

        List<Policy> searchResults = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014);
                                                    // try also Toyota Prius, Tesla Model S, etc

        if (searchResults.isEmpty()) {
            System.out.println("No search results found");
        } else {
            Policy policy = searchResults.get(0);

            // some time may pass...
            Thread.sleep(5 * 1000);

            if (priceAcceptable(policy.premium)) {
                insuranceBroker.confirmPurchase(policy.id, userAuthToken);
            }
           
         }
    }

    private static boolean priceAcceptable(BigDecimal price) {
        return true;
    }
}
