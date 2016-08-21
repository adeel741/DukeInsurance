package com.duke;

import java.math.BigDecimal;

public interface AdminFeeInterface {
	
	BigDecimal generateAdminFee(long timePassed, BigDecimal premium);
		
}
