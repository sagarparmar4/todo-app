package com.deloitte.demo.entities;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	private Date createdDate;

//	@CreatedBy
//	@ManyToOne
//	@JsonIgnore
//	private User createdBy;

	@LastModifiedDate
	private Date lastModifiedDate;

//	@LastModifiedBy
//	@ManyToOne
//	@JsonIgnore
//	private User lastModifiedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

//	public User getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(User createdBy) {
//		this.createdBy = createdBy;
//	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

//	public User getLastModifiedBy() {
//		return lastModifiedBy;
//	}
//
//	public void setLastModifiedBy(User lastModifiedBy) {
//		this.lastModifiedBy = lastModifiedBy;
//	}

}
