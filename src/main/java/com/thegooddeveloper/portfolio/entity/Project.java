package com.thegooddeveloper.portfolio.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long projectId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnore
  private UserInfo userInfo;

  @ManyToOne
  @JoinColumn(name = "project_category", nullable = false)
  private Category projectCategory;

  @Column(nullable = false)
  private String projectName;

  @Column
  private Integer projectPriority;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String projectDescription;

  @Column
  private LocalDate developmentDate;

  @Column( columnDefinition = "TEXT")
  private String githubLink;

  @Column( columnDefinition = "TEXT")
  private String playstoreLink;

  @Column( columnDefinition = "TEXT")
  private String deploymentLink;

  @Column( columnDefinition = "TEXT")
  private String videoLink;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProjectImage> images = new ArrayList<>();

}
