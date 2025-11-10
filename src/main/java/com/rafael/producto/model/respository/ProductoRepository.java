package com.rafael.producto.model.respository;

import com.rafael.producto.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoriaId(Long categoriaId);
    Optional<Producto> findByCodigo(String codigo);

    @Modifying
    @Query("UPDATE Producto p SET p.stock = :stock WHERE p.codigo = :codigo")
    void actualizarStock(@Param("codigo") String codigo, @Param("stock") Integer stock);

}