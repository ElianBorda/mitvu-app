package com.unq.mitvu.config;

import com.unq.mitvu.model.*;
import com.unq.mitvu.service.ComisionService;
import com.unq.mitvu.service.EstudianteService;
import com.unq.mitvu.service.TutorService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.unq.mitvu.model.DiaHabil.LUNES;
import static com.unq.mitvu.model.DiaHabil.MARTES;
import static com.unq.mitvu.model.DiaHabil.MIERCOLES;
import static com.unq.mitvu.model.DiaHabil.JUEVES;
import static com.unq.mitvu.model.DiaHabil.VIERNES;

@Component
public class DataInitializer implements ApplicationRunner {

    private final ComisionService comisionService;
    private final TutorService tutorService;
    private final EstudianteService estudianteService;

    public DataInitializer(ComisionService comisionService,
                           TutorService tutorService,
                           EstudianteService estudianteService) {
        this.comisionService = comisionService;
        this.tutorService = tutorService;
        this.estudianteService = estudianteService;
    }

    @Override
    public void run(ApplicationArguments args) {

        // Limpiamos todo antes de insertar para evitar duplicados en cada reinicio
        estudianteService.eliminarTodasLasComisionesDeTodosLosEstudiantes();
        estudianteService.eliminarTodo();
        comisionService.eliminarTodosLosTutoresDeTodasLasComisiones();
        comisionService.eliminarTodo();
        tutorService.eliminarTodo();

        // ─────────────────────────────────────────
        // COMISIONES (10)
        // ─────────────────────────────────────────

        // Con aula y carrera (campos opcionales completos)
        Comision c1 = comisionService.crear(new Comision("Bernal", "Informática", "Tecnicatura en Programación", "Aula 1", new Horario(17, 00), new Horario(19, 00), LUNES));
        Comision c2 = comisionService.crear(new Comision("Bernal", "Informática", "Licenciatura en Sistemas", "Aula 2", new Horario(19, 00), new Horario(21, 00), MARTES));
        Comision c3 = comisionService.crear(new Comision("Florencio Varela", "Informática", "Tecnicatura en Programación", "Aula 3", new Horario(18, 00), new Horario(20, 00), MIERCOLES));
        Comision c4 = comisionService.crear(new Comision("Berazategui", "Ciencias Sociales", "Trabajo Social", "Aula 4", new Horario(17, 00), new Horario(19, 00), JUEVES));
        Comision c5 = comisionService.crear(new Comision("Bernal", "Arte y Cultura", "Tecnicatura en Artes Audiovisuales", "Aula 5", new Horario(20, 00), new Horario(22, 00), VIERNES));

        // Sin aula (campo opcional ausente)
        Comision c6 = comisionService.crear(new Comision("Florencio Varela", "Informática", "Licenciatura en Sistemas", null, new Horario(17, 00), new Horario(19, 00), LUNES));
        Comision c7 = comisionService.crear(new Comision("Berazategui", "Informática", "Tecnicatura en Programación", null, new Horario(19, 00), new Horario(21, 00), MARTES));

        // Sin carrera (campo opcional ausente)
        Comision c8 = comisionService.crear(new Comision("Bernal", "Informática", null, "Aula 8", new Horario(18, 00), new Horario(20, 00), MIERCOLES));

        // Sin aula ni carrera
        // Comision c9 = comisionService.crear(new Comision("Florencio Varela", "Ciencias Sociales", null, null, new Horario(17, 00), new Horario(19, 00), JUEVES));

        // Sin tutor ni estudiantes (comisión vacía)
        // Comision c10 = comisionService.crear(new Comision("Berazategui", "Arte y Cultura", null, null, new Horario(20, 00), new Horario(22, 00), VIERNES));

        // ─────────────────────────────────────────
        // TUTORES (4)
        // ─────────────────────────────────────────

        // Tutor con múltiples comisiones
        Tutor t1 = new Tutor("García", "Martín", "30111222", "martin.garcia@unq.edu.ar", "password123", Rol.TUTOR);
        t1 = tutorService.crear(t1);

        // Tutor con una sola comisión
        Tutor t2 = new Tutor("López", "Sofía", "30222333", "sofia.lopez@unq.edu.ar", "password123", Rol.TUTOR);
        t2 = tutorService.crear(t2);

        // Tutor con múltiples comisiones
        Tutor t3 = new Tutor("Martínez", "Diego", "30333444", "diego.martinez@unq.edu.ar", "password123", Rol.TUTOR);
        t3 = tutorService.crear(t3);

        // Tutor sin comisiones asignadas
        Tutor t4 = new Tutor("Fernández", "Laura", "30444555", "laura.fernandez@unq.edu.ar", "password123", Rol.TUTOR);
        t4 = tutorService.crear(t4);

        // ─────────────────────────────────────────
        // ASIGNACIÓN DE TUTORES A COMISIONES
        // t1 → c1, c2, c3
        // t2 → c4
        // t3 → c5, c6, c7
        // t4 → sin comisiones
        // c8, c9, c10 → sin tutor
        // ─────────────────────────────────────────

        comisionService.agregarTutorAComisionesPorId(t1.getId(), List.of(c1.getId(), c2.getId(), c3.getId()));
        comisionService.agregarTutorAComision(t2.getId(), c4.getId());
        comisionService.agregarTutorAComisionesPorId(t3.getId(), List.of(c5.getId(), c6.getId(), c7.getId()));

        // ─────────────────────────────────────────
        // ESTUDIANTES (20)
        // ─────────────────────────────────────────

        Estudiante e1  = estudianteService.crear(new Estudiante("Rodríguez", "Juan",    "40100001", "juan.rodriguez@gmail.com",    "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e2  = estudianteService.crear(new Estudiante("Pérez",     "María",   "40100002", "maria.perez@gmail.com",        "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        Estudiante e3  = estudianteService.crear(new Estudiante("González",  "Carlos",  "40100003", "carlos.gonzalez@gmail.com",    "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e4  = estudianteService.crear(new Estudiante("Sánchez",   "Ana",     "40100004", "ana.sanchez@gmail.com",        "pass123", Rol.ESTUDIANTE, "Trabajo Social"));
        Estudiante e5  = estudianteService.crear(new Estudiante("Ramírez",   "Lucas",   "40100005", "lucas.ramirez@gmail.com",      "pass123", Rol.ESTUDIANTE, "Tecnicatura en Artes Audiovisuales"));
        Estudiante e6  = estudianteService.crear(new Estudiante("Torres",    "Valentina","40100006","valentina.torres@gmail.com",   "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e7  = estudianteService.crear(new Estudiante("Flores",    "Matías",  "40100007", "matias.flores@gmail.com",      "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        Estudiante e8  = estudianteService.crear(new Estudiante("Ruiz",      "Camila",  "40100008", "camila.ruiz@gmail.com",        "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        // Estudiante e9  = estudianteService.crear(new Estudiante("Díaz",      "Nicolás", "40100009", "nicolas.diaz@gmail.com",       "pass123", Rol.ESTUDIANTE, "Trabajo Social"));
        // Estudiante e10 = estudianteService.crear(new Estudiante("Morales",   "Florencia","40100010","florencia.morales@gmail.com",  "pass123", Rol.ESTUDIANTE, "Tecnicatura en Artes Audiovisuales"));
        // Estudiante e11 = estudianteService.crear(new Estudiante("Jiménez",   "Santiago", "40100011","santiago.jimenez@gmail.com",   "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        // Estudiante e12 = estudianteService.crear(new Estudiante("Vargas",    "Lucía",   "40100012", "lucia.vargas@gmail.com",       "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        // Estudiante e13 = estudianteService.crear(new Estudiante("Castro",    "Tomás",   "40100013", "tomas.castro@gmail.com",       "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        // Estudiante e14 = estudianteService.crear(new Estudiante("Romero",    "Julieta", "40100014", "julieta.romero@gmail.com",     "pass123", Rol.ESTUDIANTE, "Trabajo Social"));
        // Estudiante e15 = estudianteService.crear(new Estudiante("Herrera",   "Agustín", "40100015", "agustin.herrera@gmail.com",    "pass123", Rol.ESTUDIANTE, "Tecnicatura en Artes Audiovisuales"));

        // Estudiantes sin comisión asignada (casos borde)
        // Estudiante e16 = estudianteService.crear(new Estudiante("Medina",    "Abril",   "40100016", "abril.medina@gmail.com",       "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        // Estudiante e17 = estudianteService.crear(new Estudiante("Reyes",     "Felipe",  "40100017", "felipe.reyes@gmail.com",       "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        // Estudiante e18 = estudianteService.crear(new Estudiante("Ortega",    "Micaela", "40100018", "micaela.ortega@gmail.com",     "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        // Estudiante e19 = estudianteService.crear(new Estudiante("Navarro",   "Bruno",   "40100019", "bruno.navarro@gmail.com",      "pass123", Rol.ESTUDIANTE, "Trabajo Social"));
        // Estudiante e20 = estudianteService.crear(new Estudiante("Silva",     "Emilia",  "40100020", "emilia.silva@gmail.com",       "pass123", Rol.ESTUDIANTE, "Tecnicatura en Artes Audiovisuales"));

        // ─────────────────────────────────────────
        // ASIGNACIÓN DE ESTUDIANTES A COMISIONES
        // ─────────────────────────────────────────

        estudianteService.cambiarEstudiantesAComision(List.of(e1.getId(), e2.getId(), e3.getId()),   c1.getId());
        // estudianteService.cambiarEstudiantesAComision(List.of(e4.getId(), e5.getId()),               c2.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(e6.getId(), e7.getId(), e8.getId()),   c3.getId());
        // estudianteService.cambiarEstudiantesAComision(List.of(e9.getId()),                           c4.getId());
        // estudianteService.cambiarEstudiantesAComision(List.of(e10.getId(), e11.getId(), e12.getId()),c5.getId());
        // estudianteService.cambiarEstudiantesAComision(List.of(e13.getId(), e14.getId()),             c6.getId());
        // estudianteService.cambiarEstudiantesAComision(List.of(e15.getId()),                          c7.getId());
        // El resto de los estudiantes quedan sin comisión intencionalmente

        System.out.println("✅ DataInitializer: datos de prueba cargados correctamente.");
    }
}