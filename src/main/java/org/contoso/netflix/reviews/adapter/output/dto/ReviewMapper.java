package org.contoso.netflix.reviews.adapter.output.dto;

import org.contoso.netflix.reviews.domain.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toDomain(ReviewDatabase reviewDatabase);

    ReviewDatabase toDatabase(Review review);
}
