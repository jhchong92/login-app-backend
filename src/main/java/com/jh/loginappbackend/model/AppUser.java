package com.jh.loginappbackend.model;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
public class AppUser {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private UUID id;

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdOn;

}
