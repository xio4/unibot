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
package com.xiovr.unibot.data.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="races")
public class Races extends AbstractModel<Long> implements IdNameEntity {
	@Id
	@Column(name="id")
	@GeneratedValue(generator="races_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name="races_seq", sequenceName="races_seq", allocationSize=1)
	private Long id;
	
	@Column(name="name")
	private String name;

	@Column(name="description")
	@Type(type="text")
	private String desc;

	@Override
	public Long getId()
	{
		return id;
	}
	
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setId(Long id)
	{
		this.id = id;
	}
	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public void setDesc(String desc) {
		this.desc = desc;
		
	}

	@Override
	public String getVal() {
		// Nothing
		return null;
	}

	@Override
	public void setVal(String val) {
		// Nothing
	}
}
