package com.xiovr.e5bot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.xiovr.e5bot.data",
						     "com.xiovr.e5bot.utils",
							 "com.xiovr.e5bot.bot",
								})
public class TestBaseConfig {

}
