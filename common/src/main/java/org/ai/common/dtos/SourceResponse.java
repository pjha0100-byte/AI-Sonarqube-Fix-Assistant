package org.ai.common.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SourceResponse {

    private List<SourceLine> sources;
}
