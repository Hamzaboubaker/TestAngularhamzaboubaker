/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.prestafind.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
public class SMSSender {

    // Twilio API credentials
    public static final String ACCOUNT_SID = "ACe065d04d018e18b1edcbf0c746211266";
    public static final String AUTH_TOKEN = "4543ac116c9650bc77dfc3d7976dd31e";

    // Phone numbers for the sender and recipient
    public static final String FROM_NUMBER = "+12706481612";
    public static final String TO_NUMBER = "+21653853066";

    public static void sendSMS(String messageBody) {
        System.out.println("Sending SMS...");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(TO_NUMBER),
                new PhoneNumber(FROM_NUMBER), messageBody)
                .create();

        System.out.println("SMS sent successfully!");
    }

}
