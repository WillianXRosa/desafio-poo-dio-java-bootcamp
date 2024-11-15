package br.com.dio.desafio.dominio;

import java.util.*;

public class Dev {
    private String nome;
    private Set<Conteudo> conteudosInscritos = new LinkedHashSet<>();
    private Set<Conteudo> conteudosConcluidos = new LinkedHashSet<>();
    private static final double XP_MINIMO_CERTIFICACAO = 150; //

    public void inscreverBootcamp(Bootcamp bootcamp){
        this.conteudosInscritos.addAll(bootcamp.getConteudos());
        bootcamp.getDevsInscritos().add(this);
    }

    public void progredir() {
        Optional<Conteudo> conteudo = this.conteudosInscritos.stream().findFirst();
        if(conteudo.isPresent()) {
            this.conteudosConcluidos.add(conteudo.get());
            this.conteudosInscritos.remove(conteudo.get());
        } else {
            System.err.println("Você não está matriculado em nenhum conteúdo!");
        }
    }
    
    /**Inclusão do método para indicação de conteúdos para a conclusão do boot camp**/
    public void indicarConteudos(Bootcamp bootCamp) {
    	Set<Conteudo> indicacoes = bootCamp.recomendarConteudo(this);
    	if (indicacoes.isEmpty()) {
			System.out.println("O aluno " +getNome() + " já concluiu todos os conteúdos do BootCamp");
		}
    	else {
			System.out.print("Recomendamos para o aluno " +getNome());
			indicacoes.forEach(conteudo -> System.out.println(" o curso " + conteudo.getTitulo() + " para a conclusão do BootCamp."));
		}
    	
    }

    public double calcularTotalXp() {
        Iterator<Conteudo> iterator = this.conteudosConcluidos.iterator();
        double soma = 0;
        while(iterator.hasNext()){
            double next = iterator.next().calcularXp();
            soma += next;
        }
        return soma;

      /*return this.conteudosConcluidos
                .stream()
                .mapToDouble(Conteudo::calcularXp)
                .sum();*/
    }
    
    /**Inclusão do metodo para calculo de total de xp para impressão de certificado de conclusão do curso**/
    public boolean calculoCertifacadoConclusao() {
		return calcularTotalXp() >= XP_MINIMO_CERTIFICACAO;
    	
    }
    
    /**Inclusão do metodo para validação e impressão de certificado de conclusão do curso**/
    public String emitirCertificadoConclusao() {
    	if (calculoCertifacadoConclusao()) {
			return "Certificado emitido para " + getNome();
		}
    	else {
    		return "O Aluno: " + getNome() + " não conseguir XP suficiente para impresão de certificado";
    		
    	}
    }   
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Conteudo> getConteudosInscritos() {
        return conteudosInscritos;
    }

    public void setConteudosInscritos(Set<Conteudo> conteudosInscritos) {
        this.conteudosInscritos = conteudosInscritos;
    }

    public Set<Conteudo> getConteudosConcluidos() {
        return conteudosConcluidos;
    }

    public void setConteudosConcluidos(Set<Conteudo> conteudosConcluidos) {
        this.conteudosConcluidos = conteudosConcluidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dev dev = (Dev) o;
        return Objects.equals(nome, dev.nome) && Objects.equals(conteudosInscritos, dev.conteudosInscritos) && Objects.equals(conteudosConcluidos, dev.conteudosConcluidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, conteudosInscritos, conteudosConcluidos);
    }
}
