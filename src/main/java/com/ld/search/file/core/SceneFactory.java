package com.ld.search.file.core;

import java.util.HashMap;
import java.util.Map;

import com.ld.search.file.controller.IndexController;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneFactory {
	
	private Stage stage;
	private int totalSize;
	private static SceneFactory kit = new SceneFactory();
	private Map<String,IndexController> myController = new HashMap<>();
	private Map<String,Parent> myParent = new HashMap<>();

	public static SceneFactory build() {
		return kit;
	}

	
	public Stage getStage() {
		return stage;
	}
	public void init(Stage stage){
		this.stage = stage;
	}
	public IndexController getController(String key) {
		return myController.get(key);
	}
	public Parent getParent(String key) {
		return myParent.get(key);
	}
	public void setTotalSize(int k) {
		totalSize+=k;
	}
	public int getTotalSize() {
		return totalSize;
	}
	
	
	public void initSystem(){
		String[] fxml = new String[]{"Ceshi","Lodding"};
		for (String str : fxml) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ld/search/file/page/"+str+".fxml"));
				Pane root = (Pane)fxmlLoader.load();
				IndexController controller = fxmlLoader.getController();
				myController.put(str, controller);
				myParent.put(str, root);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void switIndex(String key) {
		Platform.runLater(()->{
			this.stage.getScene().getRoot().setVisible(false);
			this.stage.getScene().setRoot(getParent(key));
			this.stage.getScene().getRoot().setVisible(true);
		});
		
	}
}
