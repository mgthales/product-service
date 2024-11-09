package com.thalesmonteiro.springboot.controller;

import com.thalesmonteiro.springboot.dtos.ProductRecordDro;
import com.thalesmonteiro.springboot.models.ProductModel;
import com.thalesmonteiro.springboot.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDro productRecordDro) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDro, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productList = productRepository.findAll();
        if (!productList.isEmpty()) {
            for (ProductModel product: productList) {
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity <Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productO.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));
        return ResponseEntity.status(HttpStatus.OK).body(productO.get());

    }

    // Mapeia a requisição PUT para atualizar um produto com base no ID fornecido na URL
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(
            // Obtém o ID do produto a partir do caminho da URL
            @PathVariable(value = "id") UUID id,
            // Obtém o corpo da requisição contendo os dados do produto que serão atualizados
            @RequestBody ProductRecordDro productRecordDro) {

        // Busca o produto no banco de dados pelo ID
        Optional<ProductModel> productO = productRepository.findById(id);

        // Verifica se o produto existe, retornando "Product not found" se o produto não for encontrado
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        // Caso o produto seja encontrado, armazena o objeto em productModel
        var productModel = productO.get();

        // Copia as propriedades do objeto recebido na requisição (productRecordDro) para o modelo do produto (productModel)
        BeanUtils.copyProperties(productRecordDro, productModel);

        // Salva o produto atualizado no repositório e retorna o status OK com o objeto atualizado
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao existe.");
        }
         productRepository.delete(productO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso.");
    }






}
