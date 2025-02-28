package com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO;

import com.oppo.api.Opportunity.API.Models.AuthorModel.AuthorModel;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Builder
public record CommentsDTO (
        @NotNull UUID Id,
        @NotNull AuthorModel author,
        @NotNull String content,
        int responses,
        List<String> likes,
        Date createdAt,
        UUID publishID
        ) implements Serializable {
}
