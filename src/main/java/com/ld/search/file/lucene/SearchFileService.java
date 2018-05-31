/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: SearchFileService.java
 * @Prject: searchFile
 * @Package: com.ld.search.file.lucene
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月31日 上午10:00:36
 * @version: V1.0  
 */
package com.ld.search.file.lucene;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.TermQuery;

import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.service.BasisService;
import com.ld.search.file.core.DataGrab;
import com.ld.search.file.core.DataModel;

/**
 * @ClassName: SearchFileService
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月31日 上午10:00:36
 */
public class SearchFileService extends BasisService{

	public SearchFileService() {
	}
	public SearchFileService(String dataKey) {
		super(dataKey);
	}
	//以上 构造函数 必须写

	public List<DataModel> searchFile(String type,String fileName) throws IOException{
		if(StringUtils.isEmpty(fileName)) {
			return null;
		}
		FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("name_str", fileName));//模糊
		TermQuery typeQuery = new TermQuery(new Term("type", type));
		TermQuery txtQuery = new TermQuery(new Term("name_txt", fileName));//分词

		BooleanClause fuzzyQ = new BooleanClause(fuzzyQuery, BooleanClause.Occur.SHOULD);//模糊查询
		BooleanClause txtQ = new BooleanClause(txtQuery, BooleanClause.Occur.SHOULD);//分词查询
		BooleanClause typeQ = new BooleanClause(typeQuery, BooleanClause.Occur.FILTER);//过滤类型

		Builder builder = new BooleanQuery.Builder();
		builder.add(fuzzyQ)
		.add(txtQ);
		if(StringUtils.isNotEmpty(type)) {
//			builder.add(typeQ);
			if(type.equals("dev")) {
				setDev(builder);
			}else if(type.equals("image")) {
				setImage(builder);
			}
		}
		List<Document> searchList = searchList(builder.build(), Integer.MAX_VALUE);
		if(searchList == null) {
			return null;
		}
		List<DataModel> list = searchList.stream().map(e->{
			DataModel dataModel = new DataModel();
			dataModel.setCode(Integer.parseInt(e.get("code")));
			String name="";
			if(e.get("name_str") != null) {
				name=e.get("name_str");
			}else if(e.get("name_txt") != null) {
				name=e.get("name_txt");
			}
			dataModel.setName(name);
			dataModel.setType(e.get("type"));
			dataModel.setPath(e.get("path"));
			dataModel.setSize(Long.valueOf(e.get("size")));
			dataModel.setCreateTime(new Date(Long.valueOf(e.get("createTime"))));
			return dataModel;
		}).collect(Collectors.toList());
		return list;
	}

	//开发类型 java
	public void setDev(Builder builder) {
		BooleanQuery build = new BooleanQuery.Builder()
		.add(new BooleanClause(new TermQuery(new Term("type", "java")), BooleanClause.Occur.SHOULD))
		.add(new BooleanClause(new TermQuery(new Term("type", "class")), BooleanClause.Occur.SHOULD))
		.add(new BooleanClause(new TermQuery(new Term("type", "xml")), BooleanClause.Occur.SHOULD))
		.add(new BooleanClause(new TermQuery(new Term("type", "fxml")), BooleanClause.Occur.SHOULD))
		.add(new BooleanClause(new TermQuery(new Term("type", "html")), BooleanClause.Occur.SHOULD)).build();
		builder.add(build, BooleanClause.Occur.FILTER);
	}

	//图片类型 java
	public void setImage(Builder builder) {
		BooleanQuery build = new BooleanQuery.Builder()
		.add(new BooleanClause(new TermQuery(new Term("type", "jpg")), BooleanClause.Occur.SHOULD))
		.add(new BooleanClause(new TermQuery(new Term("type", "png")), BooleanClause.Occur.SHOULD)).build();
		builder.add(build, BooleanClause.Occur.FILTER);
	}
	//视频类型 java
	public void setvideo(Builder builder) {
		BooleanQuery build = new BooleanQuery.Builder()
				.add(new BooleanClause(new TermQuery(new Term("type", "mpg")), BooleanClause.Occur.SHOULD))
				.add(new BooleanClause(new TermQuery(new Term("type", "avi")), BooleanClause.Occur.SHOULD)).build();
				builder.add(build, BooleanClause.Occur.FILTER);
	}
	
	public static void main(String[] args) throws IOException {
		LuceneX.start(Config.class);
//		new DataGrab().create();
		List<DataModel> searchFile = new SearchFileService("search").searchFile("image", "123");
		searchFile.forEach(e->System.out.println(e));
	}

}
