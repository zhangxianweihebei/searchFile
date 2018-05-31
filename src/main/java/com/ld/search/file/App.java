package com.ld.search.file;

import java.net.URL;

import com.ld.lucenex.core.LuceneX;
import com.ld.search.file.controller.IndexController;
import com.ld.search.file.lucene.Config;
import com.ld.search.file.util.DragUtil;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Hello world!
 *
 */
public class App extends Application{
	
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		URL url = getClass().getResource("page/Ceshi.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Pane root = (Pane)fxmlLoader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		LuceneX.start(Config.class);
		IndexController index = fxmlLoader.getController();
//		index.searchName();
		DragUtil.addDragListener(primaryStage,index.getHandler() );
	}

	public static void main( String[] args ){
		launch(args);
	}
}
