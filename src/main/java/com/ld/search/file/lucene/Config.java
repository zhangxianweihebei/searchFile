/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: Config.java
 * @Prject: searchFile
 * @Package: com.ld.search.file.lucene
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午12:21:35
 * @version: V1.0  
 */
package com.ld.search.file.lucene;

import com.ld.lucenex.base.BaseConfig;
import com.ld.lucenex.config.Constants;
import com.ld.lucenex.config.LuceneXConfig;
import com.ld.search.file.core.DataModel;

/**
 * @ClassName: Config
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午12:21:35
 */
public class Config extends LuceneXConfig{

	
	@Override
	public void configConstant(Constants me) {
		me.setDefaultClass(DataModel.class);
		me.setDefaultDisk("d:/searchFile/");
	}

	@Override
	public void configLuceneX(BaseConfig me) {
		me.add("search");
	}

}
