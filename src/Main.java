import br.com.dio.desafio.dominio.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Curso cursoJava = criarCurso("Curso Java", "Descrição curso Java", 8);
        Curso cursoJavaScript = criarCurso("Curso JavaScript", "Descrição curso JavaScript", 4);
        Curso cursoSpringBoot = criarCurso("Curso Spring Boot", "Descrição curso Spring Boot", 10);

        Mentoria mentoriaJava = criarMentoria("Mentoria de Java", "Descrição mentoria Java", LocalDate.now());

        Bootcamp bootcamp = criarBootcamp(
                "Bootcamp Java Developer",
                "Descrição Bootcamp Java Developer",
                cursoJava, cursoJavaScript, cursoSpringBoot, mentoriaJava
        );

        System.out.println("=".repeat(60));
        System.out.println("BOOTCAMP: " + bootcamp.getNome());
        System.out.println("=".repeat(60));
        System.out.println();

        Dev devCamila = new Dev();
        devCamila.setNome("Camila");
        simularJornadaDev(devCamila, bootcamp, 2);

        System.out.println("\n" + "-".repeat(60) + "\n");

        Dev devJoao = new Dev();
        devJoao.setNome("João");
        simularJornadaDev(devJoao, bootcamp, 3);

        System.out.println("\n" + "=".repeat(60));
        exibirRanking(devCamila, devJoao);
    }

    private static Curso criarCurso(String titulo, String descricao, int cargaHoraria) {
        Curso curso = new Curso();
        curso.setTitulo(titulo);
        curso.setDescricao(descricao);
        curso.setCargaHoraria(cargaHoraria);
        return curso;
    }

    private static Mentoria criarMentoria(String titulo, String descricao, LocalDate data) {
        Mentoria mentoria = new Mentoria();
        mentoria.setTitulo(titulo);
        mentoria.setDescricao(descricao);
        mentoria.setData(data);
        return mentoria;
    }


    private static Bootcamp criarBootcamp(String nome, String descricao, Object... conteudos) {
        Bootcamp bootcamp = new Bootcamp();
        bootcamp.setNome(nome);
        bootcamp.setDescricao(descricao);

        for (Object conteudo : conteudos) {
            bootcamp.getConteudos().add((Conteudo) conteudo);
        }

        return bootcamp;
    }

    private static void simularJornadaDev(Dev dev, Bootcamp bootcamp, int progressoes) {
        System.out.println("👤 DEV: " + dev.getNome());
        System.out.println("-".repeat(60));

        dev.inscreverBootcamp(bootcamp);
        System.out.println("✅ Inscrito no bootcamp com sucesso!");
        System.out.println("\n📚 Conteúdos Inscritos: " + dev.getConteudosInscritos().size());
        dev.getConteudosInscritos().forEach(c -> System.out.println("   • " + c.getTitulo()));

        System.out.println("\n🎯 Progredindo " + progressoes + " conteúdo(s)...");
        for (int i = 0; i < progressoes; i++) {
            dev.progredir();
        }

        System.out.println("\n📊 SITUAÇÃO ATUAL:");
        System.out.println("   📖 Conteúdos Restantes: " + dev.getConteudosInscritos().size());
        dev.getConteudosInscritos().forEach(c -> System.out.println("      • " + c.getTitulo()));

        System.out.println("\n   ✔️  Conteúdos Concluídos: " + dev.getConteudosConcluidos().size());
        dev.getConteudosConcluidos().forEach(c -> System.out.println("      • " + c.getTitulo()));

        System.out.println("\n   ⭐ XP Total: " + dev.calcularTotalXp());
    }

    private static void exibirRanking(Dev... devs) {
        System.out.println("🏆 RANKING DE XP");
        System.out.println("=".repeat(60));

        java.util.Arrays.stream(devs)
                .sorted((d1, d2) -> Double.compare(d2.calcularTotalXp(), d1.calcularTotalXp()))
                .forEach(dev -> {
                    System.out.printf("%-20s | XP: %.2f | Concluídos: %d%n",
                            dev.getNome(),
                            dev.calcularTotalXp(),
                            dev.getConteudosConcluidos().size()
                    );
                });
    }
}