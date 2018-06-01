/**  
 * Copyright © 2018LD. All rights reserved.
 *
 * @Title: SearchFileService.java
 * @Prject: searchFile
 * @Package: com.ld.search.file.lucene
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月31日 上午10:00:36
 * @version: V1.0  
 */
package com.ld.search.file.lucene;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.TermQuery;

import com.ld.lucenex.core.LuceneX;
import com.ld.lucenex.service.BasisService;
import com.ld.search.file.core.DataModel;

/**
 * @ClassName: SearchFileService
 * @Description: TODO
 * @author: Myzhang  
 * @date: 2018年5月31日 上午10:00:36
 */
public class SearchFileService extends BasisService{

	static final String Archive = "rar,zip,cab,iso,jar,ace,7z,tar,gz,arj,lzh,z,bz2";
	static final String programing = "java,class,xml,html,css,js,jsp,php,yml";
	static final String word = "txt,docx,xlsx,rtf,jnt,pdf";
	static final String video = "mp4,3gp,avi,mkv,wmv,mpg,vob,flv,swf,mov,xv,rmvb";
	static final String music = "cd,wave,aiff,mpeg,mp3,mpeg-4,midi,wma,realaudio,vqf,oggvorbis,amr,ape,flac,aac";
	static final String image = "bmp,jpg,png,tiff,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,wmf,webp";

	public SearchFileService() {
	}
	public SearchFileService(String dataKey) {
		super(dataKey);
	}
	//以上 构造函数 必须写

	public List<DataModel> searchFile(String type,String fileName) throws IOException{
		if(StringUtils.isEmpty(fileName)) {
			return null;
		}
		FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("name_str", fileName));//模糊
		TermQuery txtQuery = new TermQuery(new Term("name_txt", fileName));//分词

		BooleanClause fuzzyQ = new BooleanClause(fuzzyQuery, BooleanClause.Occur.SHOULD);//模糊查询
		BooleanClause txtQ = new BooleanClause(txtQuery, BooleanClause.Occur.SHOULD);//分词查询

		Builder builder = new BooleanQuery.Builder();
		builder.add(fuzzyQ).add(txtQ);
		if(StringUtils.isNotEmpty(type)) {
			switch (type) {
			case "all":
				break;
			case "programing":
				setDev(builder);
				break;
			case "word":
				setWord(builder);
				break;
			case "video":
				setVideo(builder);
				break;
			case "music":
				setMusic(builder);
				break;
			case "image":
				setImage(builder);
				break;
			case "Archive":
				break;
			default:
				break;
			}
		}
		List<Document> searchList = searchList(builder.build(), Integer.MAX_VALUE);
		if(searchList == null) {
			return null;
		}
		List<DataModel> list = searchList.stream().map(e->{
			DataModel dataModel = new DataModel();
			dataModel.setCode(Integer.parseInt(e.get("code")));
			String name="";
			if(e.get("name_str") != null) {
				name=e.get("name_str");
			}else if(e.get("name_txt") != null) {
				name=e.get("name_txt");
			}
			dataModel.setName(name);
			dataModel.setType(e.get("type"));
			dataModel.setPath(e.get("path"));
			dataModel.setSize(Long.valueOf(e.get("size")));
			dataModel.setCreateTime(new Date(Long.valueOf(e.get("createTime"))));
			return dataModel;
		}).collect(Collectors.toList());
		return list;
	}

	/**
	 * @Title: setDev
	 * @Description: 编程
	 * @param builder
	 * @return: void
	 */
	public void setDev(Builder builder) {
		BooleanQuery query = toBooleanQuery(programing);
		builder.add(query, BooleanClause.Occur.FILTER);
	}

	/**
	 * @Title: setImage
	 * @Description: 图片
	 * @param builder
	 * @return: void
	 */
	public void setImage(Builder builder) {
		BooleanQuery query = toBooleanQuery(image);
		builder.add(query, BooleanClause.Occur.FILTER);
	}
	/**
	 * @Title: setVideo
	 * @Description: 视频
	 * @param builder
	 * @return: void
	 */
	public void setVideo(Builder builder) {
		BooleanQuery query = toBooleanQuery(video);
		builder.add(query, BooleanClause.Occur.FILTER);
	}
	/**
	 * @Title: setMusic
	 * @Description: 音乐
	 * @param builder
	 * @return: void
	 */
	public void setMusic(Builder builder) {
		BooleanQuery query = toBooleanQuery(music);
		builder.add(query, BooleanClause.Occur.FILTER);
	}
	
	/**
	 * @Title: setWord
	 * @Description: 文档
	 * @param builder
	 * @return: void
	 */
	public void setWord(Builder builder) {
		BooleanQuery query = toBooleanQuery(word);
		builder.add(query, BooleanClause.Occur.FILTER);
	}
	/**
	 * @Title: setArchive
	 * @Description: 压缩包
	 * @param builder
	 * @return: void
	 */
	public void setArchive(Builder builder) {
		BooleanQuery query = toBooleanQuery(Archive);
		builder.add(query, BooleanClause.Occur.FILTER);
	}
	
	public BooleanQuery toBooleanQuery(String types) {
		Builder builder = new BooleanQuery.Builder();
		String[] split = types.split(",");
		for (String string : split) {
			builder.add(toBooleanClause(string));
		}
		return builder.build();
	}
	
	public BooleanClause toBooleanClause(String type) {
		return new BooleanClause(new TermQuery(new Term("type", type)), BooleanClause.Occur.SHOULD);
	}

	public static void main(String[] args) throws IOException {
		LuceneX.start(Config.class);
		//		new DataGrab().create();
		List<DataModel> searchFile = new SearchFileService("search").searchFile("image", "123");
		searchFile.forEach(e->System.out.println(e));
	}

}
