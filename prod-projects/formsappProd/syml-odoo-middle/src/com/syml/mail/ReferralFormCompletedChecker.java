package com.syml.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReferralFormCompletedChecker extends Thread {
	static Logger log = LoggerFactory
			.getLogger(ReferralFormCompletedChecker.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub

		log.debug("inside ReferralFormCompletedChecker  class run method");
		try {

			Thread.sleep(600000);

		} catch (InterruptedException e) {
			log.error("Thread interputed excpetion " + e);
		}
	}

}
