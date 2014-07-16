package com.xiovr.e5bot.data.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="itemname")
public class ItemName extends AbstractModel<Long> implements IdNameEntity {
	@Id
	@Column(name="id")
	@GeneratedValue(generator="items_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name="items_seq", sequenceName="items_seq", allocationSize=1)
	private Long id;
	
	@Column(name="name")
	private String name;
	@Column(name="add_name")
	private String addName;
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
		return addName;
	}

	@Override
	public void setVal(String val) {
		this.addName = val;
		
	}
}
