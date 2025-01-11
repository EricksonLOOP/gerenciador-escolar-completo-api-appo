package com.oppo.api.Opportunity.API.Models.AuthorModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
@AllArgsConstructor
public class AuthorModel implements Serializable {
    private UUID AuthorID;
    private String AuthorName;
    AuthorModel(){

    }
}
