/****************************************************************************
 *                              COPYRIGHT NOTICE
*
*                      Copyright(@2006) by Interland Technology Services PVT. LTD **
*
*      This program is used to monitor the stream control and Stop/Start
*      the streams. The program and related materials are confidential and
*      proprietary of Interland Technology Services PVT. LTD and no part of these materials
*      should be reproduced, published in any forms without the written
*      approval of INTERLAND
*
** Project Name         : iPSH
** Program description  : CustomSizeValidator
** Version No           : 1.0.0
** Author               : Adarsh
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.entity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.alfaris.ipsh.security.util.ApplicationProperties;

public class CustomSizeValidator implements ConstraintValidator<CustomSize, String> {

	private String maxKey;
	private String minKey;

	@Override
	public void initialize(CustomSize customSize) {
		this.maxKey = customSize.maxKey();
		this.minKey = customSize.minKey();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		int maxValue = Integer.parseInt(ApplicationProperties.INSTANCE.getValue(maxKey));
		int minValue = Integer.parseInt(ApplicationProperties.INSTANCE.getValue(minKey));
		if (StringUtils.isEmpty(value) || value.length() < minValue || value.length() > maxValue) {
			return false;
		} 
		return true;
	}
}
