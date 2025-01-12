package com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO;

import com.oppo.api.Opportunity.API.Entitys.CommentEntity.CommentEntity;
import com.oppo.api.Opportunity.API.Models.AuthorModel.AuthorModel;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Builder
public record PostsDTO (
        @NotNull UUID id,
        @NotNull AuthorModel author,
        @NotNull String content,
        List<String> likes,
        @NotNull Date createdAt

        ) implements Serializable {
}
