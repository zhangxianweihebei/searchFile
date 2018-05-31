package com.ld.search.file.core;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import com.ld.lucenex.service.BasisService;
import com.ld.lucenex.thread.LdThreadPool;
import com.ld.lucenex.thread.future.LdFuture;
import com.ld.lucenex.thread.future.LdFutureListener;
import com.ld.lucenex.thread.future.LdThreadPoolFactory;


/**
 * 数据抓取
 * @author Administrator
 *
 */
public class DataGrab {


	LdThreadPoolFactory poolFactory = LdThreadPool.build().get();
	/**
	 * 1小时内必须扫描完
	 */
	public void create(){
		LdFuture<List<DataModel>> ldFuture = poolFactory.submit(new Callable<List<DataModel>>() {
			@Override
			public List<DataModel> call() throws Exception {
				List<DataModel> datalist = new ArrayList<>();
				List<File> fileList = new ArrayList<>();
				File[] roots = File.listRoots();
				for (int i =0; i < roots.length; i++) {
					getListFile(new File("D:\\ceshi"), fileList);
//					getListFile(roots[1], fileList);
					break;
				}
				int size = fileList.size();
				CountDownLatch countDownLatch = new CountDownLatch(size);
				for (int j = 0; j < size; j++) {
					File file = fileList.get(j);
					poolFactory.execute(new Runnable() {
						@Override
						public void run() {
							DataModel dataModel = new DataModel();
							dataModel.setCode(file.hashCode());
							dataModel.setName(file.getName());
							dataModel.setPath(file.getPath());
							dataModel.setSize(file.length());
							String name = dataModel.getName();
							String[] split = name.split("\\.");
							if(split.length > 1) {
								dataModel.setType(name.substring(name.lastIndexOf(".") + 1));
							}
							dataModel.setCreateTime(new Date(file.lastModified()));
							datalist.add(dataModel);
							countDownLatch.countDown();
						}
					});
				}
				countDownLatch.await();
				return datalist;
			}
		});
		ldFuture.addListener(new LdFutureListener<List<DataModel>>() {
			
			@Override
			public void success(List<DataModel> v) {
				try {
					new BasisService("search").addIndex(v);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void error(Throwable throwable) {
				throwable.printStackTrace();
			}
		});
	}

	public void getListFile(File file, List<File> list) {
		//文件不存在
		if(!file.exists())
			return;
		if(file.isDirectory()) {//是目录
			File[] listFiles = file.listFiles();
			if(listFiles == null || listFiles.length <= 0) {
				return;
			}
			for(int i=0;i<listFiles.length;i++) {
				getListFile(listFiles[i], list);
			}
			
		}else {//是文件
			list.add(file);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("开始扫描");
		DataGrab grab = new DataGrab();
		grab.create();
		System.out.println("后台扫描"+new Date());
	}
}
