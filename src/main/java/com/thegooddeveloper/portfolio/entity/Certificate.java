package com.thegooddeveloper.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="certificate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long certificateId;
  
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnore
  private UserInfo userInfo;

  @ManyToOne
  @JoinColumn(name="certificate_category", nullable=false)
  private Category certificateCategory;

  @Column(name = "image_link")
  private String imageLink;

  // @Column(nullable=false, columnDefinition = "MEDIUMBLOB")
  // @Lob
  // @Column( columnDefinition = "MEDIUMBLOB")
  // private byte[] image;
  
  @Column(nullable=false, columnDefinition="TEXT")
  private String verificationLink;

  @Column(nullable=false,length=1)
  private String isMajor; 

}
