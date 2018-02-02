package net.slipp.domain;

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
	
	
	//jap에서는 디폴트 생성자를 만들어 주어야 한다. 인자를 받는 생성자 外
	public Question() {}
	
	
	
	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}


	
	
	
	
}
