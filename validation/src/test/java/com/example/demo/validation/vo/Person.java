
package com.example.demo.validation.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

public class Person {
    private long personId = 0;
    @NotBlank
    //@CheckCase(value = CaseMode.UPPER, message = "名字必须为大写")
    private String name;
    public Date birthday;

    @NotEmpty
    private List<String> codes;

    public Person(){}

    public Person(String john) {
        this.name = john;
    }
    // get set......

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}