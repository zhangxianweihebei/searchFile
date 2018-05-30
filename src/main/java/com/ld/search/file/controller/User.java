/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: User.java
 * @Prject: searchFile
 * @Package: com.ld.search.file.controller
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午4:56:52
 * @version: V1.0  
 */
package com.ld.search.file.controller;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @ClassName: User
 * @Description: TODO
 * @author: Myzhang  
 * @param <T>
 * @date: 2018年5月30日 下午4:56:52
 */
public class User extends RecursiveTreeObject<User>{
	public final StringProperty name;
	public final StringProperty age;
	
	
	/**
	 * @Title:User
	 * @Description:TODO
	 */
	public User(String name,int age) {
		// TODO 自动生成的构造函数存根
		this.name = new SimpleStringProperty(name);
		this.age = new SimpleStringProperty(name);
	}


	/**
	 * @return name
	 */
	public StringProperty getName() {
		return name;
	}




	/**
	 * @return age
	 */
	public StringProperty getAge() {
		return age;
	}



}
