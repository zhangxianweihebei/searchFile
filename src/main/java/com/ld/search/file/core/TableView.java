/**  
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

import java.text.SimpleDateFormat;
import java.util.List;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

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
	static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");

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
		path.setPrefWidth(200);
		path.setCellValueFactory((TreeTableColumn.CellDataFeatures<DataModel, String> param) -> {
			if (path.validateValue(param)) {
				return new SimpleStringProperty(param.getValue().getValue().getPath()+"");
			} else {
				return path.getComputedValue(param);
			}
		});
		type.setPrefWidth(50);
		type.setCellValueFactory((TreeTableColumn.CellDataFeatures<DataModel, String> param) -> {
			if (type.validateValue(param)) {
				return new SimpleStringProperty(param.getValue().getValue().getType()+"");
			} else {
				return type.getComputedValue(param);
			}
		});
		size.setPrefWidth(50);
		size.setCellValueFactory((TreeTableColumn.CellDataFeatures<DataModel, String> param) -> {
			if (size.validateValue(param)) {
				long s = param.getValue().getValue().getSize();
				s=s/1024;
				return new SimpleStringProperty(s+"KB");
			} else {
				return size.getComputedValue(param);
			}
		});
		createTime.setPrefWidth(100);
		createTime.setCellValueFactory((TreeTableColumn.CellDataFeatures<DataModel, String> param) -> {
			if (createTime.validateValue(param)) {
				return new SimpleStringProperty(sdf.format(param.getValue().getValue().getCreateTime()));

			} else {
				return createTime.getComputedValue(param);
			}
		});

	}

	public JFXTreeTableView<DataModel> cleateTable(List<DataModel> dataList){
		ObservableList<DataModel> users = FXCollections.observableArrayList();
		dataList.forEach(e->users.add(e));
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
