package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ManagementPk {

    @Override
	public String toString() {
		return "ManagementPk [id=" + id + ", name=" + name + "]";
	}

	@Column(name = "id")
    private String id;

    @Column(name = "name") 
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
