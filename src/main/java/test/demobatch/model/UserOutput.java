package test.demobatch.model;

import java.time.LocalDateTime;

public record UserOutput(int userId,
                         int id,
                         String title,
                         String body,
                         LocalDateTime processedAt
                         ) {
}

