package com.duke;

import java.math.BigDecimal;

public class NewAdminFee implements AdminFeeInterface {
	private BigDecimal adminFee = new BigDecimal(0);
	
	@Override
	public BigDecimal generateAdminFee(long timePassed, BigDecimal premium){
		NoAdminFeeWhenPurchasedInThreeMinutes(timePassed, premium, 0, 0);
		AdminFeeWhenPurchasedBetweenThreeAndTenMinutes(timePassed, premium, 0.05, 15);
		AdminFeeWhenPurchasedBetweenElevenAndFifteenMinutes(timePassed, premium, 0.10, 40);
		
		return adminFee ;
	}
	
	
	public void NoAdminFeeWhenPurchasedInThreeMinutes(long timePassed, BigDecimal premium, double percentage, int number){
		if (timePassed <= 3 * 60 * 1000){
			adminFee = new BigDecimal(number);
		}
	}

	public void AdminFeeWhenPurchasedBetweenThreeAndTenMinutes(long timePassed, BigDecimal premium, double percentage, int number){
		if (timePassed > 3 * 60 * 1000 & timePassed <= 10 * 60 * 1000){
			BigDecimal adminFeeInPercentage = premium.multiply(BigDecimal.valueOf(percentage));
			BigDecimal adminFeeInPound = new BigDecimal(number);
			whichAdminFeeIsHigher(adminFeeInPercentage, adminFeeInPound);
		}
	}
	
	public void AdminFeeWhenPurchasedBetweenElevenAndFifteenMinutes(long timePassed, BigDecimal premium, double percentage, int number){
		if (timePassed > 10 * 60 * 1000 & timePassed <= 15 * 60 * 1000){
			BigDecimal adminFeeInPercentage = premium.multiply(BigDecimal.valueOf(percentage));
			BigDecimal adminFeeInPound = new BigDecimal(number);
			whichAdminFeeIsHigher(adminFeeInPercentage, adminFeeInPound);
		}
	}


	private void whichAdminFeeIsHigher(BigDecimal adminFeeInPercentage, BigDecimal adminFeeInPound) {
		if(adminFeeInPercentage.compareTo(adminFeeInPound) > 0 ){
			adminFee = adminFeeInPercentage;
		}
		else {
			adminFee = adminFeeInPound;
		}
	}

}
