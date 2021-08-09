package com.example.demo.validation.vo;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Range;

public class Car {
    @NotBlank // 不能为null,不能为空字符串
    private String manufacturer;
    @NotNull // 不能为null
    @Size(min = 2, max = 14) // 字符串长度位于2到14之间
    private String licensePlate;
    @Min(2)
    @Max(5) // 注意,未添加NotNull注解,所以seatCount可以为null,只有当seatCount不为null@Min @Max才会做校验
    private Integer seatCount;
    @AssertTrue // registered不为null时,则值需是true
    private Boolean registered;
    // groups用于指定所属的校验组
    @AssertTrue(message = "The car has to pass the vehicle inspection first", groups = CarChecks.class)
    private Boolean passedVehicleInspection;
    @Valid // 表明应对driver对象内字段继续做校验
    @NotNull
    private Driver driver;
    @Valid // 表明应对passengers里的Person对象内字段继续做校验
    @Size(max = 2) // 表明passengers最多只能有两个对象
    private List<Person> passengers = new ArrayList<>();
    private String brand;
    @Range(min = 2,max = 4) // 作用同@Min @Max
    private Integer doors;
    //private CarTypeEnum carTypeEnum;
    // get set......

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public Boolean getPassedVehicleInspection() {
        return passedVehicleInspection;
    }

    public void setPassedVehicleInspection(Boolean passedVehicleInspection) {
        this.passedVehicleInspection = passedVehicleInspection;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Person> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Person> passengers) {
        this.passengers = passengers;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }
}