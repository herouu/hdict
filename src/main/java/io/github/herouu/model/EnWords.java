package io.github.herouu.model;

import lombok.ToString;
import org.beetl.sql.annotation.entity.AssignID;
import org.beetl.sql.annotation.entity.Table;
/*
* 
* gen by beetlsql3 2021-09-22
*/

@Table(name="en_words")
@ToString
public class EnWords implements java.io.Serializable {
	@AssignID
	private String word ;
	private String translation ;

	public EnWords() {
	}

	public String getWord(){
		return  word;
	}
	public void setWord(String word ){
		this.word = word;
	}
	public String getTranslation(){
		return  translation;
	}
	public void setTranslation(String translation ){
		this.translation = translation;
	}

}
