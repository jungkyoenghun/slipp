package net.slipp.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Question {
	@Id
	@GeneratedValue
	private Long id;

	//@Column(nullable = false, length = 20)
	
	
	//Question에서 User와 관계를 맺을수 있도록
	
	//private String writer;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name="fk_question_writer"))
	private User writer;
	
	private String title;
	
	private String contents;
	
	//java 8 부터 새로운 api 추가
	private LocalDateTime createDate;
	
	
	//jap에서는 디폴트 생성자를 만들어 주어야 한다. 인자를 받는 생성자 外
	public Question() {}
	
	
	
	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}


	public String getFormattedCreateDate() {
		if(createDate == null) {
			return "";
		}
		
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}
	
	
	
}
