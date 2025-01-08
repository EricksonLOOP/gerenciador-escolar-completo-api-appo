package com.oppo.api.Opportunity.API.Entitys.CommentEntity;

import com.oppo.api.Opportunity.API.Entitys.PublishsEntity.PublishsEntity;
import com.oppo.api.Opportunity.API.Models.AuthorModel.AuthorModel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private AuthorModel authorModel;
    private String content;
    @OneToMany(mappedBy = "responses", cascade = CascadeType.ALL)
    private List<CommentEntity> responses;
    private int likes;
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PublishsEntity publishsEntity;
}
