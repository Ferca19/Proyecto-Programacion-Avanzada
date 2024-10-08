package programacion.ejemplo.DTO;

import lombok.Data;


@Data
public class ProductoDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String tamano;
    private String color;
    private int eliminado;
    private String imagen;
    private Integer categoriaId;
    private Integer subcategoriaId;
    private Integer marcaId;

}