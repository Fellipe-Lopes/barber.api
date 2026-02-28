package br.com.barber.saas.model.cliente;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="clientes")
@JsonPropertyOrder({"id", "nomeCliente", "contato"})
public class ClienteModel {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="cliente")
    private String nomeCliente;

    @Column(name="contato")
    private String contato;  
}
