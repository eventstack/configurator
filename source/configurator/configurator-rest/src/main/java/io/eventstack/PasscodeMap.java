package io.eventstack;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by gavin on 8/17/14.
 */
public class PasscodeMap {
    private static final ConcurrentMap<String, String> passcodeMap = new ConcurrentHashMap<String, String>();

    public static void storePasscode(String mobile, String passcode) {
        passcodeMap.put(mobile, passcode);
    }

    public static boolean verifyPasscode(String mobile, String passcode) {
        String existing = passcodeMap.get(mobile);
        if (existing != null && existing.equals(passcode)) {
            passcodeMap.remove(mobile);
            return true;
        }

        return false;
    }
}
