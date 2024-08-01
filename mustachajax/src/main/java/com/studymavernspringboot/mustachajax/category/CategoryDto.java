package com.studymavernspringboot.mustachajax.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder //@Builder는 상속을 못한다. @SuperBuilder는 가능
@NoArgsConstructor //매개변수 없는 기본생성자를 자동으로 만든다.
@AllArgsConstructor //모든 매개변수로 생성자를 자동으로 만든다.

public class CategoryDto implements ICategory {
    private Long id;
    private String name;
}