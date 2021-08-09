package com.example.demo.validation.vo;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Driver extends Person {
    @NotNull
    @Min(value = 18, message = "必须年满18岁", groups = DriverChecks.class)
    public Integer age;
    @NotNull
    @AssertTrue(message = "必须具有驾照", groups = DriverChecks.class)
    public Boolean hasDrivingLicense;

    public Driver(String john) {
        super(john);
    }

    public Driver() {
        super();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getHasDrivingLicense() {
        return hasDrivingLicense;
    }

    public void setHasDrivingLicense(Boolean hasDrivingLicense) {
        this.hasDrivingLicense = hasDrivingLicense;
    }
}