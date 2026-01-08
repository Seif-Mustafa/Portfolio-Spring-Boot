package com.thegooddeveloper.portfolio.entity;

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
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(nullable = false)
  private String username;

  @Column(name="imageLink")
  private String imageLink;
  // @Lob
  // @Column(nullable = true, columnDefinition = "MEDIUMBLOB")
  // private byte[] userImage;

  @Column(nullable = false)
  private String jobTitle;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String summary;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String whatsappLink;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String linkedinLink;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String githubLink;

  // Relationships
  @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Certificate> certificates = new ArrayList<>();

  @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Project> projects = new ArrayList<>();

  @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Experience> experiences = new ArrayList<>();

  @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Category> categories = new ArrayList<>();

}
