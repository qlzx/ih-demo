package com.example.demo.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.demo.validation.vo.Car;
import com.example.demo.validation.vo.Driver;
import com.example.demo.validation.vo.OrderedChecks;
import com.example.demo.validation.vo.Person;
import com.example.demo.validation.vo.RentalCar;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class ValidationTest {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    @Test
    public void test() {
        // 可以试着给不同字段赋值查看校验效果
        Car car = new Car();
        car.setManufacturer("benz");
        car.setLicensePlate("234234");
        car.setSeatCount(5);
        car.setRegistered(true);
        car.setPassedVehicleInspection(true);
        Driver driver = new Driver();
        driver.setName("JACK");
        driver.setAge(11);
        driver.setHasDrivingLicense(false);
        car.setDriver(driver);
        car.setPassengers(new ArrayList<>());
        car.setBrand("");
        car.setDoors(4);
        //car.setCarTypeEnum(CarTypeEnum.BENZ);
        // 不传递校验顺序,则只校验groups为Default的,没有显式在注解写明groups属性的,则默认为Default
        validateBean(car);
        // 校验顺序显式传递
        validateBean(car, OrderedChecks.class);

        car.getDriver().setAge(20);
        car.getDriver().setHasDrivingLicense(true);
        validateBean(car, OrderedChecks.class);

        car.setSeatCount(1);
        Set<ConstraintViolation<Car>> constraintViolations3 = validator.validateProperty(car, "seatCount");
        System.err.println(constraintViolations3.iterator().next().getMessage());

        Set<ConstraintViolation<Car>> constraintViolations4 = validator.validateValue(Car.class, "registered", false);
        System.err.println(constraintViolations4.iterator().next().getMessage());

        // 校验顺序写在bean的类注解上
        RentalCar rentalCar = new RentalCar();
        Set<ConstraintViolation<RentalCar>> constraintViolations5 = validator.validate(rentalCar);
        System.err.println(constraintViolations5.iterator().next().getMessage());

    }

    @Test
    public void test1() {
        // 测试自定义校验注解
        Person person = new Person("John");
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
        ConstraintViolation<Person> constraintViolation = constraintViolations.iterator().next();
        System.out.println(constraintViolation.getMessage());
    }

    public static <T> void validateBean(T bean, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean, groups);
        if (constraintViolations.isEmpty()) {
            System.out.println("校验通过");
            return;
        }
        List<String> errors = new ArrayList<>(10);
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            errors.add(constraintViolation.getPropertyPath() + constraintViolation.getMessage());
        }
        //throw new ValidationException(StringUtils.join(errors, ","));
        System.err.println(StringUtils.join(errors, ","));
    }
}