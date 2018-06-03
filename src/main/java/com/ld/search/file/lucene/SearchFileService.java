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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
	
	public static InputStream getImageUrl(DataModel data) {
		String type = data.getType();
		InputStream imageUrl = null;
		if(type == null) {
			imageUrl = SearchFileService.class.getResourceAsStream("/com/ld/search/file/controller/image/unknown.jpg");
			return imageUrl;
		}
		if(Archive.indexOf(type) != -1) {
			imageUrl = SearchFileService.class.getResourceAsStream("/com/ld/search/file/controller/image/Archive.jpg");
		}else if(programing.indexOf(type) != -1) {
			imageUrl = SearchFileService.class.getResourceAsStream("/com/ld/search/file/controller/image/programing.jpg");
		}else if(word.indexOf(type) != -1) {
			imageUrl = SearchFileService.class.getResourceAsStream("/com/ld/search/file/controller/image/word.jpg");
		}else if(video.indexOf(type) != -1) {
			imageUrl = SearchFileService.class.getResourceAsStream("/com/ld/search/file/controller/image/video.jpg");
		}else if(music.indexOf(type) != -1) {
			imageUrl = SearchFileService.class.getResourceAsStream("/com/ld/search/file/controller/image/music.png");
		}else if("目录".indexOf(type) != -1) {
			imageUrl = SearchFileService.class.getResourceAsStream("/com/ld/search/file/controller/image/directory.png");
		}else if(image.indexOf(type) != -1) {
			try {
				imageUrl = new FileInputStream(new File(data.getPath()));
			} catch (FileNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else {
			imageUrl = SearchFileService.class.getResourceAsStream("/com/ld/search/file/controller/image/unknown.jpg");
		}
		return imageUrl;
	}
	//以上 构造函数 必须写

	public List<DataModel> searchFile(String type,String fileName) throws IOException{
		if(StringUtils.isEmpty(fileName)) {
			return null;
		}
		fileName = fileName.toLowerCase();
		FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("name_str", fileName));//模糊
		TermQuery txtQuery = new TermQuery(new Term("name_txt", fileName));//分词

		BooleanQuery booleanQuery = new BooleanQuery.Builder()
				.add(new BooleanClause(fuzzyQuery, BooleanClause.Occur.SHOULD))
				.add(new BooleanClause(txtQuery, BooleanClause.Occur.SHOULD)).build();

		Builder builder = new BooleanQuery.Builder();
		builder.add(booleanQuery,BooleanClause.Occur.MUST);
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
				setArchive(builder);
				break;
			case "directory":
				setDirectory(builder);
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

	private void setDirectory(Builder builder) {
		BooleanQuery query = toBooleanQuery("目录");
		builder.add(query, BooleanClause.Occur.FILTER);
		
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
