package com.oppo.api.Opportunity.API.Entitys.PublishsEntity;

import com.oppo.api.Opportunity.API.Entitys.CommentEntity.CommentEntity;
import com.oppo.api.Opportunity.API.Models.AuthorModel.AuthorModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@Builder
public class PublishsEntity implements  Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Embedded
    private AuthorModel author;
    private String content;
    @OneToMany(mappedBy = "publishsEntity", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
    private List<String> likes;
    private Date createdAt;
    PublishsEntity(){

    }
}
