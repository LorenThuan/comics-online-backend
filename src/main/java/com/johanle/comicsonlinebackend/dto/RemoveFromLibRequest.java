package com.johanle.comicsonlinebackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.johanle.comicsonlinebackend.model.Comic;
import com.johanle.comicsonlinebackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveFromLibRequest {
    private List<Comic> comicList;
    private List<Comic> comicListRemove;
}
