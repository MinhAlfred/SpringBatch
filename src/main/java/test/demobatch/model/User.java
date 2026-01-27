package test.demobatch.model;

import lombok.Builder;
import lombok.Data;

@Builder
public record User(
        int userId,
        int id,
        String title,
        String body
) {
}
