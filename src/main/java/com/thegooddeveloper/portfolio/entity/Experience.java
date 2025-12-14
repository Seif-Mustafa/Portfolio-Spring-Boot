package com.thegooddeveloper.portfolio.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="experience")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experience {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long experianceId;

  @ManyToOne
  @JoinColumn(name="user_id", nullable=false)
  @JsonIgnore
  private UserInfo userInfo;

  @Column(nullable=false)
  private String title;

  @Column(name="date_from", nullable=false)
  private LocalDate dateFrom;

  @Column(name="date_to", nullable=false)
  private LocalDate dateTo;

  @Column(name="experiance_detail", nullable = false, columnDefinition="TEXT")
  private String experianceDetail;


}
