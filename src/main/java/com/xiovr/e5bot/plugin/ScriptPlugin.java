package com.xiovr.e5bot.plugin;

/**
 * @author xio4
 * Base interface for java scripts
 */
public interface ScriptPlugin {
	/**
	 * Init script before start
	 * @param context is context to access with bot environment
	 */
	public void init(PluginContext context);
	/**
	 * @return update interval in millisecons. if -1 then update() method disabled. May be more then 50ms
	 */
	public int getUpdateInterval();
	/**
	 * Update bot logic
	 */
	public void update();
	/**
	 * Income package from server 
	 * @param deltaTime is time interval between two incomeS() invokes
	 * @return If packet is passthrought then true, else false
	 */
	public boolean incomeS(float deltaTime);
	/**
	 * Income package from client 
	 * @param deltaTime is time interval between two incomeC() invokes
	 * @return If packet is passthrought then true, else false
	 */
	public boolean incomeC(float deltaTime);
	/**
	 * Release script after stop one or bot
	 */
	public void dispose();

}
