package com.practice.training.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class Student {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @NonNull
    @Size(min = 10, max = 10)
    private String phoneNumber;
    private String address;
    @PrePersist
    public void GenerateId(){
        if(this.id == null|| this.id <=0L){
            this.id=generate8DigitId();

        }
    }

    private Long generate8DigitId(){
        long min=100000000L;
        long max=999999999L;
        return min+(long)(Math.random()*(max-min));
    }

}
