@Entity
public class TransferRule {
 @Id @GeneratedValue
 private Long id;

 @ManyToOne private University sourceUniversity;
 @ManyToOne private University targetUniversity;

 private Double minimumOverlapPercentage;
 private Integer creditHourTolerance = 0;
 private boolean active = true;

 public Long getId(){return id;} 
 public void setId(Long id){this.id=id;}
 public University getSourceUniversity(){return sourceUniversity;}
 public void setSourceUniversity(University u){this.sourceUniversity=u;}
 public University getTargetUniversity(){return targetUniversity;}
 public void setTargetUniversity(University u){this.targetUniversity=u;}
 public Double getMinimumOverlapPercentage(){return minimumOverlapPercentage;}
 public void setMinimumOverlapPercentage(Double m){this.minimumOverlapPercentage=m;}
 public Integer getCreditHourTolerance(){return creditHourTolerance;}
 public void setCreditHourTolerance(Integer c){this.creditHourTolerance=c;}
 public boolean isActive(){return active;}
 public void setActive(boolean a){this.active=a;}
}