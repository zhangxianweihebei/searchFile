/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: TreeTableDemo.java
 * @Prject: searchFile
 * @Package: com.ld.search.file
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午5:02:41
 * @version: V1.0  
 */
package com.ld.search.file;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * @ClassName: TreeTableDemo
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午5:02:41
 */
public class TreeTableDemo extends Application {


    private static final String COMPUTER_DEPARTMENT = "Computer Department";
    private static final String SALES_DEPARTMENT = "Sales Department";
    private static final String IT_DEPARTMENT = "IT Department";
    private static final String HR_DEPARTMENT = "HR Department";

    @Override
    public void start(Stage primaryStage) throws Exception {

        JFXTreeTableColumn<User, String> deptColumn = new JFXTreeTableColumn<>("Department");
        deptColumn.setPrefWidth(150);
        deptColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) -> {
            if (deptColumn.validateValue(param)) {
                return param.getValue().getValue().department;
            } else {
                return deptColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<User, String> empColumn = new JFXTreeTableColumn<>("Employee");
        empColumn.setPrefWidth(150);
        empColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) -> {
            if (empColumn.validateValue(param)) {
                return param.getValue().getValue().getUserName();
            } else {
                return empColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<User, String> ageColumn = new JFXTreeTableColumn<>("Age");
        ageColumn.setPrefWidth(150);
        ageColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) -> {
            if (ageColumn.validateValue(param)) {
                return param.getValue().getValue().age;
            } else {
                return ageColumn.getComputedValue(param);
            }
        });


        // data
        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User(COMPUTER_DEPARTMENT, "23", "CD 1"));
        users.add(new User(SALES_DEPARTMENT, "22", "Employee 1"));
        users.add(new User(SALES_DEPARTMENT, "24", "Employee 2"));
        users.add(new User(SALES_DEPARTMENT, "25", "Employee 4"));
        users.add(new User(SALES_DEPARTMENT, "27", "Employee 5"));
        users.add(new User(IT_DEPARTMENT, "42", "ID 2"));
        users.add(new User(HR_DEPARTMENT, "21", "HR 1"));
        users.add(new User(HR_DEPARTMENT, "28", "HR 2"));

        // build tree
        final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);

        JFXTreeTableView<User> treeView = new JFXTreeTableView<>(root);
        treeView.setShowRoot(false);
        treeView.setEditable(true);
        treeView.getColumns().setAll(deptColumn, ageColumn, empColumn);

        FlowPane main = new FlowPane();
        main.setPadding(new Insets(10));
        main.getChildren().add(treeView);
        
        Scene scene = new Scene(main, 475, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    class User extends RecursiveTreeObject<User> {

        final StringProperty userName;
        final StringProperty age;
        final StringProperty department;
        
        /**
		 * @return userName
		 */
		public StringProperty getUserName() {
			return userName;
		}

        User(String department, String age, String userName) {
            this.department = new SimpleStringProperty(department);
            this.userName = new SimpleStringProperty(userName);
            this.age = new SimpleStringProperty(age);
        }

    }


}
