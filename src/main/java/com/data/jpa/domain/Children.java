package com.data.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "children")
@Data
public class Children {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long seq;
    private Long parent_seq;
    private String name;
}
