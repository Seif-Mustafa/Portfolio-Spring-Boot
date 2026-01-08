package com.thegooddeveloper.portfolio.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnore
  private UserInfo userInfo;

  @Column(nullable = false)
  private String categoryName;

  @Column(nullable = false, length = 1)
  private String isHidden;
  // Relationships
  @OneToMany(mappedBy = "certificateCategory", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Certificate> certificates = new ArrayList<>();

  @OneToMany(mappedBy = "projectCategory", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Project> projects = new ArrayList<>();

}
