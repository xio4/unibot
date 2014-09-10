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
package com.xiovr.unibot.plugin;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.packet.Packet;

public interface CryptorPlugin {

	public void init(BotContext context);
	/**
	 * @param command If this null then createFirstCommand
	 * @return
	 */
	//ArrayBlockingQueue bq;
	public void execClientCommand(@NonNull Packet enc, @NonNull Packet dec);
	public void execServerCommand(@NonNull Packet enc, @NonNull Packet dec);
	// TODO Not implemented yet
	public void update();
	public Packet decryptFromServer(@NonNull Packet enc, @NonNull Packet dec);
	public Packet decryptFromClient(@NonNull Packet enc, @NonNull Packet dec);
	public Packet encryptToServer(@NonNull Packet dec,@NonNull  Packet enc);
	public Packet encryptToClient(@NonNull Packet dec,@NonNull  Packet enc);
	public void dispose();
	public void onConnected(int type);
	public void onDisconnected(int type);
	public void modifyServerPacket(Packet pck2);
	public void modifyClientPacket(Packet pck2);
}
