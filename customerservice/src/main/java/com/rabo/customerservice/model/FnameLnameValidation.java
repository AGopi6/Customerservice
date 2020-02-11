package com.rabo.customerservice.model;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rabo.customerservice.exception.CustomerNotFoundException;
import com.rabo.customerservice.util.ErrorCodes;

@Component
public class FnameLnameValidation {
	private static final Logger log = LoggerFactory.getLogger(FnameLnameValidation.class);

	public boolean vaidateFnameLnameRegex(String fname, String lname) {
		if (StringUtils.isEmpty(fname) || StringUtils.isEmpty(lname)) {
			throw new CustomerNotFoundException(ErrorCodes.FNAME_AND_OR_LNAME_EMPTY);
		}
		fname = fname.trim();
		lname = lname.trim();
		log.info("Enters CustomerServiceController: SearchCustomerName by after trim===" + fname + lname);
		String regexName = "^[a-zA-Z]+([-_\\s]{1}[a-zA_Z0-9]+)*$";
		if (!fname.toLowerCase().matches(regexName) || !lname.toLowerCase().matches(regexName)) {
			throw new CustomerNotFoundException(ErrorCodes.FNAME_AND_OR_LNAME_EMPTY);
		}
		return true;
	}

}
