package com.xiovr.e5bot.bot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author xio4
 * E5bot launcher bean
 */
@Component
public class E5botLauncher implements CommandLineRunner {
    public void run(String... args) {
    	System.out.println("Test runner");
    }
}
