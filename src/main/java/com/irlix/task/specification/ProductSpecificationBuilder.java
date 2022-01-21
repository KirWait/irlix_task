package com.irlix.task.specification;

import com.irlix.task.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSpecificationBuilder {

    private final List<SearchCriteria> params;

    public ProductSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public void with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));

    }

    public Specification<ProductEntity> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<ProductEntity>> specs = params.stream()
                .map(ProductSpecification::new)
                .collect(Collectors.toList());

        Specification<ProductEntity> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}