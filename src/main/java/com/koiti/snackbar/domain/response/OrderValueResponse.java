package com.koiti.snackbar.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderValueResponse {
    private Double value;
    private Double discount;
}
