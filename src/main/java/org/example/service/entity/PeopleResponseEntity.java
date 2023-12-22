package org.example.service.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
public class PeopleResponseEntity {
    private Integer count;
    private String next;
    private String previous;
    private List<PeopleEntity> results;

}
