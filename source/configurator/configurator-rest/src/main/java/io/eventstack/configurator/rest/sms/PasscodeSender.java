package io.eventstack.configurator.rest.sms;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import io.eventstack.PasscodeMap;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gavin on 8/16/14.
 */
public class PasscodeSender {

    private static final int PASSCODE_LENGTH = 6;

    public String sendPasscode(String mobileNumber) {
        String accountSid = System.getProperty("twilioAccountSid");
        String authToken = System.getProperty("twilioAuthToken");
        String fromNumber = System.getProperty("twilioFromNumber");
        String passcode = generatePasscode();

        TwilioRestClient client = new TwilioRestClient(accountSid, authToken);

        // Build the parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", mobileNumber));
        params.add(new BasicNameValuePair("From", fromNumber));
        params.add(new BasicNameValuePair("Body", "Eventstack Passcode: " + passcode));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = null;
        try {
            message = messageFactory.create(params);

            PasscodeMap.storePasscode(mobileNumber, passcode);
        } catch (TwilioRestException e) {
            e.printStackTrace();
            return "error";
        }
        System.out.println(message.getSid());

        return passcode;
    }

    private static String generatePasscode() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(PASSCODE_LENGTH);
        for (int i = 0; i < PASSCODE_LENGTH; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }
}
