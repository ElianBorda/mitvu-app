package com.unq.mitvu.config;

import com.unq.mitvu.model.*;
import com.unq.mitvu.service.ComisionService;
import com.unq.mitvu.service.EstudianteService;
import com.unq.mitvu.service.EventoService;
import com.unq.mitvu.service.TutorService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.unq.mitvu.model.DiaHabil.LUNES;
import static com.unq.mitvu.model.DiaHabil.MARTES;
import static com.unq.mitvu.model.DiaHabil.MIERCOLES;
import static com.unq.mitvu.model.DiaHabil.JUEVES;
import static com.unq.mitvu.model.DiaHabil.VIERNES;

@Component
@Profile({"dev", "test"})
public class DataInitializer implements ApplicationRunner {

    private final ComisionService comisionService;
    private final TutorService tutorService;
    private final EstudianteService estudianteService;
    private final EventoService eventoService;

    public DataInitializer(ComisionService comisionService,
                           TutorService tutorService,
                           EstudianteService estudianteService,
                           EventoService eventoService) {
        this.comisionService = comisionService;
        this.tutorService = tutorService;
        this.estudianteService = estudianteService;
        this.eventoService = eventoService;
    }

    @Override
    public void run(ApplicationArguments args) {

        // Limpiamos todo antes de insertar
        estudianteService.eliminarTodasLasComisionesDeTodosLosEstudiantes();
        estudianteService.eliminarTodo();
        comisionService.eliminarTodosLosTutoresDeTodasLasComisiones();
        comisionService.eliminarTodo();
        tutorService.eliminarTodo();
        eventoService.eliminarTodo();

        // ─────────────────────────────────────────
        // 1. CALENDARIO DE CLASES (Compartido entre eventos y asistencias)
        // ─────────────────────────────────────────
        LocalDate hoy = LocalDate.now();
        int diasHastaLunes = (DayOfWeek.MONDAY.getValue() - hoy.getDayOfWeek().getValue() + 7) % 7;
        if (diasHastaLunes == 0) diasHastaLunes = 7;
        LocalDate primerLunes = hoy.plusDays(diasHastaLunes);

        List<LocalDate> fechasCursada = List.of(
                primerLunes,                  // s1 Lunes
                primerLunes.plusDays(2),      // s1 Miércoles
                primerLunes.plusDays(4),      // s1 Viernes
                primerLunes.plusDays(7),      // s2 Lunes
                primerLunes.plusDays(9),      // s2 Miércoles
                primerLunes.plusDays(11)      // s2 Viernes
        );
        Random random = new Random();

        // ─────────────────────────────────────────
        // 2. COMISIONES Y TUTORES
        // ─────────────────────────────────────────
        Comision c1 = comisionService.crear(new Comision("Bernal",           "Informática",     "Tecnicatura en Programación",        "Aula 1", new Horario(17, 00), new Horario(19, 00), LUNES));
        Comision c2 = comisionService.crear(new Comision("Bernal",           "Informática",     "Licenciatura en Sistemas",           "Aula 2", new Horario(19, 00), new Horario(21, 00), MARTES));
        Comision c3 = comisionService.crear(new Comision("Florencio Varela", "Informática",     "Tecnicatura en Programación",        "Aula 3", new Horario(18, 00), new Horario(20, 00), MIERCOLES));
        Comision c4 = comisionService.crear(new Comision("Berazategui",      "Ciencias Sociales","Trabajo Social",                   "Aula 4", new Horario(17, 00), new Horario(19, 00), JUEVES));
        Comision c5 = comisionService.crear(new Comision("Bernal",           "Arte y Cultura",  "Tecnicatura en Artes Audiovisuales", "Aula 5", new Horario(20, 00), new Horario(22, 00), VIERNES));
        Comision c6 = comisionService.crear(new Comision("Florencio Varela", "Informática",     "Licenciatura en Sistemas",           null,     new Horario(17, 00), new Horario(19, 00), LUNES));
        Comision c7 = comisionService.crear(new Comision("Berazategui",      "Informática",     "Tecnicatura en Programación",        null,     new Horario(19, 00), new Horario(21, 00), MARTES));
        Comision c8 = comisionService.crear(new Comision("Bernal",           "Informática",     null,                                 "Aula 8", new Horario(18, 00), new Horario(20, 00), MIERCOLES));

        Tutor t1 = tutorService.crear(new Tutor("García",    "Martín", "30111222", "martin.garcia@unq.edu.ar",   "password123", Rol.TUTOR));
        Tutor t2 = tutorService.crear(new Tutor("López",     "Sofía",  "30222333", "sofia.lopez@unq.edu.ar",     "password123", Rol.TUTOR));
        Tutor t3 = tutorService.crear(new Tutor("Martínez",  "Diego",  "30333444", "diego.martinez@unq.edu.ar",  "password123", Rol.TUTOR));
        Tutor t4 = tutorService.crear(new Tutor("Fernández", "Laura",  "30444555", "laura.fernandez@unq.edu.ar", "password123", Rol.TUTOR));

        comisionService.agregarTutorAComisionesPorId(t1.getId(), List.of(c1.getId(), c2.getId(), c3.getId()));
        comisionService.agregarTutorAComision(t2.getId(), c4.getId());
        comisionService.agregarTutorAComisionesPorId(t3.getId(), List.of(c5.getId(), c6.getId(), c7.getId()));

        // ─────────────────────────────────────────
        // 3. ESTUDIANTES ACTIVOS ORIGINALES (8)
        // ─────────────────────────────────────────
        // A los que SÍ van a una comisión (true) se les generan asistencias
        Estudiante e1 = crearEstudiante("Rodríguez", "Juan", "40100001", "juan.rodriguez@gmail.com", "Tecnicatura en Programación", fechasCursada, true, random);
        Estudiante e2 = crearEstudiante("Pérez", "María", "40100002", "maria.perez@gmail.com", "Licenciatura en Sistemas", fechasCursada, true, random);
        Estudiante e3 = crearEstudiante("González", "Carlos", "40100003", "carlos.gonzalez@gmail.com", "Tecnicatura en Programación", fechasCursada, true, random);

        // Casos borde: NO van a comisión, por lo tanto (false) NO se les generan asistencias
        Estudiante e4 = crearEstudiante("Sánchez", "Ana", "40100004", "ana.sanchez@gmail.com", "Trabajo Social", fechasCursada, false, random);
        Estudiante e5 = crearEstudiante("Ramírez", "Lucas", "40100005", "lucas.ramirez@gmail.com", "Tecnicatura en Artes Audiovisuales", fechasCursada, false, random);

        Estudiante e6 = crearEstudiante("Torres", "Valentina", "40100006", "valentina.torres@gmail.com", "Tecnicatura en Programación", fechasCursada, true, random);
        Estudiante e7 = crearEstudiante("Flores", "Matías", "40100007", "matias.flores@gmail.com", "Licenciatura en Sistemas", fechasCursada, true, random);
        Estudiante e8 = crearEstudiante("Ruiz", "Camila", "40100008", "camila.ruiz@gmail.com", "Tecnicatura en Programación", fechasCursada, true, random);

        estudianteService.cambiarEstudiantesAComision(List.of(e1.getId(), e2.getId(), e3.getId()), c1.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(e6.getId(), e7.getId(), e8.getId()), c3.getId());

        // ─────────────────────────────────────────
        // 4. ESTUDIANTES MASIVOS (20 POR COMISIÓN) = 160 Estudiantes
        // ─────────────────────────────────────────
        List<Comision> todasLasComisiones = List.of(c1, c2, c3, c4, c5, c6, c7, c8);
        int estudianteCounter = 100;

        for (Comision comision : todasLasComisiones) {
            List<String> idsEstudiantesNuevos = new ArrayList<>();

            for (int i = 0; i < 20; i++) {
                String carreraAsignada = comision.getCarrera() != null ? comision.getCarrera() : "Carrera General";

                // Todos estos van a una comisión, por ende tienen asistencias (true)
                Estudiante nuevoEstudiante = crearEstudiante(
                        "Apellido" + estudianteCounter,
                        "Nombre" + estudianteCounter,
                        "40200" + String.format("%03d", estudianteCounter),
                        "estudiante" + estudianteCounter + "@unq.edu.ar",
                        carreraAsignada,
                        fechasCursada,
                        true,
                        random
                );

                idsEstudiantesNuevos.add(nuevoEstudiante.getId());
                estudianteCounter++;
            }
            estudianteService.cambiarEstudiantesAComision(idsEstudiantesNuevos, comision.getId());
        }

        // ─────────────────────────────────────────
        // 5. ESTUDIANTES DADOS DE BAJA (4)
        // ─────────────────────────────────────────
        Estudiante b1 = crearEstudiante("Díaz", "Nicolás", "40100009", "nicolas.diaz@gmail.com", "Trabajo Social", fechasCursada, true, random);
        Estudiante b2 = crearEstudiante("Morales", "Florencia", "40100010", "florencia.morales@gmail.com", "Tecnicatura en Artes Audiovisuales", fechasCursada, true, random);
        Estudiante b3 = crearEstudiante("Jiménez", "Santiago", "40100011", "santiago.jimenez@gmail.com", "Tecnicatura en Programación", fechasCursada, true, random);
        Estudiante b4 = crearEstudiante("Vargas", "Lucía", "40100012", "lucia.vargas@gmail.com", "Licenciatura en Sistemas", fechasCursada, true, random);

        estudianteService.cambiarEstudiantesAComision(List.of(b1.getId(), b2.getId()), c2.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(b3.getId(), b4.getId()), c4.getId());

        estudianteService.darseDeBaja(b1.getId(), new FormularioBaja(MotivoBaja.OTRO, "Decido no continuar con la cursada.", LocalDate.now().minusDays(10), null));
        estudianteService.darseDeBaja(b2.getId(), new FormularioBaja(MotivoBaja.CAMBIO_INSTITUCION, "Cambié de universidad.", LocalDate.now().minusDays(7), null));
        estudianteService.darseDeBaja(b3.getId(), new FormularioBaja(MotivoBaja.MOTIVOS_PERSONALES, "Problemas familiares.", LocalDate.now().minusDays(3), null));
        estudianteService.darseDeBaja(b4.getId(), new FormularioBaja(MotivoBaja.FALTA_DE_TIEMPO, "", LocalDate.now().minusDays(1), null));

        // ─────────────────────────────────────────
        // 6. EVENTOS DE CURSADA (Usando las fechas compartidas)
        // ─────────────────────────────────────────
        eventoService.crear(new Evento(null, "Encuentro 1", "Primer encuentro.",  fechasCursada.get(0), null, null, true));
        eventoService.crear(new Evento(null, "Encuentro 2", "Segundo encuentro.", fechasCursada.get(1), null, null, true));
        eventoService.crear(new Evento(null, "Encuentro 3", "Tercer encuentro.",  fechasCursada.get(2), null, null, true));
        eventoService.crear(new Evento(null, "Encuentro 4", "Cuarto encuentro.",  fechasCursada.get(3), null, null, true));
        eventoService.crear(new Evento(null, "Encuentro 5", "Quinto encuentro.",  fechasCursada.get(4), null, null, true));
        eventoService.crear(new Evento(null, "Encuentro 6", "Último encuentro.",  fechasCursada.get(5), null, null, true));

        System.out.println("✅ DataInitializer: datos de prueba cargados correctamente.");
        System.out.println("   → 8 comisiones | 4 tutores | 168 estudiantes activos | 4 dados de baja | 6 eventos");
    }

    /**
     * Helper privado para crear un estudiante e inyectarle las asistencias aleatorias solo si se especifica.
     */
    private Estudiante crearEstudiante(String apellido, String nombre, String dni, String email, String carrera, List<LocalDate> fechas, boolean tieneComision, Random random) {
        Estudiante e = new Estudiante(apellido, nombre, dni, email, "pass123", Rol.ESTUDIANTE, carrera);

        // Solo genera asistencias si el estudiante va a tener comisión
        if (tieneComision && fechas != null) {
            for (LocalDate fecha : fechas) {
                int probabilidad = random.nextInt(100);
                TipoDeAsistencia tipo;

                if (probabilidad < 75) {
                    tipo = TipoDeAsistencia.PRESENTE;
                } else if (probabilidad < 90) {
                    tipo = TipoDeAsistencia.AUSENTE;
                } else {
                    tipo = TipoDeAsistencia.AUSENCIA_JUSTIFICADA;
                }

                e.agregarAsistencia(new Asistencia(fecha, tipo, "Generado por DataInitializer"));
            }
        }

        return estudianteService.crear(e);
    }
}