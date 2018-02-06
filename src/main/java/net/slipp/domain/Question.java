package net.slipp.domain;

<<<<<<< HEAD
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
=======
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
>>>>>>> new

@Entity
public class Question {
	@Id
	@GeneratedValue
	private Long id;

	//@Column(nullable = false, length = 20)
<<<<<<< HEAD
	private String writer;
	private String title;
	private String contents;
	
=======
	
	
	//Question에서 User와 관계를 맺을수 있도록
	
	//private String writer;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name="fk_question_writer"))
	private User writer;
	
	private String title;
	
	@Lob
	private String contents;
	
	//java 8 부터 새로운 api 추가
	private LocalDateTime createDate;
	
	
	@OneToMany(mappedBy="question")
	@OrderBy("id ASC")
	private List<Answer> answers;
	
>>>>>>> new
	
	//jap에서는 디폴트 생성자를 만들어 주어야 한다. 인자를 받는 생성자 外
	public Question() {}
	
	
	
<<<<<<< HEAD
	public Question(String writer, String title, String contents) {
=======
	public Question(User writer, String title, String contents) {
>>>>>>> new
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
<<<<<<< HEAD
	}


	
=======
		this.createDate = LocalDateTime.now();
	}


	public String getFormattedCreateDate() {
		if(createDate == null) {
			return "";
		}
		
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}



	public void update(String title, String contents) {
		this.title =title;
		this.contents = contents;
		
	}



	public boolean isSameWriter(User loginUser) {
System.out.println("wriger : " + this.writer);		
		//equls는 인스턴스는 다르지만 가지고 있는 값이 같은때 같은 놈으로 인식해 주는 것인데
		// 오버라이드를 해주어야 한다
		return this.writer.equals(loginUser);
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	//hash code와 equals를 쌍으로 구현해 준다
>>>>>>> new
	
	
	
}
