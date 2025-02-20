package com.page.turner.PageTurner.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookDTO {

    private Long id;

    private String title;

    private String author;

    private String isbn;

    private String publisher;

    private LocalDate publishedDate;

    private String description;

    private BigDecimal price;

    private String language;

    private String genre;

    private Double rating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
