/**
 * 
 */
package com.mercadolibre.ip.validator.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author robet
 *
 */
@Entity
@Table(name = "black_list")
public class IPBlackListEntity implements Serializable {

	private static final long serialVersionUID = 7156852159047481362L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ip_number", nullable = false, unique = true, length = 15)
	private String ipNumber;

	@Column(name = "create_at", nullable = false)
	private LocalDateTime createAt;

	public IPBlackListEntity() {
	}

	public IPBlackListEntity(String ipNumber) {
		this.ipNumber = ipNumber;
		this.createAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIpNumber() {
		return ipNumber;
	}

	public void setIpNumber(String ipNumber) {
		this.ipNumber = ipNumber;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

}
