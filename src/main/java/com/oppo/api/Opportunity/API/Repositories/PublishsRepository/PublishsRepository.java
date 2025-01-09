package com.oppo.api.Opportunity.API.Repositories.PublishsRepository;

import com.oppo.api.Opportunity.API.Entitys.PublishsEntity.PublishsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PublishsRepository extends JpaRepository<PublishsEntity, UUID> {
}
