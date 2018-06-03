package com.ld.search.file.core;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ld.search.file.lucene.SearchFileService;


/**
 * 数据抓取
 * @author Administrator
 *
 */
public class DataGrab {
	
	public static void getFileTotal(File file) {
		if(file.getPath().equals(System.getenv("windir"))) return ;
		File[] listFiles = file.listFiles();
		if(listFiles == null) return;
		SceneFactory.build().setTotalSize(listFiles.length);
		for(int i=0;i<listFiles.length;i++) {
			getFileTotal(listFiles[i]);
		}
	}
	
	/**
	 * 1小时内必须扫描完
	 */
	static Integer k=0;
	public void create() throws IOException{
		k=0;
		SceneFactory.build().getController("Lodding").setMsg("初始化中。。。");
		SearchFileService service = new SearchFileService("search");
		try {
			service.deleteAll();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		
		File[] roots = File.listRoots();
		for (int i =0; i < roots.length; i++) {
			File file = roots[i];
			List<DataModel> datalist = new ArrayList<>();
			SceneFactory.build().getController("Lodding").setMsg("正在扫描-->"+file.getPath());
			getListFile(file, datalist);
			try {
				SceneFactory.build().getController("Lodding").setMsg("正在提交-->"+file.getPath());
				service.addIndex(datalist);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		SceneFactory.build().switIndex("Ceshi");
	}

	public void getListFile(File file,List<DataModel> dataList) {
		if(file.getPath().equals(System.getenv("windir"))) return ;
		File[] listFiles = file.listFiles();
		if(listFiles == null) return;
		for(int i=0;i<listFiles.length;i++) {
			File f = listFiles[i];
			DataModel dataModel = new DataModel();
			dataModel.setCode(f.hashCode());
			dataModel.setName(f.getName());
			dataModel.setPath(f.getPath());
			dataModel.setSize(f.length());
			if(f.isDirectory()) {//目录
				dataModel.setType("目录");
			}else {//文件
				String name = dataModel.getName();
				String[] split = name.split("\\.");
				if(split.length > 1) {
					dataModel.setType(name.substring(name.lastIndexOf(".") + 1));
				}
			}
			dataModel.setCreateTime(new Date(f.lastModified()));
			dataList.add(dataModel);
			++k;
			SceneFactory.build().getController("Lodding").schedule(k);
			getListFile(f,dataList);
		}
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("开始扫描");
		DataGrab grab = new DataGrab();
		grab.create();
		System.out.println("后台扫描"+new Date());
	}
}
