package org.contoso.netflix.reviews.adapter.output.dto;

import org.contoso.netflix.reviews.domain.entity.ReviewDraft;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewDraftMapper {
    ReviewDraft toDomain(ReviewsDraftDatabase reviewsDraftDatabase);
}
