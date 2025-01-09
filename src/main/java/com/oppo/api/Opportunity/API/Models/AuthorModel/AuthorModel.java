package com.oppo.api.Opportunity.API.Models.AuthorModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@Embeddable
@AllArgsConstructor
public class AuthorModel {
    private UUID AuthorID;
    private String AuthorName;
    AuthorModel(){

    }
}
