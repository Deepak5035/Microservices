package com.alt.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Response",description = "Schema to hold Book Details")
public class Book {
	
    @Schema(description = "BookId for the Book")
	private Integer bookId;
    @Schema(description = "Name Of the Book")
    private String bookName ;
    @Schema(description = "Cost of the Book")
	private Double bookCost;
    
}
