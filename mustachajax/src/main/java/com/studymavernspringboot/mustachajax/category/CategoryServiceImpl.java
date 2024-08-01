package com.studymavernspringboot.mustachajax.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService<ICategory> {
    @Autowired
    //SpringBoot가 CategoryMybatisMapper 데이터형으로 객체를 자동 생성한다.
    private CategoryMybatisMapper categoryMybatisMapper;

    @Override
    public ICategory findById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        CategoryDto dto = this.categoryMybatisMapper.findById(id);
        return dto;
    }

    @Override
    public ICategory findByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        CategoryDto find = this.categoryMybatisMapper.findByName(name);
        return find;
    }

    @Override
    public List<ICategory> getAllList() {
        List<ICategory> list = this.getICategoryList(
                this.categoryMybatisMapper.findAll()
        );
        return list;
    }

    private List<ICategory> getICategoryList(List<CategoryDto> list) {
        if (list == null || list.size() <= 0) {
            return new ArrayList<>();
        }

        List <ICategory> result = list.stream()
                .map(entity -> (ICategory)entity)
                .toList();
        //stream Java 1.8 등장한 방법 : 배열/Collection 자료형을 처리할때
        //stream을 사용하면 처리 속도도 증가하고 문법도 간결하게 만든다.
        //배열객체/컬렉션자료형.stream() ~~~~~ 정렬, 형변환, 원소마다 똑같은 동작을 실행한다.
        return result;
    }

    @Override
    public ICategory insert(ICategory category) throws Exception {
        if (!isValidInsert(category)) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.copyFields(category);
        dto.setId(0L);
        this.categoryMybatisMapper.insert(dto);
        return dto;
    }

    private boolean isValidInsert(ICategory category) {
        if (category == null) {
            return false;
        } else if (category.getName() == null || category.getName().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean remove(Long id) throws Exception {
        if (id == null || id <= 0) {
            return false;
        }
        ICategory find = this.findById(id);
        if (find == null) {
            return false;
        }
        this.categoryMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public ICategory update(Long id, ICategory category) throws Exception {
        ICategory find = this.findById(id);
        if (find == null) {
            return null;
        }
        find.copyFields(category);
        this.categoryMybatisMapper.update((CategoryDto)find);
        return find;
    }
    @Override
    public List<ICategory> findAllByNameContains(SearchCategoryDto dto) {
        if (dto == null) {
//            return List.of();
            return new ArrayList<>();
        }
        String sOrer = "";
        if ( dto.getSortColumn() == null ) {
            sOrer = "id";
        } else {
            sOrer = dto.getSortColumn();
        }
        dto.setOrderByWord( sOrer + " " + dto.getSortAscDsc() );
        if (dto.getRowsOnePage() == null) {
            //한 페이지당 보여주는 행의 갯수
            dto.setRowsOnePage(10);
        }
        List<ICategory> list = this.getICategoryList(
                this.categoryMybatisMapper.findAllByNameContains(dto)
        );
        return list;
    }

    @Override
    public int countAllByNameContains(SearchCategoryDto searchCategoryDto) {
        return this.categoryMybatisMapper.countAllByNameContains(searchCategoryDto);
    }
}