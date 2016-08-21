package com.duke;

import java.math.BigDecimal;

public class NewAdminFee implements AdminFeeInterface {
	private BigDecimal adminFee = new BigDecimal(0);
	
	@Override
	public BigDecimal generateAdminFee(long timePassed, BigDecimal premium){
		if (timePassed <= 3 * 60 * 1000){
			adminFee = new BigDecimal(0);
		}
		
		if (timePassed > 3 * 60 * 1000 & timePassed <= 10 * 60 * 1000){
			
			System.out.println("3 to 10");
			BigDecimal adminFeeInPercentage = premium.multiply(BigDecimal.valueOf(0.05));
			BigDecimal adminFeeInPound = new BigDecimal(15);
			if(adminFeeInPercentage.compareTo(adminFeeInPound) > 0 ){
				adminFee = adminFeeInPercentage;
			}
			else {
				adminFee = adminFeeInPound;
			}
		}
		
		if (timePassed > 10 * 60 * 1000 & timePassed <= 15 * 60 * 1000){
			System.out.println("11 to 15");
			BigDecimal adminFeeInPercentage = premium.multiply(BigDecimal.valueOf(0.10));
			BigDecimal adminFeeInPound = new BigDecimal(40);
			if(adminFeeInPercentage.compareTo(adminFeeInPound) > 0 ){
				adminFee = adminFeeInPercentage;
			}
			else {
				adminFee = adminFeeInPound;
			}
		}
		
		return adminFee ;
	}
	
	
	/*public void NoAdminFeeWhenPurchasedInThreeMinutes(long timePassed, BigDecimal premium){
		setAdminFee(adminFee);
	}

	public void AdminFeeWhenPurchasedBetweenThreeAndTenMinutes(long timePassed, BigDecimal premium){
		setAdminFee(adminFee);
	}
	
	public void AdminFeeWhenPurchasedBetweenElevenAndFifteenMinutes(long timePassed, BigDecimal premium){
		setAdminFee(adminFee);
	}
	
	private void setAdminFee(BigDecimal adminFee) {
		this.adminFee = adminFee;
		
	}*/
	
	/*NoAdminFeeWhenPurchasedInThreeMinutes(timePassed, premium);
	AdminFeeWhenPurchasedBetweenThreeAndTenMinutes(timePassed, premium);
	AdminFeeWhenPurchasedBetweenElevenAndFifteenMinutes(timePassed, premium);*/

}
