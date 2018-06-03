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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.ld.search.file.core.DataGrab;
import com.ld.search.file.core.DataModel;
import com.ld.search.file.core.SceneFactory;
import com.ld.search.file.core.TableView;
import com.ld.search.file.lucene.SearchFileService;
import com.ld.search.file.util.CalculateUtils;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;

/**
 * @ClassName: IndexController
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午2:08:47
 */
public class IndexController {
	static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");

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
	@FXML
	JFXButton directory;//目录

	static List<JFXButton> types = new ArrayList<>();
	
	
	@FXML
	Pane showFile;
	@FXML
	JFXTextField jFileName;
	@FXML
	JFXTextField jFilePath;
	@FXML
	JFXTextField jFileType;
	@FXML
	JFXTextField jFileSize;
	@FXML
	JFXTextField jFiletime;

	@FXML
	JFXSlider schedule;
	
	@FXML
	Text lodding;
	
	public void setMsg(String v) {
		lodding.setText(v);
	}


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
		if(list == null) {
			list = new ArrayList<>();
			//JFXDialog
//			JFXDialogLayout layout = new JFXDialogLayout();
//			layout.setBody(new Label("没有找到您想要的数据。。。。。"));
//			JFXAlert<Void> alert = new JFXAlert<>(App.stage);
//			alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
//			alert.setContent(layout);
//			alert.initModality(Modality.APPLICATION_MODAL);
//			alert.show();
		}
		data.getChildren().add(new TableView().cleateTable(list));
	}

	/**
	 * @Title: updateIndex
	 * @Description: 更新索引
	 * @return: void
	 * @throws IOException 
	 */
	public void updateIndex() throws IOException {
		SceneFactory.build().switIndex("Lodding");
		new Thread(()-> {
			try {
				new DataGrab().create();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}).start();;
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
		types.add(directory);
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
					case "目录":
						type="directory";
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
		fileName.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("---->"+fileName.getText());
				try {
					searchName();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		});
		jFileName.setEditable(false);
		jFilePath.setEditable(false);
		jFileType.setEditable(false);
		jFileSize.setEditable(false);
		jFiletime.setEditable(false);
		ImageView imageView = new ImageView();
		Image image = new Image(SearchFileService.class.getResourceAsStream("/com/ld/search/file/controller/image/unknown.jpg"));
		imageView.setImage(image);
		imageView.setFitWidth(225.0);
		imageView.setFitHeight(225.0);
		showFile.getChildren().add(imageView);
	
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
	
	public void setFileView(DataModel data) {
		//showFile
		ImageView imageView = new ImageView();
		Image image = new Image(SearchFileService.getImageUrl(data));
		imageView.setImage(image);
		imageView.setFitWidth(225.0);
		imageView.setFitHeight(225.0);
		showFile.getChildren().remove(0);
		showFile.getChildren().add(imageView);
		jFileName.setText(data.getName());
		jFilePath.setText(data.getPath());
		jFileType.setText(data.getType());
		long s = data.getSize();
		if(s < 1024) {
			jFileSize.setText(s+"KB");
		}else if(s < 1024*1024) {
			Double d1 = Double.valueOf(s);
			Double d2 = Double.valueOf(1024*1024);
			jFileSize.setText(CalculateUtils.div(d1, d2, 2)+"MB");
		}else {
			Double d1 = Double.valueOf(s);
			Double d2 = Double.valueOf(1024*1024*1024);
			jFileSize.setText(CalculateUtils.div(d1, d2, 2)+"GB");
		}
		jFiletime.setText(sdf.format(data.getCreateTime()));
	}
	
	public void openFile() {
		JFXAlert<Void> alert = new JFXAlert<>(SceneFactory.build().getStage());
		Platform.runLater(()->{
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setBody(new Label("Lodding......"));
			alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
			alert.setContent(layout);
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.show();
		});
		try {
			if(StringUtils.isNotEmpty(jFilePath.getText())) {
				Desktop.getDesktop().open(new File(jFilePath.getText()));
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			alert.hide();
		}
	}
	
	public void schedule(int i) {
		int total = SceneFactory.build().getTotalSize();
		Double d2 = Double.valueOf(total);
		Double d1 = Double.valueOf(i);
		double div = CalculateUtils.div(d1, d2, 2);
		double mul = CalculateUtils.mul(div, 100);
		schedule.setValue(mul);
	}

}
