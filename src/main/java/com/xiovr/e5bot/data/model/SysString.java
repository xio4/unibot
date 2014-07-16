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
@Table(name="sysstring")
public class SysString extends AbstractModel<Long> implements IdNameEntity {
	@Id
	@Column(name="id")
	@GeneratedValue(generator="sysstring_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name="sysstring_seq", sequenceName="sysmsgs_seq", allocationSize=1)
	private Long id;
	
	@Column(name="name")
	@Type(type="text")
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
