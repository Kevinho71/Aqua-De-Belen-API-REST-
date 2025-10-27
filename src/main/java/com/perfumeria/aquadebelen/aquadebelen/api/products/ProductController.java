package com.perfumeria.aquadebelen.aquadebelen.api.products;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.api.products.dto.ProductCreateRequest;
import com.perfumeria.aquadebelen.aquadebelen.api.products.dto.ProductUpdateRequest;
import com.perfumeria.aquadebelen.aquadebelen.api.products.dto.ProductView;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductQueryService query;
    private final ProductWriteService write;

    public ProductController(ProductQueryService query, ProductWriteService write){
        this.query = query; this.write = write;
    }

    @GetMapping
    public List<ProductView> list(){ return query.listAll(); }

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody ProductCreateRequest req){
        return ResponseEntity.ok(write.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody ProductUpdateRequest req){
        write.update(id, req);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        write.delete(id);
        return ResponseEntity.noContent().build();
    }
}
