package com.xiovr.unibot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.xiovr.unibot.data",
						     "com.xiovr.unibot.utils",
							 "com.xiovr.unibot.bot",
							 "com.xiovr.unibot.bot.packet",
								})
public class TestBaseConfig {

}
