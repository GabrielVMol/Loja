package br.com.loja.gabrielvinicius.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.loja.gabrielvinicius.models.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer>{
}