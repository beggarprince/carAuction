package aaa.pfa.carAuctionBackend.services;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WelcomeService {

    private static final List<String> WELCOME_MESSAGES = Collections.unmodifiableList(List.of(
            "Welcome! Your treasure hunt starts here.",
            "Deals so good, you'll think it's a typo.",
            "Come on in! Bargains await.",
            "Finders keepers—welcome to the deal zone.",
            "Step right up! Your next favorite thing is here.",
            "Welcome! Good stuff, great prices.",
            "Your search ends here. Welcome!",
            "Glad you stopped by! Check out these deals.",
            "Savings ahead. Welcome, smart shopper!",
            "Welcome! Don’t blink, these deals move fast.",
            "Ready, set, shop! Welcome aboard.",
            "Happy browsing! The deals start now.",
            "Welcome! Where value meets variety.",
            "Thanks for visiting! Don’t leave empty-handed.",
            "Welcome! It’s bargain o’clock."
    ));

    private final static int messageCount = WELCOME_MESSAGES.size();
    private final static Random random = new Random();

    public static String getWelcomeMessage(){
        return WELCOME_MESSAGES.get(random.nextInt(messageCount));
    }

}
