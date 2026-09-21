package org.socialization.friends.makings.backend.friend;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class Friend {
    private Integer id;
    private String name;
    private String surname;
    private String gender;
    private String status;
    private Date birthDate;
    private Optional<Date> dateMet;
    private Optional<String> description;

    public Friend(String name, String surname, String gender, String status, Date birthDate) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.status = status;
        this.birthDate = birthDate;
    }

    public Friend(String name, String surname, String gender, String status, Date birthDate, Date dateMet, String description) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.status = status;
        this.birthDate = birthDate;
        this.dateMet = Optional.of(dateMet);
        this.description = Optional.of(description);
    }

    public Friend(){}

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Optional<Date> getDateMet() {
        return dateMet;
    }

    public Optional<String> getDescription() {
        return description;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setDateMet(Date dateMet) {
        if(dateMet == null){
            this.dateMet = Optional.empty();
            return;
        }
        this.dateMet = Optional.of(dateMet);
    }

    public void setDescription(String description) {
        if(description == null){
            this.description = Optional.empty();
            return;
        }
        this.description = Optional.of(description);;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                ", birthDate=" + birthDate +
                ", dateMet=" + dateMet +
                ", description=" + description +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return this.id.equals(friend.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
