/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: IndexController.java
 * @Prject: searchFile
 * @Package: com.ld.search.file.controller
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午2:08:47
 * @version: V1.0  
 */
package com.ld.search.file.controller;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.ld.lucenex.service.BasisService;
import com.ld.search.file.core.DataGrab;
import com.ld.search.file.core.TableView;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

/**
 * @ClassName: IndexController
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午2:08:47
 */
public class IndexController {

	@FXML
	JFXTextField fileName;
	@FXML
	JFXButton search;
	@FXML
	JFXButton update;
	@FXML
	JFXListView<String> dataList;

	@FXML
	Pane data;


	@FXML
	Pane handler;


	/**
	 * @return handler
	 */
	public Pane getHandler() {
		return handler;
	}

	/**
	 * @Title: searchName
	 * @Description: 检索
	 * @return: void
	 * @throws IOException 
	 */
	public void searchName() throws IOException {
		BasisService basisService = new BasisService("search");
		List<Document> search_1 = basisService.searchList(new TermQuery(new Term("name_txt","java")),Integer.MAX_VALUE);
		data.getChildren().add(new TableView().cleateTable(search_1));
		//		ObservableList<String> list = FXCollections.observableArrayList();
		//		BasisService basisService = new BasisService("search");
		//		List<Document> search_1 = basisService.searchList(new TermQuery(new Term("name_str",fileName.getText())),Integer.MAX_VALUE);
		//		List<Document> search_2 = basisService.searchList(new TermQuery(new Term("name_txt",fileName.getText())),Integer.MAX_VALUE);
		//
		//		if(search_1 == null && search_2 == null) {
		//			list.add("没找着到->"+fileName.getText()+"相关");
		//		}
		//		if(search_1 != null) {
		//			search_1.forEach(e->list.add(e.get("name_str")+"         --->   "+e.get("path")));
		//		}
		//		if(search_2 != null) {
		//			search_2.forEach(e->list.add(e.get("name_txt")+"         --->   "+e.get("path")));
		//		}
		//		dataList.setItems(list);
		//		System.out.println("查询");
	}

	/**
	 * @Title: updateIndex
	 * @Description: 更新索引
	 * @return: void
	 */
	public void updateIndex() {
		new DataGrab().create();
		System.out.println("更新");
	}

	public void exit() {
		System.exit(0);
	}

}
