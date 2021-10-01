package io.github.herouu.entity;
import org.beetl.sql.annotation.entity.*;
/*
* 
* gen by beetlsql3 2021-10-01
*/

@Table(name="oxford")
public class Oxford implements java.io.Serializable {
	private String id ;
	private String wordName ;
	private String wordValue ;

	public Oxford() {
	}

	public String getId(){
		return  id;
	}
	public void setId(String id ){
		this.id = id;
	}
	public String getWordName(){
		return  wordName;
	}
	public void setWordName(String wordName ){
		this.wordName = wordName;
	}
	public String getWordValue(){
		return  wordValue;
	}
	public void setWordValue(String wordValue ){
		this.wordValue = wordValue;
	}

}
