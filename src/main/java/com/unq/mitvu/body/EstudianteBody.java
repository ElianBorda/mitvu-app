package com.unq.mitvu.body;

import com.unq.mitvu.model.Estudiante;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstudianteBody {

    private String apellido;
    private String nombre;
    private String dni;
    private String carrera;
    private String comision_id;

    public EstudianteBody() {}

    public EstudianteBody(String apellido, String nombre, String dni,  String carrera, String comision_id) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.carrera = carrera;
        this.comision_id = comision_id;
    }

    public Estudiante toEstudiante(){
        return new Estudiante(apellido, nombre, dni, carrera, comision_id);
    }

}
