package com.ld.search.file;

import java.io.File;
import java.net.URL;
import java.util.Date;

import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.thread.LdThreadPool;
import com.ld.search.file.controller.IndexController;
import com.ld.search.file.core.DataGrab;
import com.ld.search.file.core.SceneFactory;
import com.ld.search.file.lucene.Config;
import com.ld.search.file.util.DragUtil;

import javafx.application.Application;
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
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		SceneFactory factory = SceneFactory.build();
		factory.init(primaryStage);
		factory.initSystem();
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		SceneFactory.build().getParent("Ceshi").setVisible(true);
		Scene scene = new Scene(factory.getParent("Ceshi"));
		primaryStage.setScene(scene);
		primaryStage.show();
		LuceneX.start(Config.class);
		addMonitor(factory.getController("Ceshi"));
		initTotalSize();
	}
	
	public static void addMonitor(IndexController index) {
		index.typeMonitor();
		DragUtil.addDragListener(SceneFactory.build().getStage(),index.getHandler() );
	}
	
	public static void initTotalSize() {
		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			File file = roots[i];
			DataGrab.getFileTotal(file);
		}
	}

	public static void main( String[] args ){
		launch(args);
	}
}
