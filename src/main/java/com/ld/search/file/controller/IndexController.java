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

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.ld.lucenex.thread.LdThreadPool;
import com.ld.search.file.App;
import com.ld.search.file.core.DataGrab;
import com.ld.search.file.core.DataModel;
import com.ld.search.file.core.TableView;
import com.ld.search.file.lucene.SearchFileService;
import com.sun.prism.paint.Paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

/**
 * @ClassName: IndexController
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午2:08:47
 */
public class IndexController {

	@FXML
	TextField fileName;
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
	String type="all";


	@FXML
	JFXButton all;//全部
	@FXML
	JFXButton programing;//编程
	@FXML
	JFXButton word;//文档
	@FXML
	JFXButton video;//视频
	@FXML
	JFXButton music;//音乐
	@FXML
	JFXButton image;//图片
	@FXML
	JFXButton Archive;//压缩包

	static List<JFXButton> types = new ArrayList<>();



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
		List<DataModel> list = new SearchFileService("search").searchFile(type, fileName.getText());
		if(list == null || list.isEmpty()) {
			//JFXDialog
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setBody(new Label("没有找到您想要的数据。。。。。"));
			JFXAlert<Void> alert = new JFXAlert<>(App.stage);
			alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
			alert.setContent(layout);
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.show();
		}else {
			data.getChildren().add(new TableView().cleateTable(list));
		}
	}

	/**
	 * @Title: updateIndex
	 * @Description: 更新索引
	 * @return: void
	 */
	public void updateIndex() {
		LdThreadPool.build().get().execute(()->new DataGrab().create());
	}

	public void exit() {
		System.exit(0);
	}

	/**
	 * 类型监听
	 * @Title: typeMonitor
	 * @Description: TODO
	 * @return: void
	 */
	public void typeMonitor() {
		types.add(all);
		types.add(programing);
		types.add(word);
		types.add(video);
		types.add(music);
		types.add(image);
		types.add(Archive);
		for (JFXButton button : types) {
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					JFXButton but = (JFXButton) event.getSource();
					String text = but.getText();
					switch (text) {
					case "全部":
						type="all";
						break;
					case "编程":
						type="programing";
						break;
					case "文档":
						type="word";
						break;
					case "视频":
						type="video";
						break;
					case "音乐":
						type="music";
						break;
					case "图片":
						type="image";
						break;
					case "压缩":
						type="Archive";
						break;
					default:
						type="all";
						break;
					}
					switBack(text);
					try {
						searchName();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			});
		}
	
	}

	/**
	 * @Title: switBack
	 * @Description: 切换类型
	 * @return: void
	 */
	public void switBack(String text) {
		for (JFXButton button : types) {
			if(button.getText().equals(text)) {//选中
				button.setStyle("-fx-background-color: #00BFFF");
			}else {
				button.setStyle("-fx-background-color: #F5F5F5");
			}
		}
	}

}
