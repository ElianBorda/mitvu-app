package com.unq.mitvu.config;

import com.unq.mitvu.model.*;
import com.unq.mitvu.service.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.unq.mitvu.model.DiaHabil.*;
import static com.unq.mitvu.model.TipoDeAsistencia.*;

@Component
@Profile({"dev", "test"})
public class DataInitializer implements ApplicationRunner {

    private final AdministradorService administradorService;
    private final ComisionService comisionService;
    private final TutorService tutorService;
    private final EstudianteService estudianteService;
    private final EventoService eventoService;
    private final AnuncioService anuncioService;
    private final FormularioFeedbackService feedbackService;
    private final SolicitudTutorService solicitudTutorService;

    public DataInitializer(AdministradorService administradorService,
                           ComisionService comisionService,
                           TutorService tutorService,
                           EstudianteService estudianteService,
                           EventoService eventoService,
                           AnuncioService anuncioService,
                           FormularioFeedbackService feedbackService,
                           SolicitudTutorService solicitudTutorService) {
        this.administradorService = administradorService;
        this.comisionService = comisionService;
        this.tutorService = tutorService;
        this.estudianteService = estudianteService;
        this.eventoService = eventoService;
        this.anuncioService = anuncioService;
        this.feedbackService = feedbackService;
        this.solicitudTutorService = solicitudTutorService;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // HELPER: registra o reemplaza la asistencia de un estudiante en una fecha
    // Se llama directamente al service para evitar el envío de mensajes Rabbit
    // que dispara el controller al detectar faltas >= 2.
    // ─────────────────────────────────────────────────────────────────────────
    private void asistencia(Estudiante est, LocalDate fecha, TipoDeAsistencia tipo) {
        estudianteService.pasarAsistenciaDeEstudiante(est.getId(), new Asistencia(fecha, tipo, null));
    }

    @Override
    public void run(ApplicationArguments args) {

        // ── LIMPIEZA ──────────────────────────────────────────────────────────
        feedbackService.eliminarTodo();
        solicitudTutorService.eliminarTodo();
        anuncioService.eliminarTodo();
        eventoService.eliminarTodo();
        estudianteService.eliminarTodasLasComisionesDeTodosLosEstudiantes();
        estudianteService.eliminarTodo();
        comisionService.eliminarTodosLosTutoresDeTodasLasComisiones();
        comisionService.eliminarTodo();
        tutorService.eliminarTodo();
        administradorService.eliminarTodo();

        // ── FECHAS ────────────────────────────────────────────────────────────
        // Los 3 encuentros YA realizados (pasado)
        LocalDate hoy       = LocalDate.now();
        LocalDate enc1Past  = hoy.minusDays(14);
        LocalDate enc2Past  = hoy.minusDays(12);
        LocalDate enc3Past  = hoy.minusDays(10);

        // Los 3 encuentros que QUEDAN en el calendario (futuro)
        int diasHastaLunes = (DayOfWeek.MONDAY.getValue() - hoy.getDayOfWeek().getValue() + 7) % 7;
        if (diasHastaLunes == 0) diasHastaLunes = 7;
        LocalDate primerLunes  = hoy.plusDays(diasHastaLunes);
        LocalDate enc4Future   = primerLunes;
        LocalDate enc5Future   = primerLunes.plusDays(2);
        LocalDate enc6Future   = primerLunes.plusDays(4);

        // Fechas auxiliares para eventos de comisión (segunda semana futura)
        LocalDate semana2Lunes  = primerLunes.plusDays(7);
        LocalDate semana2Mierc  = primerLunes.plusDays(9);
        LocalDate semana2Viernes = primerLunes.plusDays(11);

        // ── ADMINISTRADORES (2) ───────────────────────────────────────────────
        Administrador a1 = administradorService.crear(new Administrador("Borda",  "Elián",   "42997562", "eliancamiloalejandro@gmail.com", "admin1234"));
        Administrador a2 = administradorService.crear(new Administrador("Ferro",  "Ignacio", "44966154", "ignacioferro.if@gmail.com",      "admin1234"));

        // ── COMISIONES (10) ───────────────────────────────────────────────────
        Comision c1  = comisionService.crear(new Comision("Bernal",           "Informática",      "Tecnicatura en Programación",        "Aula 1",  new Horario(17, 0), new Horario(19, 0), LUNES));
        Comision c2  = comisionService.crear(new Comision("Bernal",           "Informática",      "Licenciatura en Sistemas",           "Aula 2",  new Horario(19, 0), new Horario(21, 0), MARTES));
        Comision c3  = comisionService.crear(new Comision("Bernal",           "Informática",      "Tecnicatura en Programación",        "Aula 3",  new Horario(17, 0), new Horario(19, 0), MIERCOLES));
        Comision c4  = comisionService.crear(new Comision("Florencio Varela", "Informática",      "Licenciatura en Sistemas",           "Aula 4",  new Horario(18, 0), new Horario(20, 0), LUNES));
        Comision c5  = comisionService.crear(new Comision("Florencio Varela", "Informática",      "Tecnicatura en Programación",        null,      new Horario(17, 0), new Horario(19, 0), JUEVES));
        Comision c6  = comisionService.crear(new Comision("Berazategui",      "Ciencias Sociales","Trabajo Social",                    "Aula 6",  new Horario(17, 0), new Horario(19, 0), VIERNES));
        Comision c7  = comisionService.crear(new Comision("Berazategui",      "Informática",      "Tecnicatura en Programación",        null,      new Horario(19, 0), new Horario(21, 0), MARTES));
        Comision c8  = comisionService.crear(new Comision("Bernal",           "Arte y Cultura",   "Tecnicatura en Artes Audiovisuales", "Aula 8",  new Horario(20, 0), new Horario(22, 0), VIERNES));
        Comision c9  = comisionService.crear(new Comision("Florencio Varela", "Ciencias Sociales","Trabajo Social",                    "Aula 9",  new Horario(18, 0), new Horario(20, 0), MIERCOLES));
        Comision c10 = comisionService.crear(new Comision("Berazategui",      "Arte y Cultura",   null,                                 null,      new Horario(17, 0), new Horario(19, 0), JUEVES));

        // ── TUTORES (5) ───────────────────────────────────────────────────────
        // t1 → c1, c2, c3  |  t2 → c4, c5  |  t3 → c6, c7  |  t4 → c8, c9
        // t5 → sin comisiones (caso borde)  |  c10 → sin tutor (caso borde)
        Tutor t1 = tutorService.crear(new Tutor("García",    "Martín",   "30111222", "martin.garcia@unq.edu.ar",    "password123", Rol.TUTOR));
        Tutor t2 = tutorService.crear(new Tutor("López",     "Sofía",    "30222333", "sofia.lopez@unq.edu.ar",      "password123", Rol.TUTOR));
        Tutor t3 = tutorService.crear(new Tutor("Martínez",  "Diego",    "30333444", "diego.martinez@unq.edu.ar",   "password123", Rol.TUTOR));
        Tutor t4 = tutorService.crear(new Tutor("Fernández", "Laura",    "30444555", "laura.fernandez@unq.edu.ar",  "password123", Rol.TUTOR));
        Tutor t5 = tutorService.crear(new Tutor("Romero",    "Claudia",  "30555666", "claudia.romero@unq.edu.ar",   "password123", Rol.TUTOR));

        comisionService.agregarTutorAComisionesPorId(t1.getId(), List.of(c1.getId(), c2.getId(), c3.getId()));
        comisionService.agregarTutorAComisionesPorId(t2.getId(), List.of(c4.getId(), c5.getId()));
        comisionService.agregarTutorAComisionesPorId(t3.getId(), List.of(c6.getId(), c7.getId()));
        comisionService.agregarTutorAComisionesPorId(t4.getId(), List.of(c8.getId(), c9.getId()));

        // ── ESTUDIANTES ACTIVOS (30) ──────────────────────────────────────────
        // c1 (4 estudiantes) — Asistencia alta ~83%
        Estudiante e1  = estudianteService.crear(new Estudiante("Rodríguez", "Juan",      "40100001", "juan.rodriguez@gmail.com",      "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e2  = estudianteService.crear(new Estudiante("Pérez",     "María",     "40100002", "maria.perez@gmail.com",         "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        Estudiante e3  = estudianteService.crear(new Estudiante("González",  "Carlos",    "40100003", "carlos.gonzalez@gmail.com",     "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e4  = estudianteService.crear(new Estudiante("Sánchez",   "Ana",       "40100004", "ana.sanchez@gmail.com",         "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        // c2 (3 estudiantes) — Asistencia media ~67%
        Estudiante e5  = estudianteService.crear(new Estudiante("Ramírez",   "Lucas",     "40100005", "lucas.ramirez@gmail.com",       "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        Estudiante e6  = estudianteService.crear(new Estudiante("Torres",    "Valentina", "40100006", "valentina.torres@gmail.com",    "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        Estudiante e7  = estudianteService.crear(new Estudiante("Flores",    "Matías",    "40100007", "matias.flores@gmail.com",       "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        // c3 (4 estudiantes) — Asistencia muy alta ~92%
        Estudiante e8  = estudianteService.crear(new Estudiante("Ruiz",      "Camila",    "40100008", "camila.ruiz@gmail.com",         "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e9  = estudianteService.crear(new Estudiante("Díaz",      "Marcos",    "40100009", "marcos.diaz@gmail.com",         "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e10 = estudianteService.crear(new Estudiante("Morales",   "Florencia", "40100010", "florencia.morales@gmail.com",   "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e11 = estudianteService.crear(new Estudiante("Jiménez",   "Santiago",  "40100011", "santiago.jimenez@gmail.com",    "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        // c4 (3 estudiantes) — Asistencia baja ~56%
        Estudiante e12 = estudianteService.crear(new Estudiante("Vargas",    "Lucía",     "40100012", "lucia.vargas@gmail.com",        "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        Estudiante e13 = estudianteService.crear(new Estudiante("Castro",    "Tomás",     "40100013", "tomas.castro@gmail.com",        "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        Estudiante e14 = estudianteService.crear(new Estudiante("Romero",    "Julieta",   "40100014", "julieta.romero@gmail.com",      "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        // c5 (3 estudiantes) — Asistencia media ~67%
        Estudiante e15 = estudianteService.crear(new Estudiante("Herrera",   "Agustín",   "40100015", "agustin.herrera@gmail.com",     "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e16 = estudianteService.crear(new Estudiante("Medina",    "Abril",     "40100016", "abril.medina@gmail.com",        "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e17 = estudianteService.crear(new Estudiante("Reyes",     "Felipe",    "40100017", "felipe.reyes@gmail.com",        "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        // c6 (3 estudiantes) — Asistencia media-alta ~78%
        Estudiante e18 = estudianteService.crear(new Estudiante("Ortega",    "Micaela",   "40100018", "micaela.ortega@gmail.com",      "pass123", Rol.ESTUDIANTE, "Trabajo Social"));
        Estudiante e19 = estudianteService.crear(new Estudiante("Navarro",   "Bruno",     "40100019", "bruno.navarro@gmail.com",       "pass123", Rol.ESTUDIANTE, "Trabajo Social"));
        Estudiante e20 = estudianteService.crear(new Estudiante("Silva",     "Emilia",    "40100020", "emilia.silva@gmail.com",        "pass123", Rol.ESTUDIANTE, "Trabajo Social"));
        // c7 (3 estudiantes) — Asistencia alta ~89%
        Estudiante e21 = estudianteService.crear(new Estudiante("Mendoza",   "Sofía",     "40100021", "sofia.mendoza@gmail.com",       "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e22 = estudianteService.crear(new Estudiante("Acosta",    "Ramiro",    "40100022", "ramiro.acosta@gmail.com",       "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante e23 = estudianteService.crear(new Estudiante("Vega",      "Paula",     "40100023", "paula.vega@gmail.com",          "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        // c8 (3 estudiantes) — Asistencia media ~67%
        Estudiante e24 = estudianteService.crear(new Estudiante("Ríos",      "Andrés",    "40100024", "andres.rios@gmail.com",         "pass123", Rol.ESTUDIANTE, "Tecnicatura en Artes Audiovisuales"));
        Estudiante e25 = estudianteService.crear(new Estudiante("Ponce",     "Natalia",   "40100025", "natalia.ponce@gmail.com",       "pass123", Rol.ESTUDIANTE, "Tecnicatura en Artes Audiovisuales"));
        Estudiante e26 = estudianteService.crear(new Estudiante("Cabrera",   "Ignacio",   "40100026", "ignacio.cabrera@gmail.com",     "pass123", Rol.ESTUDIANTE, "Tecnicatura en Artes Audiovisuales"));
        // c9 (2 estudiantes) — Asistencia alta ~83%
        Estudiante e27 = estudianteService.crear(new Estudiante("Ibáñez",    "Valeria",   "40100027", "valeria.ibanez@gmail.com",      "pass123", Rol.ESTUDIANTE, "Trabajo Social"));
        Estudiante e28 = estudianteService.crear(new Estudiante("Suárez",    "Mateo",     "40100028", "mateo.suarez@gmail.com",        "pass123", Rol.ESTUDIANTE, "Trabajo Social"));
        // c10 (2 estudiantes) — Asistencia media ~67%
        Estudiante e29 = estudianteService.crear(new Estudiante("Ferreira",  "Daniela",   "40100029", "daniela.ferreira@gmail.com",    "pass123", Rol.ESTUDIANTE, "Tecnicatura en Artes Audiovisuales"));
        Estudiante e30 = estudianteService.crear(new Estudiante("Molina",    "Ezequiel",  "40100030", "ezequiel.molina@gmail.com",     "pass123", Rol.ESTUDIANTE, "Tecnicatura en Artes Audiovisuales"));

        // Asignaciones a comisiones
        estudianteService.cambiarEstudiantesAComision(List.of(e1.getId(),  e2.getId(),  e3.getId(),  e4.getId()),  c1.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(e5.getId(),  e6.getId(),  e7.getId()),               c2.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(e8.getId(),  e9.getId(),  e10.getId(), e11.getId()), c3.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(e12.getId(), e13.getId(), e14.getId()),              c4.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(e15.getId(), e16.getId(), e17.getId()),              c5.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(e18.getId(), e19.getId(), e20.getId()),              c6.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(e21.getId(), e22.getId(), e23.getId()),              c7.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(e24.getId(), e25.getId(), e26.getId()),              c8.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(e27.getId(), e28.getId()),                          c9.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(e29.getId(), e30.getId()),                          c10.getId());

        // ── ESTUDIANTES DADOS DE BAJA (5) ─────────────────────────────────────
        // Se crean → asignan → reciben asistencia de enc1 → se dan de baja
        Estudiante b1 = estudianteService.crear(new Estudiante("Delgado",  "Nicolás",   "40200001", "nicolas.delgado@gmail.com",     "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante b2 = estudianteService.crear(new Estudiante("Guerrero", "Florencia", "40200002", "florencia.guerrero@gmail.com",  "pass123", Rol.ESTUDIANTE, "Licenciatura en Sistemas"));
        Estudiante b3 = estudianteService.crear(new Estudiante("Paredes",  "Sebastián", "40200003", "sebastian.paredes@gmail.com",   "pass123", Rol.ESTUDIANTE, "Tecnicatura en Programación"));
        Estudiante b4 = estudianteService.crear(new Estudiante("Aguilar",  "Valentina", "40200004", "valentina.aguilar@gmail.com",   "pass123", Rol.ESTUDIANTE, "Trabajo Social"));
        Estudiante b5 = estudianteService.crear(new Estudiante("Núñez",    "Joaquín",   "40200005", "joaquin.nunez@gmail.com",       "pass123", Rol.ESTUDIANTE, "Tecnicatura en Artes Audiovisuales"));

        estudianteService.cambiarEstudiantesAComision(List.of(b1.getId(), b2.getId()), c1.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(b3.getId()),             c3.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(b4.getId()),             c6.getId());
        estudianteService.cambiarEstudiantesAComision(List.of(b5.getId()),             c8.getId());

        // Asistencia de los que se darán de baja (solo enc1, abandonaron antes del enc2)
        asistencia(b1, enc1Past, PRESENTE);
        asistencia(b2, enc1Past, AUSENTE);
        asistencia(b3, enc1Past, PRESENTE);
        asistencia(b4, enc1Past, PRESENTE);
        asistencia(b5, enc1Past, AUSENTE);

        estudianteService.darseDeBaja(b1.getId(), new FormularioBaja(MotivoBaja.OTRO,               "Decidió no continuar.",                     hoy.minusDays(11), null));
        estudianteService.darseDeBaja(b2.getId(), new FormularioBaja(MotivoBaja.CAMBIO_INSTITUCION, "Se inscribió en otra universidad.",          hoy.minusDays(11), null));
        estudianteService.darseDeBaja(b3.getId(), new FormularioBaja(MotivoBaja.MOTIVOS_PERSONALES, "Razones de salud.",                         hoy.minusDays(11), null));
        estudianteService.darseDeBaja(b4.getId(), new FormularioBaja(MotivoBaja.FALTA_DE_TIEMPO,    "No puede con los horarios de cursada.",      hoy.minusDays(11), null));
        estudianteService.darseDeBaja(b5.getId(), new FormularioBaja(MotivoBaja.OTRO,               "Problemas económicos para trasladarse.",     hoy.minusDays(11), null));

        // ── ASISTENCIAS DE LOS 3 ENCUENTROS YA REALIZADOS ────────────────────
        // Los patrones están diseñados para dar métricas variadas por comisión.
        // Leyenda: PRESENTE / AUSENTE / AUSENCIA_JUSTIFICADA

        // c2 — Asistencia media ~89% (6P, 3A de 9 posibles)
        asistencia(e5,  enc1Past, PRESENTE); asistencia(e5,  enc2Past, PRESENTE);  asistencia(e5,  enc3Past, PRESENTE);
        asistencia(e6,  enc1Past, PRESENTE); asistencia(e6,  enc2Past, PRESENTE); asistencia(e6,  enc3Past, PRESENTE);
        asistencia(e7,  enc1Past, AUSENTE);  asistencia(e7,  enc2Past, PRESENTE); asistencia(e7,  enc3Past, PRESENTE);

        // c3 — Asistencia muy alta ~100% (11P, 1J de 12 posibles)
        asistencia(e8,  enc1Past, PRESENTE); asistencia(e8,  enc2Past, PRESENTE); asistencia(e8,  enc3Past, PRESENTE);
        asistencia(e9,  enc1Past, PRESENTE); asistencia(e9,  enc2Past, PRESENTE); asistencia(e9,  enc3Past, PRESENTE);
        asistencia(e10, enc1Past, PRESENTE); asistencia(e10, enc2Past, PRESENTE); asistencia(e10, enc3Past, PRESENTE);
        asistencia(e11, enc1Past, AUSENCIA_JUSTIFICADA); asistencia(e11, enc2Past, PRESENTE); asistencia(e11, enc3Past, PRESENTE);

        // c4 — Asistencia baja ~56% (5P, 4A de 9 posibles)
        asistencia(e12, enc1Past, PRESENTE); asistencia(e12, enc2Past, AUSENTE);  asistencia(e12, enc3Past, PRESENTE);
        asistencia(e13, enc1Past, AUSENTE);  asistencia(e13, enc2Past, PRESENTE); asistencia(e13, enc3Past, AUSENTE);
        asistencia(e14, enc1Past, AUSENTE);  asistencia(e14, enc2Past, AUSENTE);  asistencia(e14, enc3Past, PRESENTE);

        // c5 — Asistencia media ~67% (6P, 3A de 9 posibles)
        asistencia(e15, enc1Past, PRESENTE); asistencia(e15, enc2Past, PRESENTE); asistencia(e15, enc3Past, AUSENTE);
        asistencia(e16, enc1Past, PRESENTE); asistencia(e16, enc2Past, AUSENTE);  asistencia(e16, enc3Past, PRESENTE);
        asistencia(e17, enc1Past, AUSENTE);  asistencia(e17, enc2Past, PRESENTE); asistencia(e17, enc3Past, AUSENTE);

        // c6 — Asistencia media-alta ~78% (7P, 1A, 1J de 9 posibles)
        asistencia(e18, enc1Past, PRESENTE); asistencia(e18, enc2Past, PRESENTE); asistencia(e18, enc3Past, PRESENTE);
        asistencia(e19, enc1Past, PRESENTE); asistencia(e19, enc2Past, AUSENCIA_JUSTIFICADA); asistencia(e19, enc3Past, PRESENTE);
        asistencia(e20, enc1Past, PRESENTE); asistencia(e20, enc2Past, AUSENTE);  asistencia(e20, enc3Past, PRESENTE);

        // c7 — Asistencia alta ~89% (8P, 1A de 9 posibles)
        asistencia(e21, enc1Past, PRESENTE); asistencia(e21, enc2Past, PRESENTE); asistencia(e21, enc3Past, PRESENTE);
        asistencia(e22, enc1Past, PRESENTE); asistencia(e22, enc2Past, PRESENTE); asistencia(e22, enc3Past, PRESENTE);
        asistencia(e23, enc1Past, AUSENTE);  asistencia(e23, enc2Past, PRESENTE); asistencia(e23, enc3Past, PRESENTE);

        // c8 — Asistencia media ~100% (6P, 3A de 9 posibles)
        asistencia(e24, enc1Past, PRESENTE); asistencia(e24, enc2Past, PRESENTE);  asistencia(e24, enc3Past, PRESENTE);
        asistencia(e25, enc1Past, PRESENTE); asistencia(e25, enc2Past, PRESENTE); asistencia(e25, enc3Past, PRESENTE);
        asistencia(e26, enc1Past, PRESENTE);  asistencia(e26, enc2Past, PRESENTE); asistencia(e26, enc3Past, PRESENTE);

        // c9 — Asistencia alta ~83% (5P, 1A de 6 posibles)
        asistencia(e27, enc1Past, PRESENTE); asistencia(e27, enc2Past, PRESENTE); asistencia(e27, enc3Past, PRESENTE);
        asistencia(e28, enc1Past, PRESENTE); asistencia(e28, enc2Past, AUSENTE);  asistencia(e28, enc3Past, PRESENTE);

        // c10 — Asistencia media ~67% (4P, 2A de 6 posibles)
        asistencia(e29, enc1Past, PRESENTE); asistencia(e29, enc2Past, PRESENTE); asistencia(e29, enc3Past, AUSENTE);
        asistencia(e30, enc1Past, AUSENTE);  asistencia(e30, enc2Past, PRESENTE); asistencia(e30, enc3Past, PRESENTE);

        // ── EVENTOS: 3 ENCUENTROS RESTANTES (globales, futuro) ────────────────
        eventoService.crear(new Evento(null, "Encuentro 1", "Primer encuentro presencial del taller.",  enc1Past,   null,       null,       true));
        eventoService.crear(new Evento(null, "Encuentro 2", "Segundo encuentro presencial del taller.",  enc2Past,   null,       null,       true));
        eventoService.crear(new Evento(null, "Encuentro 3", "Tercer encuentro presencial del taller.",  enc3Past,   null,       null,       true));
        eventoService.crear(new Evento(null, "Encuentro 4", "Cuarto encuentro presencial del taller.",  enc4Future,   null,       null,       true));
        eventoService.crear(new Evento(null, "Encuentro 5", "Quinto encuentro presencial del taller.",  enc5Future,   null,       null,       true));
        eventoService.crear(new Evento(null, "Encuentro 6", "Último encuentro presencial del taller.",  enc6Future,   null,       null,       true));

        // ── EVENTOS POR COMISIÓN (1 por comisión, segunda semana futura) ──────
        eventoService.crear(new Evento(null, "Entrega de reflexión — C1", "Reflexión escrita sobre el primer mes en la universidad.",  semana2Lunes,   c1.getId(),  t1.getId(), false));
        eventoService.crear(new Evento(null, "Actividad integradora — C2", "Dinámica grupal sobre la vida universitaria.",            semana2Lunes,   c2.getId(),  t1.getId(), false));
        eventoService.crear(new Evento(null, "Trabajo en grupo — C3",      "Presentación de grupos sobre facultades de la UNQ.",      semana2Mierc,   c3.getId(),  t1.getId(), false));
        eventoService.crear(new Evento(null, "Tutoría de apoyo — C4",      "Encuentro adicional para estudiantes con faltas.",        semana2Mierc,   c4.getId(),  t2.getId(), false));
        eventoService.crear(new Evento(null, "Muestra de proyectos — C5",  "Exposición de proyectos del primer tramo del taller.",    semana2Viernes, c5.getId(),  t2.getId(), false));
        eventoService.crear(new Evento(null, "Presentación grupal — C6",   "Cada grupo expone sus aprendizajes de la primera semana.", semana2Lunes,  c6.getId(),  t3.getId(), false));
        eventoService.crear(new Evento(null, "Debate interno — C7",        "Debate sobre recursos y servicios de la universidad.",    semana2Mierc,   c7.getId(),  t3.getId(), false));
        eventoService.crear(new Evento(null, "Cierre de primera etapa — C8","Actividad de cierre y reflexión colectiva.",             semana2Viernes, c8.getId(),  t4.getId(), false));
        eventoService.crear(new Evento(null, "Reunión de seguimiento — C9", "Seguimiento personalizado de cada estudiante.",          semana2Mierc,   c9.getId(),  t4.getId(), false));
        eventoService.crear(new Evento(null, "Actividad especial — C10",    "Encuentro artístico y de expresión libre.",              semana2Viernes, c10.getId(), a1.getId(), false));

        // ── ANUNCIOS (1 global + 1 por comisión) ─────────────────────────────
        anuncioService.crear(new Anuncio(null, "¡Bienvenidos al Taller de Vida Universitaria!",
                "Esperamos que estas semanas les sirvan para conocer la universidad y sentirse parte de ella. ¡Mucho ánimo!",
                hoy, null, a1.getId()));

        anuncioService.crear(new Anuncio(null, "Cambio de aula para el próximo encuentro",
                "El Encuentro 4 de esta comisión se realizará en el Aula Magna. Por favor lleguen 10 minutos antes.",
                hoy, c1.getId(), t1.getId()));
        anuncioService.crear(new Anuncio(null, "Recordatorio: material del Encuentro 2",
                "Les recordamos que deben traer una copia del texto distribuido en el segundo encuentro para la próxima clase.",
                hoy, c2.getId(), t1.getId()));
        anuncioService.crear(new Anuncio(null, "¡Excelente asistencia, comisión!",
                "Queremos felicitarlos por el gran compromiso demostrado en los primeros tres encuentros. ¡Sigan así!",
                hoy, c3.getId(), t1.getId()));
        anuncioService.crear(new Anuncio(null, "Atención: situación de asistencias",
                "Algunos integrantes de la comisión tienen faltas que comprometen su continuidad. Los contactaremos individualmente.",
                hoy, c4.getId(), t2.getId()));
        anuncioService.crear(new Anuncio(null, "Información sobre la segunda semana",
                "En el Encuentro 4 comenzaremos con la segunda etapa del taller. Les pedimos que repasen los materiales del campus.",
                hoy, c5.getId(), t2.getId()));
        anuncioService.crear(new Anuncio(null, "Encuesta de satisfacción disponible",
                "Les pedimos que completen la encuesta de satisfacción del primer tramo del taller. El link está en el campus virtual.",
                hoy, c6.getId(), t3.getId()));
        anuncioService.crear(new Anuncio(null, "¡Gran participación en el Encuentro 3!",
                "Queremos destacar la excelente participación de toda la comisión en el último encuentro. ¡Fue muy enriquecedor!",
                hoy, c7.getId(), t3.getId()));
        anuncioService.crear(new Anuncio(null, "Materiales de apoyo disponibles",
                "Subimos al campus virtual un resumen de los tres primeros encuentros para quienes quieran repasar los contenidos.",
                hoy, c8.getId(), t4.getId()));
        anuncioService.crear(new Anuncio(null, "Reunión de cierre de primera semana",
                "Este sábado tendremos un encuentro opcional de cierre de la primera etapa. No es obligatorio pero sí muy recomendable.",
                hoy, c9.getId(), t4.getId()));
        anuncioService.crear(new Anuncio(null, "Aviso importante sobre el próximo encuentro",
                "El Encuentro 4 contará con la visita de una autoridad de la universidad. Les pedimos puntualidad y predisposición.",
                hoy, c10.getId(), a1.getId()));

        // ── FORMULARIOS DE FEEDBACK (20) ─────────────────────────────────────
        // Distribuidos entre los tutores y comisiones existentes, con variedad
        // en puntajes, respuestas de enums y comentarios cualitativos.

        // — t1 / c1 (4 feedbacks)
        feedbackService.guardarFeedback(new FormularioFeedback(null, t1.getId(), c1.getId(), null,
                5, 4, 5, 5,
                UtilidadEncuentro.MUCHO, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                4, true,
                "Muy buen manejo del grupo, explicaciones claras y motivadoras.",
                "Podría incorporar más actividades prácticas.",
                null));

        feedbackService.guardarFeedback(new FormularioFeedback(null, t1.getId(), c1.getId(), null,
                4, 5, 4, 4,
                UtilidadEncuentro.BASTANTE, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                5, true,
                "Siempre dispuesto a responder dudas fuera del horario.",
                null,
                "El taller me ayudó a adaptarme mucho más rápido a la vida universitaria."));

        feedbackService.guardarFeedback(new FormularioFeedback(null, t1.getId(), c1.getId(), null,
                3, 3, 4, 3,
                UtilidadEncuentro.POCO, FrecuenciaEncuentro.FALTARON_ENCUENTROS, RespuestaCerrada.A_VECES,
                3, false,
                null,
                "Los encuentros se sintieron algo improvisados, faltó más estructura.",
                "Espero que en próximas ediciones haya más contenido sobre recursos universitarios."));

        feedbackService.guardarFeedback(new FormularioFeedback(null, t1.getId(), c1.getId(), null,
                5, 5, 5, 5,
                UtilidadEncuentro.MUCHO, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                5, true,
                "Excelente tutor, muy comprometido con el grupo.",
                null,
                null));

        // — t1 / c2 (2 feedbacks)
        feedbackService.guardarFeedback(new FormularioFeedback(null, t1.getId(), c2.getId(), null,
                4, 4, 5, 4,
                UtilidadEncuentro.BASTANTE, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                4, true,
                "Muy buen clima en el grupo, el tutor supo generar confianza desde el primer encuentro.",
                "Más espacio para hablar sobre el plan de estudios.",
                null));

        feedbackService.guardarFeedback(new FormularioFeedback(null, t1.getId(), c2.getId(), null,
                2, 3, 3, 2,
                UtilidadEncuentro.POCO, FrecuenciaEncuentro.FALTARON_ENCUENTROS, RespuestaCerrada.NO,
                2, false,
                null,
                "Los horarios no se respetaban con regularidad.",
                "Siento que el taller podría ser más aprovechado con mejor organización."));

        // — t1 / c3 (2 feedbacks)
        feedbackService.guardarFeedback(new FormularioFeedback(null, t1.getId(), c3.getId(), null,
                5, 5, 5, 5,
                UtilidadEncuentro.MUCHO, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                5, true,
                "Una de las mejores experiencias del primer cuatrimestre.",
                null,
                "Recomendaría este taller a todos los ingresantes sin dudarlo."));

        feedbackService.guardarFeedback(new FormularioFeedback(null, t1.getId(), c3.getId(), null,
                4, 4, 4, 4,
                UtilidadEncuentro.BASTANTE, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                4, true,
                "El tutor explicó muy bien el funcionamiento de la universidad.",
                "Podría haber más instancias para hablar entre compañeros.",
                null));

        // — t2 / c4 (2 feedbacks)
        feedbackService.guardarFeedback(new FormularioFeedback(null, t2.getId(), c4.getId(), null,
                3, 2, 3, 3,
                UtilidadEncuentro.POCO, FrecuenciaEncuentro.FALTARON_ENCUENTROS, RespuestaCerrada.A_VECES,
                3, false,
                null,
                "El tutor tardaba en responder los mensajes del grupo.",
                "Esperaba más acompañamiento para los que venimos de lejos."));

        feedbackService.guardarFeedback(new FormularioFeedback(null, t2.getId(), c4.getId(), null,
                4, 3, 4, 4,
                UtilidadEncuentro.BASTANTE, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                3, true,
                "Buena disposición general, aunque a veces le faltó energía.",
                "Más dinamismo en las actividades grupales.",
                null));

        // — t2 / c5 (2 feedbacks)
        feedbackService.guardarFeedback(new FormularioFeedback(null, t2.getId(), c5.getId(), null,
                5, 4, 5, 5,
                UtilidadEncuentro.MUCHO, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                4, true,
                "Excelente manejo del grupo diverso que tenemos en esta comisión.",
                null,
                "Me sentí muy contenido/a durante todo el proceso de adaptación."));

        feedbackService.guardarFeedback(new FormularioFeedback(null, t2.getId(), c5.getId(), null,
                3, 4, 3, 3,
                UtilidadEncuentro.POCO, FrecuenciaEncuentro.FUERON_DEMASIADOS, RespuestaCerrada.A_VECES,
                3, false,
                null,
                "Los encuentros se repitieron demasiado en los temas tratados.",
                "Quizás con menos encuentros pero más intensos sería mejor."));

        // — t3 / c6 (2 feedbacks)
        feedbackService.guardarFeedback(new FormularioFeedback(null, t3.getId(), c6.getId(), null,
                4, 5, 4, 4,
                UtilidadEncuentro.BASTANTE, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                4, true,
                "El tutor siempre respondió rápido y con mucha amabilidad.",
                "Faltó profundizar en los recursos de bienestar estudiantil.",
                null));

        feedbackService.guardarFeedback(new FormularioFeedback(null, t3.getId(), c6.getId(), null,
                5, 5, 5, 5,
                UtilidadEncuentro.MUCHO, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                5, true,
                "Increíble la dedicación y la calidez humana del tutor.",
                null,
                "Sin dudas recomendaría la experiencia a mis compañeros de Trabajo Social."));

        // — t3 / c7 (2 feedbacks)
        feedbackService.guardarFeedback(new FormularioFeedback(null, t3.getId(), c7.getId(), null,
                4, 4, 5, 4,
                UtilidadEncuentro.BASTANTE, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                4, true,
                "Muy buen ambiente en la comisión, el tutor supo unir al grupo.",
                "Incorporar una visita guiada por el campus sería genial.",
                null));

        feedbackService.guardarFeedback(new FormularioFeedback(null, t3.getId(), c7.getId(), null,
                2, 2, 3, 2,
                UtilidadEncuentro.NADA, FrecuenciaEncuentro.FUERON_DEMASIADOS, RespuestaCerrada.NO,
                2, false,
                null,
                "Los encuentros no aportaron información nueva ni relevante para mí.",
                "Sería útil poder elegir a qué actividades asistir según los intereses de cada uno."));

        // — t4 / c8 (2 feedbacks)
        feedbackService.guardarFeedback(new FormularioFeedback(null, t4.getId(), c8.getId(), null,
                5, 5, 5, 5,
                UtilidadEncuentro.MUCHO, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                5, true,
                "La tutora estuvo siempre presente y disponible para el grupo.",
                null,
                "El taller fue fundamental para animarme a participar más en la facultad."));

        feedbackService.guardarFeedback(new FormularioFeedback(null, t4.getId(), c8.getId(), null,
                4, 3, 4, 4,
                UtilidadEncuentro.BASTANTE, FrecuenciaEncuentro.FALTARON_ENCUENTROS, RespuestaCerrada.SI,
                3, true,
                "El taller fue positivo en general.",
                "Me hubiera gustado tener un encuentro más al final para cerrar la experiencia.",
                null));

        // — t4 / c9 (2 feedbacks)
        feedbackService.guardarFeedback(new FormularioFeedback(null, t4.getId(), c9.getId(), null,
                3, 3, 4, 3,
                UtilidadEncuentro.POCO, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.A_VECES,
                3, false,
                "Buen trato personal.",
                "Necesitaría más seguimiento individual entre encuentros.",
                "Trabajo Social tiene necesidades específicas que no siempre se contemplaron."));

        feedbackService.guardarFeedback(new FormularioFeedback(null, t4.getId(), c9.getId(), null,
                5, 4, 5, 5,
                UtilidadEncuentro.MUCHO, FrecuenciaEncuentro.FUERON_SUFICIENTES, RespuestaCerrada.SI,
                4, true,
                "Muy buena experiencia, el taller me ayudó a conectar con mis compañeros.",
                "Podría haber más actividades interdisciplinarias con otras comisiones.",
                null));

        // ── SOLICITUDES DE TUTORES (15) ───────────────────────────────────────
        // Mezcla de estados: PENDIENTE, APROBADA y RECHAZADA.
        // El service setea automáticamente fechaPostulacion y estadoSolicitud=PENDIENTE,
        // por lo que las solicitudes no-pendientes se actualizan manualmente después.

        SolicitudTutor s1 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Aguirre", "Valentina", "valentina.aguirre@gmail.com", "41500001",
                "Licenciatura en Sistemas", EstadoAcademico.ESTUDIANTE_AVANZADO,
                false, false, EstadoDiplomatura.CURSANDO, null, null));

        SolicitudTutor s2 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Benítez", "Facundo", "facundo.benitez@gmail.com", "38700002",
                "Trabajo Social", EstadoAcademico.EGRESADO,
                true, true, EstadoDiplomatura.REALIZADA, null, null));

        SolicitudTutor s3 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Cárdenas", "Milagros", "milagros.cardenas@gmail.com", "42100003",
                "Tecnicatura en Programación", EstadoAcademico.ESTUDIANTE_AVANZADO,
                false, true, EstadoDiplomatura.CURSANDO, null, null));

        SolicitudTutor s4 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Domínguez", "Leandro", "leandro.dominguez@gmail.com", "39300004",
                "Licenciatura en Sistemas", EstadoAcademico.EGRESADO,
                true, false, EstadoDiplomatura.REALIZADA, null, null));

        SolicitudTutor s5 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Espinoza", "Rocío", "rocio.espinoza@gmail.com", "43200005",
                "Tecnicatura en Artes Audiovisuales", EstadoAcademico.ESTUDIANTE_AVANZADO,
                false, false, EstadoDiplomatura.NO_REALIZADA, null, null));

        SolicitudTutor s6 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Figueroa", "Ignacio", "ignacio.figueroa@gmail.com", "40800006",
                "Trabajo Social", EstadoAcademico.EGRESADO,
                true, true, EstadoDiplomatura.REALIZADA, null, null));

        SolicitudTutor s7 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Godoy", "Camila", "camila.godoy@gmail.com", "41900007",
                "Licenciatura en Sistemas", EstadoAcademico.ESTUDIANTE_AVANZADO,
                false, false, EstadoDiplomatura.CURSANDO, null, null));

        SolicitudTutor s8 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Heredia", "Matías", "matias.heredia@gmail.com", "37600008",
                "Tecnicatura en Programación", EstadoAcademico.EGRESADO,
                true, true, EstadoDiplomatura.REALIZADA, null, null));

        SolicitudTutor s9 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Ibarra", "Luciana", "luciana.ibarra@gmail.com", "44000009",
                "Trabajo Social", EstadoAcademico.ESTUDIANTE_AVANZADO,
                false, true, EstadoDiplomatura.CURSANDO, null, null));

        SolicitudTutor s10 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Juárez", "Tomás", "tomas.juarez@gmail.com", "42600010",
                "Tecnicatura en Artes Audiovisuales", EstadoAcademico.EGRESADO,
                true, false, EstadoDiplomatura.REALIZADA, null, null));

        SolicitudTutor s11 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Kramer", "Daniela", "daniela.kramer@gmail.com", "43800011",
                "Licenciatura en Sistemas", EstadoAcademico.ESTUDIANTE_AVANZADO,
                false, false, EstadoDiplomatura.NO_REALIZADA, null, null));

        SolicitudTutor s12 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Luna", "Federico", "federico.luna@gmail.com", "40100012",
                "Tecnicatura en Programación", EstadoAcademico.EGRESADO,
                true, true, EstadoDiplomatura.REALIZADA, null, null));

        SolicitudTutor s13 = solicitudTutorService.crearSolicitud(new SolicitudTutor(null,
                "Mansilla", "Florencia", "florencia.mansilla@gmail.com", "41200013",
                "Trabajo Social", EstadoAcademico.ESTUDIANTE_AVANZADO,
                false, true, EstadoDiplomatura.CURSANDO, null, null));

        // ── RESUMEN ───────────────────────────────────────────────────────────
        System.out.println("✅ DataInitializer: instancia avanzada del taller cargada correctamente.");
        System.out.println("   → 2 admins | 10 comisiones | 5 tutores");
        System.out.println("   → 30 estudiantes activos | 5 dados de baja");
        System.out.println("   → 3 encuentros ya realizados (" + enc1Past + " / " + enc2Past + " / " + enc3Past + ")");
        System.out.println("   → 3 encuentros restantes en calendario (" + enc4Future + " / " + enc5Future + " / " + enc6Future + ")");
        System.out.println("   → 10 eventos de comisión | 11 anuncios");
        System.out.println("   → 20 formularios de feedback | 13 solicitudes de tutores");
        System.out.println("   → Métricas de asistencia por comisión:");
        System.out.println("      c1: ~SIN ASISTENCIA | c2: ~67% | c3: ~92% | c4: ~56% | c5: ~67%");
        System.out.println("      c6: ~78% | c7: ~89% | c8: ~100% | c9: ~83% | c10: ~67%");
    }
}