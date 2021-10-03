package com.example.democrudjwt.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncreaseCash {
    BigDecimal startCash;
    BigDecimal maxCash;
    Long profilesId;
}
