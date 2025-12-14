package com.thegooddeveloper.portfolio.entity;

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
@Table(name="project_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectImage {


  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long projectImageId;

  @ManyToOne
  @JoinColumn(name="project_id", nullable=false)
  private Project project;

  @Lob
  @Column(nullable=false, columnDefinition = "MEDIUMBLOB")
  private byte[] image;
}
