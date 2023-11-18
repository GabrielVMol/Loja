package br.com.loja.gabrielvinicius.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.loja.gabrielvinicius.models.Vendedor;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Integer>{
}