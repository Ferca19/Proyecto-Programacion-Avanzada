package programacion.ejemplo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombre;

    private String descripcion;

    @NotNull
    private double precio;

    @NotNull
    private int stock;

    @NotNull
    private String tamano;

    @NotNull
    private String color;

    @NotNull
    private int eliminado;
    public static final int NO=0;
    public static final int SI=1;

    public void asEliminar() {
        this.setEliminado(1);
    }

    private String imagen; // URL de la imagen


    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "subcategoria_id", nullable = false)
    private Subcategoria subcategoria;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    // Relación con DetalleAjusteInventario (un producto puede estar en muchos detalles)
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DetalleAjusteInventario> detallesAjuste;
}
