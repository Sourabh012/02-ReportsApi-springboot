package com.accenture.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Entity
@Data
@Table(name="ELIGIBILITY_DETAILS")
public class EligibilityDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ELIG_ID")
	private Integer eligId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="MOBILE")
	private Long mobile;
	
	@Column(name="email")
	private String email;
	
	@Column(name="gender")
	private Character gender;
	
	@Column(name="ssn")
	private Long ssn;
	
	@Column(name="Plan_Name")
	private String planName;
	
	@Column(name="Plan_Status")
	private String planStatus;
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	private LocalDate planStartDate;
		
    @JsonFormat(pattern = "yyyy/MM/dd")
	private LocalDate planEndDate;
	
    @Column(name="Create_Date")
	private LocalDate createDate;
	
	@Column(name="Updated_Data")
	private LocalDate updateDate;
	
	@Column(name="Create_By")
	private String createBY;
	
	@Column(name="Update_By")
	private String updateBy;
}
