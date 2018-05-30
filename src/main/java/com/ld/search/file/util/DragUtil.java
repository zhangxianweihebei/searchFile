/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: DragUtil.java
 * @Prject: searchFile
 * @Package: com.ld.search.file.util
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午4:34:36
 * @version: V1.0  
 */
package com.ld.search.file.util;

import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * @ClassName: DragUtil
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月30日 下午4:34:36
 */
public class DragUtil {
	public static void addDragListener(Stage stage,Node root) {
        new DragListener(stage).enableDrag(root);
    }
}
