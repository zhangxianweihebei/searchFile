/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: TableView.java
 * @Prject: searchFile
 * @Package: com.ld.search.file.core
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午5:44:12
 * @version: V1.0  
 */
package com.ld.search.file.core;

import java.util.Date;
import java.util.List;

import org.apache.lucene.document.Document;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.ld.search.file.controller.User;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

/**
 * @ClassName: TableView
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午5:44:12
 */
public class TableView {

	static JFXTreeTableColumn<DataModel, String> code = new JFXTreeTableColumn<>("编号");
	static JFXTreeTableColumn<DataModel, String> name = new JFXTreeTableColumn<>("文件名");
	static JFXTreeTableColumn<DataModel, String> path = new JFXTreeTableColumn<>("路径");
	static JFXTreeTableColumn<DataModel, String> type = new JFXTreeTableColumn<>("类型");
	static JFXTreeTableColumn<DataModel, String> size = new JFXTreeTableColumn<>("大小");
	static JFXTreeTableColumn<DataModel, String> createTime = new JFXTreeTableColumn<>("创建时间");

	static {
		code.setPrefWidth(100);
		code.setCellValueFactory((TreeTableColumn.CellDataFeatures<DataModel, String> param) -> {
			if (code.validateValue(param)) {
				return new SimpleStringProperty(param.getValue().getValue().getCode()+"");
			} else {
				return code.getComputedValue(param);
			}
		});
		name.setPrefWidth(190);
		name.setCellValueFactory((TreeTableColumn.CellDataFeatures<DataModel, String> param) -> {
			if (name.validateValue(param)) {
				return new SimpleStringProperty(param.getValue().getValue().getName()+"");
			} else {
				return name.getComputedValue(param);
			}
		});
		path.setPrefWidth(100);
		path.setCellValueFactory((TreeTableColumn.CellDataFeatures<DataModel, String> param) -> {
			if (path.validateValue(param)) {
				return new SimpleStringProperty(param.getValue().getValue().getPath()+"");
			} else {
				return path.getComputedValue(param);
			}
		});
		type.setPrefWidth(100);
		type.setCellValueFactory((TreeTableColumn.CellDataFeatures<DataModel, String> param) -> {
			if (type.validateValue(param)) {
				return new SimpleStringProperty(param.getValue().getValue().getType()+"");
			} else {
				return type.getComputedValue(param);
			}
		});
		size.setPrefWidth(100);
		size.setCellValueFactory((TreeTableColumn.CellDataFeatures<DataModel, String> param) -> {
			if (size.validateValue(param)) {
				return new SimpleStringProperty(param.getValue().getValue().getSize()+"");
			} else {
				return size.getComputedValue(param);
			}
		});
		createTime.setPrefWidth(100);
		createTime.setCellValueFactory((TreeTableColumn.CellDataFeatures<DataModel, String> param) -> {
			if (createTime.validateValue(param)) {
				return new SimpleStringProperty(param.getValue().getValue().getCreateTime()+"");
			} else {
				return createTime.getComputedValue(param);
			}
		});

	}

	public JFXTreeTableView<DataModel> cleateTable(List<Document> dataList){
		ObservableList<DataModel> users = FXCollections.observableArrayList();
		for (Document doc : dataList) {
			DataModel dataModel = new DataModel();
			dataModel.setCode(Integer.parseInt(doc.get("code")));
			dataModel.setName(doc.get("name_txt"));
			dataModel.setType(doc.get("type"));
			dataModel.setPath(doc.get("path"));
			dataModel.setSize(Long.valueOf(doc.get("size")));
			dataModel.setCreateTime(new Date());
			users.add(dataModel);
		}
		
		
		final TreeItem<DataModel> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
		JFXTreeTableView<DataModel> treeView = new JFXTreeTableView<>(root);
		treeView.setShowRoot(false);
		treeView.setEditable(true);
		treeView.setPrefWidth(605.0);
		treeView.setPrefHeight(380.0);
		treeView.getColumns().setAll(name,path,type,size,createTime);
		return treeView;
	}

}
