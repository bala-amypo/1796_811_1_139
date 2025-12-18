@Entity
public class CourseContentTopic {
 @Id @GeneratedValue
 private Long id;
 private String topicName;
 private Double weightPercentage;

 @ManyToOne
 private Course course;

 public Long getId(){return id;}
 public void setId(Long id){this.id=id;}
 public String getTopicName(){return topicName;}
 public void setTopicName(String t){this.topicName=t;}
 public Double getWeightPercentage(){return weightPercentage;}
 public void setWeightPercentage(Double w){this.weightPercentage=w;}
 public Course getCourse(){return course;}
 public void setCourse(Course c){this.course=c;}
}