package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ManagementDto {

    @NotNull(message = "{NotNull.managementDto.id}")
    @NotBlank(message = "{NotBlank.managementDto.id}")
    private String id;

    @NotNull(message = "{NotNull.managementDto.name}")
    @NotBlank(message = "{NotBlank.managementDto.name}")
    private String name;

    @NotNull(message = "{NotNull.managementDto.address}")
    @NotBlank(message = "{NotBlank.managementDto.address}")
    private String address;

    @NotNull(message = "{NotNull.managementDto.phoneNumber}")
    @NotBlank(message = "{NotBlank.managementDto.phoneNumber}")
    @Pattern(regexp = "^[0-9+]+$", message = "{Pattern.managementDto.phoneNumber}")
    private String phoneNumber;

    @NotNull(message = "{NotNull.managementDto.logdate}")
    @NotBlank(message = "{NotBlank.managementDto.logdate}")
    private String logdate;

    @NotNull(message = "{NotNull.managementDto.acOrNonAc}")
    @NotBlank(message = "{NotBlank.managementDto.acOrNonAc}")
    private String acOrNonAc;

    @NotNull(message = "{NotNull.managementDto.age}")
    @NotBlank(message = "{NotBlank.managementDto.age}")
    @Pattern(regexp = "^[0-9]+$", message = "{Pattern.managementDto.age}")
    private String age;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLogdate() {
        return logdate;
    }

    public void setLogdate(String logdate) {
        this.logdate = logdate;
    }

    public String getAcOrNonAc() {
        return acOrNonAc;
    }

    public void setAcOrNonAc(String acOrNonAc) {
        this.acOrNonAc = acOrNonAc;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ManagementDto [id=" + id + ", name=" + name + ", address=" + address + ", phoneNumber=" + phoneNumber
                + ", logdate=" + logdate + ", acOrNonAc=" + acOrNonAc + ", age=" + age + "]";
    }
}
