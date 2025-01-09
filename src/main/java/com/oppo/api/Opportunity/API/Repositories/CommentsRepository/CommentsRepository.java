package com.oppo.api.Opportunity.API.Repositories.CommentsRepository;

import com.oppo.api.Opportunity.API.Entitys.CommentEntity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, UUID> {
}
