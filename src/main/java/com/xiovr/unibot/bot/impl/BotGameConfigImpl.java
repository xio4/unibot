/**
 * Copyright (c) 2014 xio4
 * Universal bot for lineage-like games (Archeage, Lineage2 etc)
 *
 * This file is part of Unibot.
 *
 * Unibot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.xiovr.unibot.bot.impl;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xiovr.unibot.bot.BotGameConfig;
import com.xiovr.unibot.bot.Param;
import com.xiovr.unibot.bot.Settings;
import com.xiovr.unibot.utils.BotUtils;
import com.xiovr.unibot.utils.SortedProperties;

@Component
public class BotGameConfigImpl implements BotGameConfig {
	public final String DIR_PATH;
	private static final Logger logger = LoggerFactory
			.getLogger(BotGameConfigImpl.class);

	public BotGameConfigImpl() {
		super();
		DIR_PATH = getClass().getProtectionDomain().getCodeSource()
				.getLocation().toString().substring(6);
	}

	private Properties createSettings(Class<?> clazz, String fn, String comment) {

		Properties props = new SortedProperties();
		try {
//			File file = new File("/" + DIR_PATH + "/" + fn);
			File file = new File(fn);
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				// Find setters
				if (method.getName().startsWith("set")) {
					if (method.isAnnotationPresent(Param.class)) {
						Annotation annotation = method
								.getAnnotation(Param.class);
						Param param = (Param) annotation;
						if (param.name().equals("")
								|| param.values().length == 0) {
							throw new RuntimeException("Wrong param in class "
									+ clazz.getCanonicalName()
									+ " with method " + method.getName());
						}
						Class<?>[] paramClazzes = method.getParameterTypes();
						if (paramClazzes.length != 1) {
							throw new RuntimeException(
									"Error contract design in class "
											+ clazz.getCanonicalName()
											+ " with method "
											+ method.getName());
						}
						// Check param belongs to List
						Class<?> paramClazz = paramClazzes[0];
						if (List.class.isAssignableFrom(paramClazz)) {
							// Oh, its array...
							// May be its InetSocketAddress?
							Type[] gpt = method.getGenericParameterTypes();
							if (gpt[0] instanceof ParameterizedType) {
								ParameterizedType type = (ParameterizedType) gpt[0];
								Type[] typeArguments = type
										.getActualTypeArguments();

								for (Type typeArgument : typeArguments) {
									Class<?> classType = ((Class<?>) typeArgument);
									if (InetSocketAddress.class
											.isAssignableFrom(classType)) {
										String[] vals = param.values();
										for (int i = 0; i < vals.length / 2; ++i) {
											props.setProperty(param.name()
													+ "." + String.valueOf(i)
													+ ".ip", vals[i * 2]);
											props.setProperty(param.name()
													+ "." + String.valueOf(i)
													+ ".port", vals[i * 2 + 1]);
										}
										props.setProperty(param.name()
												+ ".count",
												String.valueOf(vals.length / 2));

									} else {
										throw new RuntimeException(
												"Settings param in class "
														+ clazz.getCanonicalName()
														+ " with method "
														+ method.getName()
														+ " not implementes yet");
									}
								}

							}
						} else if (paramClazz.isPrimitive()) {
							props.setProperty(param.name(), param.values()[0]);

						} else if (String.class.isAssignableFrom(paramClazz)) {

							props.setProperty(param.name(), param.values()[0]);

						} else {
							throw new RuntimeException(
									"Settings param in class "
											+ clazz.getCanonicalName()
											+ " with method "
											+ method.getName()
											+ " not implemented yet");
						}

					}
				}
			}

			BotUtils.saveProperties(file, props, comment);
		} catch (IOException e) {
			logger.error("Error save file " + fn);
		}
		return props;
	}

	@Override
	// public void loadSettings(Settings instance, Class<?> clazz, String fn)
	public void loadSettings(Settings instance, String fn) {

		Class<?> clazz = instance.getClass().getInterfaces()[0];
//		File file = new File("/" + DIR_PATH + "/" + fn);
		File file = new File(fn);
		Properties props = null;
		try {
			props = BotUtils.loadProperties(file);

			// botEnvironment.setClientIp((props.getProperty("client.ip",
			// "127.0.0.1"));
		} catch (IOException e) {
			// e.printStackTrace();
//			logger.warn("Can't load settings /" + DIR_PATH + "/" + fn
//					+ ". Create new settings file.");
			logger.warn("Can't load settings " + fn
					+ ". Create new settings file.");
			props = createSettings(clazz, fn, "Bot v" + VERSION);

		}
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			// Find setters
			if (method.getName().startsWith("set")) {

				if (method.isAnnotationPresent(Param.class)) {

					Annotation annotation = method.getAnnotation(Param.class);
					Param param = (Param) annotation;
					if (param.name().equals("") || param.values().length == 0) {
						throw new RuntimeException("Wrong param in class "
								+ clazz.getCanonicalName() + " with method "
								+ method.getName());
					}
					Class<?>[] paramClazzes = method.getParameterTypes();
					if (paramClazzes.length != 1) {
						throw new RuntimeException(
								"Error contract design in class "
										+ clazz.getCanonicalName()
										+ " with method " + method.getName());
					}
					// Check param belongs to List
					Class<?> paramClazz = paramClazzes[0];

					try {
						if (List.class.isAssignableFrom(paramClazz)) {
							// Oh, its array...
							// May be its InetSocketAddress?

							Type[] gpt = method.getGenericParameterTypes();
							if (gpt[0] instanceof ParameterizedType) {
								ParameterizedType type = (ParameterizedType) gpt[0];
								Type[] typeArguments = type
										.getActualTypeArguments();

								for (Type typeArgument : typeArguments) {
									Class<?> classType = ((Class<?>) typeArgument);
									if (InetSocketAddress.class
											.isAssignableFrom(classType)) {
										List<InetSocketAddress> isaArr = new ArrayList<>();
										int cnt = Integer.parseInt(props
												.getProperty(param.name()
														+ ".count", "2"));
										for (int i = 0; i < cnt; ++i) {
											String defaultHostname = "";
											String defaultPort = "";
											if (param.values().length > i * 2) {
												defaultHostname = param
														.values()[i * 2];
												defaultPort = param.values()[i * 2 + 1];
											} else {
												defaultHostname = DEFAULT_HOSTNAME;
												defaultPort = String
														.valueOf(DEFAULT_PORT);
											}
											InetSocketAddress isa = new InetSocketAddress(
													props.getProperty(
															param.name()
																	+ "."
																	+ String.valueOf(i)
																	+ ".ip",
															defaultHostname),
													Integer.parseInt(props.getProperty(
															param.name()
																	+ "."
																	+ String.valueOf(i)
																	+ ".port",
															defaultPort)));
											// invocableClazz.getMethod(method.getName(),
											// InetSocketAddress.class).invoke(
											// instance, isa);
											isaArr.add(isa);

										}

										method.invoke(instance, isaArr);

									} else {
										throw new RuntimeException(
												"Settings param in class "
														+ clazz.getCanonicalName()
														+ " with method "
														+ method.getName()
														+ " not implemented yet");
									}
								}

							}
						} else if (paramClazz.isPrimitive()) {
							if (int.class.isAssignableFrom(paramClazz)) {
								method.invoke(instance, Integer.parseInt(props
										.getProperty(param.name(),
												param.values()[0])));
							} else if (long.class
									.isAssignableFrom(paramClazz)) {
								method.invoke(
										instance,
										Long.parseLong(props.getProperty(
												param.name(), param.values()[0])));
							} else if (boolean.class
									.isAssignableFrom(paramClazz)) {
								method.invoke(
										instance,
										Boolean.parseBoolean(props.getProperty(
												param.name(), param.values()[0])));
							} else if (double.class
									.isAssignableFrom(paramClazz)) {
								method.invoke(
										instance,
										Double.parseDouble(props.getProperty(
												param.name(), param.values()[0])));
							}

						} else if (String.class.isAssignableFrom(paramClazz)) {
							method.invoke(
									instance,
									props.getProperty(param.name(),
											param.values()[0]));
						} else {
							throw new RuntimeException(
									"Settings param in class "
											+ clazz.getCanonicalName()
											+ " with method "
											+ method.getName()
											+ " not implemented yet");
						}
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveSettings(Settings instance, String fn, String comment) {
		Class<?> clazz = instance.getClass().getInterfaces()[0];
		Class<?> invocableClazz = instance.getClass();
//		File file = new File("/" + DIR_PATH + "/" + fn);
		File file = new File(fn);
		Properties props = new SortedProperties();

		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			// Find setters
			if (method.getName().startsWith("set")) {

				if (method.isAnnotationPresent(Param.class)) {

					Annotation annotation = method.getAnnotation(Param.class);
					Param param = (Param) annotation;
					if (param.name().equals("") || param.values().length == 0) {
						throw new RuntimeException("Wrong param in class "
								+ clazz.getCanonicalName() + " with method "
								+ method.getName());
					}
					Class<?>[] paramClazzes = method.getParameterTypes();
					if (paramClazzes.length != 1) {
						throw new RuntimeException(
								"Error contract design in class "
										+ clazz.getCanonicalName()
										+ " with method " + method.getName());
					}
					// Check param belongs to List
					Class<?> paramClazz = paramClazzes[0];

					try {
						if (List.class.isAssignableFrom(paramClazz)) {
							// Oh, its array...
							// May be its InetSocketAddress?

							Type[] gpt = method.getGenericParameterTypes();
							if (gpt[0] instanceof ParameterizedType) {
								ParameterizedType type = (ParameterizedType) gpt[0];
								Type[] typeArguments = type
										.getActualTypeArguments();

								for (Type typeArgument : typeArguments) {
									Class<?> classType = ((Class<?>) typeArgument);
									if (InetSocketAddress.class
											.isAssignableFrom(classType)) {
										List<InetSocketAddress> isaArr = (List<InetSocketAddress>) invocableClazz
												.getMethod("get"+method.getName().substring(3))
												.invoke(instance);
										int cnt = isaArr.size();
										props.setProperty(param.name()
												+ ".count", String.valueOf(cnt));
										for (int i = 0; i < cnt; ++i) {
											props.setProperty(param.name()
													+ "." + String.valueOf(i)
													+ ".ip", isaArr.get(i)
													.getHostString());
											props.setProperty(param.name()
													+ "." + String.valueOf(i)
													+ ".port", String
													.valueOf(isaArr.get(i)
															.getPort()));
										}
									} else {
										throw new RuntimeException(
												"Settings param in class "
														+ clazz.getCanonicalName()
														+ " with method "
														+ method.getName()
														+ " not implemented yet");
									}
								}

							}
						} else if (paramClazz.isPrimitive()) {
								props.setProperty(param.name(), String.valueOf(
										invocableClazz.getMethod("get"+method.getName().substring(3))
											.invoke(instance)));
						} else if (String.class.isAssignableFrom(paramClazz)) {
								props.setProperty(param.name(), (String)(
										invocableClazz.getMethod("get"+method.getName().substring(3))
											.invoke(instance)));
						} else {
							throw new RuntimeException(
									"Settings param in class "
											+ clazz.getCanonicalName()
											+ " with method "
											+ method.getName()
											+ " not implemented yet");
						}
						BotUtils.saveProperties(file, props, "Bot v" + VERSION);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException
							| IOException
							e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

//	private void saveBotSettings(BotSettings settings, String botFn,
//			String comments) {
//		try {
//			File fn = new File("/" + DIR_PATH + "/" + botFn);
//			Properties props = new SortedProperties();
//			props.setProperty("bot.type", String.valueOf(settings.getType()));
//			props.setProperty("bot.login", settings.getLogin());
//			props.setProperty("bot.password", settings.getPassword());
//			props.setProperty("bot.name", settings.getName());
//			props.setProperty("bot.serverId",
//					String.valueOf(settings.getServerId()));
//			props.setProperty("bot.autoconnect",
//					String.valueOf(settings.getAutoConnect()));
//			props.setProperty("bot.autoconnect_interval",
//					String.valueOf(settings.getAutoConnectInterval()));
//			props.setProperty("bot.disabled",
//					String.valueOf(settings.getDisabled()));
//			props.setProperty("bot.logging",
//					String.valueOf(settings.getLogging()));
//			props.setProperty("bot.modif_logging",
//					String.valueOf(settings.getModifLogging()));
//			BotUtils.saveProperties(fn, props, comments);
//		} catch (IOException e) {
//			logger.error("Error save file " + botFn);
//		}
//	}

//	private Properties createBotSettings(String botFn, String comments) {
//		try {
//			File fn = new File("/" + DIR_PATH + "/" + botFn);
//			Properties props = new SortedProperties();
//
//			props.setProperty("bot.type",
//					String.valueOf(BotSettings.OUTGAME_TYPE));
//			props.setProperty("bot.login", "");
//			props.setProperty("bot.password", "");
//			props.setProperty("bot.name", "");
//			props.setProperty("bot.serverId", String.valueOf(0));
//			props.setProperty("bot.client_proxy", String.valueOf(false));
//			props.setProperty("bot.autoconnect", String.valueOf(false));
//			props.setProperty("bot.autoconnect_interval", String.valueOf(10));
//			props.setProperty("bot.disabled", String.valueOf(false));
//			props.setProperty("bot.logging", String.valueOf(false));
//			props.setProperty("bot.modif_logging", String.valueOf(false));
//
//			BotUtils.saveProperties(fn, props, comments);
//			return props;
//		} catch (IOException e) {
//			logger.error("Error create file " + botFn);
//
//		}
//		return null;
//	}

//	private void loadBotSettings(BotSettings botSettings, String botFn) {
//		// File fn = new File("/" + DIR_PATH + "/" + SETTINGS_CFG_DIR + "/"
//		File fn = new File("/" + DIR_PATH + "/" + "/" + botFn);
//		Properties props = null;
//		try {
//			props = BotUtils.loadProperties(fn);
//
//			// botEnvironment.setClientIp((props.getProperty("client.ip",
//			// "127.0.0.1"));
//		} catch (IOException e) {
//			// e.printStackTrace();
//			logger.warn("Can't load settings /" + DIR_PATH + "/" + botFn
//					+ ". Create new settings file.");
//			props = createEnvironmentProps(fn);
//
//		}
//		if (props == null) {
//			logger.error("Error create bot settings. Terminate.");
//			return;
//		}
//
//		botSettings.setType(Integer.parseInt(props.getProperty("bot.type",
//				String.valueOf(BotSettings.OUTGAME_TYPE))));
//		botSettings.setLogin(props.getProperty("bot.login", ""));
//		botSettings.setPassword(props.getProperty("bot.password", ""));
//		botSettings.setName(props.getProperty("bot.name", ""));
//		botSettings.setServerId(Integer.parseInt(props.getProperty(
//				"bot.serverId", "0")));
//		botSettings.setAutoConnect(Boolean.parseBoolean(props.getProperty(
//				"bot.autoconnect", "false")));
//		botSettings.setAutoConnectInterval(Integer.parseInt(props.getProperty(
//				"bot.autoconnect_interval", "10")));
//		botSettings.setDisabled(Boolean.parseBoolean(props.getProperty(
//				"bot.disabled", "false")));
//		botSettings.setLogging(Boolean.parseBoolean(props.getProperty(
//				"bot.logging", "false")));
//		botSettings.setModifLogging(Boolean.parseBoolean(props.getProperty(
//				"bot.modif_logging", "false")));
//		logger.info("Bot settings " + botFn + " has been loaded");
//	}

//	private Properties createEnvironmentProps(File fn) {
//		try {
//			Properties props = new SortedProperties();
//
//			props.setProperty("bot.connect_stages_count", String.valueOf(2));
//			props.setProperty("client.0.ip", DEFAULT_CLIENT_IP);
//			props.setProperty("server.0.ip", DEFAULT_SERVER_IP);
//			props.setProperty("client.0.port",
//					String.valueOf(DEFAULT_CLIENT_PORT));
//			props.setProperty("server.0.port",
//					String.valueOf(DEFAULT_SERVER_PORT));
//			props.setProperty("client.1.ip", DEFAULT_CLIENT_IP);
//			props.setProperty("server.1.ip", DEFAULT_SERVER_IP);
//			props.setProperty("client.1.port",
//					String.valueOf(DEFAULT_CLIENT_PORT));
//			props.setProperty("server.1.port",
//					String.valueOf(DEFAULT_SERVER_PORT));
//			props.setProperty("script.update_interval", "100");
//			props.setProperty("bot.next_connection_interval", "5");
//			props.setProperty("client.proxy", "false");
//			props.setProperty("bot.raw_data", "false");
//			props.setProperty("bot.port_range_min",
//					String.valueOf(PORT_RANGE_MIN));
//			props.setProperty("bot.port_range_max",
//					String.valueOf(PORT_RANGE_MAX));
//
//			BotUtils.saveProperties(fn, props, "Autogeneratied file. Bot v"
//					+ VERSION);
//
//			return props;
//		} catch (IOException e) {
//			logger.error("Error create file " + fn.getName());
//
//		}
//		return null;
//	}
//
//	private void savePropsFromBotEnvironment(BotEnvironment botEnvironment) {
//		try {
//
//			File fn = new File("/" + DIR_PATH + "/" + BotEnvironment.ENVIRONMENT_CFG_FN);
//			Properties props = new SortedProperties();
//
//			int stagesCount = botEnvironment.getServerAddresses().size();
//			props.setProperty("bot.connect_stages_count",
//					String.valueOf(stagesCount));
//
//			for (int i = 0; i < stagesCount; ++i) {
//				props.setProperty("client." + i + ".ip", botEnvironment
//						.getClientAddresses().get(i).getAddress()
//						.getHostAddress());
//				props.setProperty("server." + i + ".ip", botEnvironment
//						.getServerAddresses().get(i).getAddress()
//						.getHostAddress());
//				props.setProperty(
//						"client." + i + ".port",
//						String.valueOf(botEnvironment.getClientAddresses()
//								.get(i).getPort()));
//				props.setProperty(
//						"server." + i + ".port",
//						String.valueOf(botEnvironment.getServerAddresses()
//								.get(i).getPort()));
//			}
//			props.setProperty("script.update_interval",
//					String.valueOf(botEnvironment.getUpdateInterval()));
//			props.setProperty("bot.next_connection_interval", String
//					.valueOf(botEnvironment.getNextBotConnectionInterval()));
//			props.setProperty("client.proxy",
//					String.valueOf(botEnvironment.getProxy()));
//			props.setProperty("bot.raw_data",
//					String.valueOf(botEnvironment.getRawData()));
//			props.setProperty("bot.port_range_min",
//					String.valueOf(botEnvironment.getPortRangeMin()));
//			props.setProperty("bot.port_range_max",
//					String.valueOf(botEnvironment.getPortRangeMax()));
//			BotUtils.saveProperties(fn, props, "Autogeneratied file. Bot v"
//					+ VERSION);
//
//		} catch (IOException e) {
//
//		}
//	}
//
//	private void loadPropsToBotEnvironment(BotEnvironment botEnvironment) {
//		File fn = new File("/" + DIR_PATH + "/" + ENVIRONMENT_CFG_FN);
//		Properties props = null;
//		try {
//			props = BotUtils.loadProperties(fn);
//
//			// botEnvironment.setClientIp((props.getProperty("client.ip",
//			// "127.0.0.1"));
//		} catch (IOException e) {
//			// e.printStackTrace();
//			logger.warn("Can't load config /" + DIR_PATH + "/"
//					+ ENVIRONMENT_CFG_FN + ". Create new config.");
//			props = createEnvironmentProps(fn);
//
//		}
//		if (props == null) {
//			logger.error("Error create properties. Terminate load.");
//			return;
//		}
//
//		int stagesCount = Integer.parseInt(props.getProperty(
//				"bot.connect_stages_count", String.valueOf(0)));
//
//		for (int i = 0; i < stagesCount; ++i) {
//			InetSocketAddress cAddr = new InetSocketAddress(props.getProperty(
//					"client." + i + ".ip", DEFAULT_CLIENT_IP),
//					Integer.parseInt(props.getProperty("client." + i + ".port",
//							String.valueOf(DEFAULT_CLIENT_PORT))));
//			botEnvironment.addClientAddress(cAddr);
//
//			InetSocketAddress sAddr = new InetSocketAddress(props.getProperty(
//					"server." + i + ".ip", DEFAULT_SERVER_IP),
//					Integer.parseInt(props.getProperty("server." + i + ".port",
//							String.valueOf(DEFAULT_SERVER_PORT))));
//			botEnvironment.addServerAddress(sAddr);
//		}
//
//		botEnvironment.setUpdateInterval(Long.parseLong(props.getProperty(
//				"script.update_interval", "100")));
//		botEnvironment.setNextBotConnectionInterval(Integer.parseInt(props
//				.getProperty("bot.next_connection_interval", "5")));
//
//		botEnvironment.setProxy(Boolean.parseBoolean(props.getProperty(
//				"client.proxy", "false")));
//
//		botEnvironment.setRawData(Boolean.parseBoolean(props.getProperty(
//				"bot.raw_data", "false")));
//		botEnvironment.setPortRangeMin(Integer.parseInt(props.getProperty(
//				"bot.port_range_min", String.valueOf(PORT_RANGE_MIN))));
//		botEnvironment.setPortRangeMax(Integer.parseInt(props.getProperty(
//				"bot.port_range_max", String.valueOf(PORT_RANGE_MAX))));
//		logger.info("Environment config loaded");
//	}
}
