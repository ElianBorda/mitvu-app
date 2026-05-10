package com.unq.mitvu.config;

import com.unq.mitvu.model.*;
import com.unq.mitvu.service.ComisionService;
import com.unq.mitvu.service.EstudianteService;
import com.unq.mitvu.service.EventoService;
import com.unq.mitvu.service.TutorService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

        // Limpiamos todo antes de insertar para evitar duplicados en cada reinicio
        estudianteService.eliminarTodasLasComisionesDeTodosLosEstudiantes();
        estudianteService.eliminarTodo();
        comisionService.eliminarTodosLosTutoresDeTodasLasComisiones();
        comisionService.eliminarTodo();
        tutorService.eliminarTodo();
        eventoService.eliminarTodo();

        // ─────────────────────────────────────────
        // COMISIONES (8)
        // ─────────────────────────────────────────

        Comision c1 = comisionService.crear(new Comision("Bernal",           "Informática",     "Tecnicatura en Programación",        "Aula 1", new Horario(17, 00), new Horario(19, 00), LUNES));
        Comision c2 = comisionService.crear(new Comision("Bernal",           "Informática",     "Licenciatura en Sistemas",           "Aula 2", new Horario(19, 00), new Horario(21, 00), MARTES));
        Comision c3 = comisionService.crear(new Comision("Florencio Varela", "Informática",     "Tecnicatura en Programación",        "Aula 3", new Horario(18, 00), new Horario(20, 00), MIERCOLES));
        Comision c4 = comisionService.crear(new Comision("Berazategui",      "Ciencias Sociales","Trabajo Social",                   "Aula 4", new Horario(17, 00), new Horario(19, 00), JUEVES));
        Comision c5 = comisionService.crear(new Comision("Bernal",           "Arte y Cultura",  "Tecnicatura en Artes Audiovisuales", "Aula 5", new Horario(20, 00), new Horario(22, 00), VIERNES));
        Comision c6 = comisionService.crear(new Comision("Florencio Varela", "Informática",     "Licenciatura en Sistemas",           null,     new Horario(17, 00), new Horario(19, 00), LUNES));
        Comision c7 = comisionService.crear(new Comision("Berazategui",      "Informática",     "Tecnicatura en Programación",        null,     new Horario(19, 00), new Horario(21, 00), MARTES));
        Comision c8 = comisionService.crear(new Comision("Bernal",           "Informática",     null,                                 "Aula 8", new Horario(18, 00), new Horario(20, 00), MIERCOLES));

        // ─────────────────────────────────────────
        // TUTORES (4)
        // ─────────────────────────────────────────

        Tutor t1 = tutorService.crear(new Tutor("García",    "Martín", "30111222", "martin.garcia@unq.edu.ar",   "password123", Rol.TUTOR));
        Tutor t2 = tutorService.crear(new Tutor("López",     "Sofía",  "30222333", "sofia.lopez@unq.edu.ar",     "password123", Rol.TUTOR));
        Tutor t3 = tutorService.crear(new Tutor("Martínez",  "Diego",  "30333444", "diego.martinez@unq.edu.ar",  "password123", Rol.TUTOR));
        Tutor t4 = tutorService.crear(new Tutor("Fernández", "Laura",  "30444555", "laura.fernandez@unq.edu.ar", "password123", Rol.TUTOR));

        // t1 → c1, c2, c3  |  t2 → c4  |  t3 → c5, c6, c7  |  t4 → sin comisiones  |  c8 → sin tutor
        comisionService.agregarTutorAComisionesPorId(t1.getId(), List.of(c1.getId(), c2.getId(), c3.getId()));
        comisionService.agregarTutorAComision(t2.getId(), c4.getId());
        comisionService.agregarTutorAComisionesPorId(t3.getId(), List.of(c5.getId(), c6.getId(), c7.getId()));

        // ─────────────────────────────────────────
        // ESTUDIANTES ACTIVOS (8)
        // ─────────────────────────────────────────

        Estudiante e1 = estudianteService.crear(new Estudiante("Rodríguez", "Juan",      "40100001", "juan.rodriguez@gmail.com",   "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e2 = estudianteService.crear(new Estudiante("Pérez",     "María",     "40100002", "maria.perez@gmail.com",      "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        Estudiante e3 = estudianteService.crear(new Estudiante("González",  "Carlos",    "40100003", "carlos.gonzalez@gmail.com",  "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e4 = estudianteService.crear(new Estudiante("Sánchez",   "Ana",       "40100004", "ana.sanchez@gmail.com",      "pass123", Rol.ESTUDIANTE, "Trabajo Social"));
        Estudiante e5 = estudianteService.crear(new Estudiante("Ramírez",   "Lucas",     "40100005", "lucas.ramirez@gmail.com",    "pass123", Rol.ESTUDIANTE, "Tecnicatura en Artes Audiovisuales"));
        Estudiante e6 = estudianteService.crear(new Estudiante("Torres",    "Valentina", "40100006", "valentina.torres@gmail.com", "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e7 = estudianteService.crear(new Estudiante("Flores",    "Matías",    "40100007", "matias.flores@gmail.com",    "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        Estudiante e8 = estudianteService.crear(new Estudiante("Ruiz",      "Camila",    "40100008", "camila.ruiz@gmail.com",      "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));

        // e1, e2, e3 → c1  |  e6, e7, e8 → c3  |  e4, e5 sin comisión (casos borde)
        estudianteService.cambiarEstudiantesAComision(List.of(e1.getId(), e2.getId(), e3.getId()), c1.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(e6.getId(), e7.getId(), e8.getId()), c3.getId());

        // ─────────────────────────────────────────
        // ESTUDIANTES DADOS DE BAJA (4)
        // Se crean → se asignan a comisión → se dan de baja
        // ─────────────────────────────────────────

        Estudiante b1 = estudianteService.crear(new Estudiante("Díaz",    "Nicolás",   "40100009", "nicolas.diaz@gmail.com",      "pass123", Rol.ESTUDIANTE, "Trabajo Social"));
        Estudiante b2 = estudianteService.crear(new Estudiante("Morales", "Florencia", "40100010", "florencia.morales@gmail.com", "pass123", Rol.ESTUDIANTE, "Tecnicatura en Artes Audiovisuales"));
        Estudiante b3 = estudianteService.crear(new Estudiante("Jiménez", "Santiago",  "40100011", "santiago.jimenez@gmail.com",  "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante b4 = estudianteService.crear(new Estudiante("Vargas",  "Lucía",     "40100012", "lucia.vargas@gmail.com",      "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));

        // Se asignan a comisiones (requisito del service: debe tener comisión para poder darse de baja)
        estudianteService.cambiarEstudiantesAComision(List.of(b1.getId(), b2.getId()), c2.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(b3.getId(), b4.getId()), c4.getId());

        // Se dan de baja con distintos motivos para cubrir variedad de casos en la UI
        estudianteService.darseDeBaja(b1.getId(), new FormularioBaja(
                MotivoBaja.OTRO,
                "Decido no continuar con la cursada.",
                LocalDate.now().minusDays(10),
                null  // el service lo setea internamente
        ));
        estudianteService.darseDeBaja(b2.getId(), new FormularioBaja(
                MotivoBaja.CAMBIO_INSTITUCION,
                "Cambié a otra carrera en una universidad diferente.",
                LocalDate.now().minusDays(7),
                null
        ));
        estudianteService.darseDeBaja(b3.getId(), new FormularioBaja(
                MotivoBaja.MOTIVOS_PERSONALES,
                "Problemas familiares que me impiden continuar.",
                LocalDate.now().minusDays(3),
                null
        ));
        estudianteService.darseDeBaja(b4.getId(), new FormularioBaja(
                MotivoBaja.FALTA_DE_TIEMPO,
                "",
                LocalDate.now().minusDays(1),
                null
        ));

        // ─────────────────────────────────────────
        // EVENTOS DE CURSADA (6)
        // 2 semanas × 3 días (lunes, miércoles, viernes)
        // Comienzan el lunes siguiente a la fecha actual de ejecución
        // Son globales (esGlobal = true, aplican a todas las comisiones)
        // ─────────────────────────────────────────

        LocalDate hoy = LocalDate.now();
        int diasHastaLunes = (DayOfWeek.MONDAY.getValue() - hoy.getDayOfWeek().getValue() + 7) % 7;
        if (diasHastaLunes == 0) diasHastaLunes = 7; // si hoy es lunes, el siguiente es en 7 días
        LocalDate primerLunes = hoy.plusDays(diasHastaLunes);

        // Semana 1
        LocalDate s1Lunes    = primerLunes;
        LocalDate s1Miercoles = primerLunes.plusDays(2);
        LocalDate s1Viernes   = primerLunes.plusDays(4);
        // Semana 2
        LocalDate s2Lunes     = primerLunes.plusDays(7);
        LocalDate s2Miercoles = primerLunes.plusDays(9);
        LocalDate s2Viernes   = primerLunes.plusDays(11);

        eventoService.crear(new Evento(null, "Encuentro 1", "Primer encuentro presencial del taller.",  s1Lunes,     null, null, true));
        eventoService.crear(new Evento(null, "Encuentro 2", "Segundo encuentro presencial del taller.", s1Miercoles, null, null, true));
        eventoService.crear(new Evento(null, "Encuentro 3", "Tercer encuentro presencial del taller.",  s1Viernes,   null, null, true));
        eventoService.crear(new Evento(null, "Encuentro 4", "Cuarto encuentro presencial del taller.",  s2Lunes,     null, null, true));
        eventoService.crear(new Evento(null, "Encuentro 5", "Quinto encuentro presencial del taller.",  s2Miercoles, null, null, true));
        eventoService.crear(new Evento(null, "Encuentro 6", "Último encuentro presencial del taller.",  s2Viernes,   null, null, true));

        System.out.println("✅ DataInitializer: datos de prueba cargados correctamente.");
        System.out.println("   → 8 comisiones | 4 tutores | 8 estudiantes activos | 4 dados de baja | 6 eventos");
        System.out.println("   → Cursada: " + s1Lunes + " → " + s2Viernes);
    }
}
