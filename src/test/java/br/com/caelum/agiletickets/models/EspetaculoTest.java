package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void verificarCriarSessoesDataInicioMaiorQueDataFim(){
		Espetaculo espetaculo = new Espetaculo();
		LocalTime horario = LocalTime.parse("22:00");
		LocalDate inicio = new LocalDate();
		inicio = inicio.plusDays(2);
		espetaculo.criaSessoes(inicio, new LocalDate(), horario, Periodicidade.DIARIA);
		//assertTrue(sessoes.isEmpty());
	}
	
	@Test
	public void verificarCriarSessoesDataInicioIgualDataFim(){
		Espetaculo espetaculo = new Espetaculo();
		LocalTime horario = LocalTime.parse("22:00");
		List<Sessao> sessoes = espetaculo.criaSessoes(new LocalDate(), new LocalDate(), horario, Periodicidade.DIARIA);
		assertTrue(sessoes.size() == 1);
	}
	
	public void verificarCriarSessoesDataHoraInicioMaiorQueDataAtual(){
		
	}
	
	@Test
	public void verificarCriarSessoesDataInicioMenorQueDataFimDiaria(){
		Espetaculo espetaculo = new Espetaculo();
		LocalTime horario = LocalTime.parse("22:00");
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate();
		fim = fim.plusDays(3);
		
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		
		Assert.assertEquals(4, sessoes.size());
	}
	
	@Test
	public void verificarCriar3SessoesSemanaisDataInicioMenorQueDataFim(){
		Espetaculo espetaculo = new Espetaculo();
		LocalTime horario = LocalTime.parse("22:00");
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate();
		fim = fim.plusWeeks(3);
		
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.SEMANAL);
		
		Assert.assertEquals(4, sessoes.size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveriaCriarSessoesDiariasQuandoDataInicioMaiorQueDataFim() {
		//DADAS ESTAS ENTRADAS:
		LocalDate hoje = new LocalDate();
		LocalDate fim = new LocalDate();
	  	LocalDate amanha = fim.plusDays(1);
	  	LocalTime agora = new LocalTime();
	  	Periodicidade diaria = Periodicidade.DIARIA;
	  
	  	//QUANDO EU DISPARAR O PROCESSAMENTO:
	  	Espetaculo show = new Espetaculo();
	  	show.criaSessoes(amanha, hoje, agora, diaria);
	  
	  	//ESTAS SAO AS SAIDAS ESPERADAS:
	  	//Nao tem asserts pq deveria jogar exception...
	}

	@Test(expected = IllegalArgumentException.class)
	public void naoDeveriaCriarSessoesSemanaisQuandoDataInicioMaiorQueDataFim() {
		//DADAS ESTAS ENTRADAS:
		LocalDate hoje = new LocalDate();
		LocalDate fim = new LocalDate();
	  	LocalDate amanha = fim.plusDays(1);
	  	LocalTime agora = new LocalTime();
	  	Periodicidade semanal = Periodicidade.SEMANAL;
	  
	  	//QUANDO EU DISPARAR O PROCESSAMENTO:
	  	Espetaculo show = new Espetaculo();
	  	show.criaSessoes(amanha, hoje, agora, semanal);
	  
	  	//ESTAS SAO AS SAIDAS ESPERADAS:
	  	//Nao tem asserts pq deveria jogar exception...
	}

	@Test
	public void deveriaCriarApenasUmaSessaoParaPeriodicidadeDiariaComDataInicioIgualDataFim() {
		//DADAS ESTAS ENTRADAS:
		LocalDate hoje = new LocalDate();
	  	LocalTime agora = new LocalTime();
	  	Periodicidade diaria = Periodicidade.DIARIA;
	  
	  	//QUANDO EU DISPARAR O PROCESSAMENTO:
	  	Espetaculo show = new Espetaculo();
	  	List<Sessao> sessoes = show.criaSessoes(hoje, hoje, agora, diaria);
	  
	  	//ESTAS SAO AS SAIDAS ESPERADAS:
		Assert.assertEquals(1, sessoes.size());
	  
	  	//Nao basta apenas verificar o size da lista, precisa garantir que criou as sessoes corretamente:
	  	Sessao unica = sessoes.get(0);
	  	Assert.assertEquals(show, unica.getEspetaculo());
	    Assert.assertEquals(hoje.toDateTime(agora), unica.getInicio());
	}

	@Test
	public void deveriaCriarApenasUmaSessaoParaPeriodicidadeSemanalComDataInicioIgualDataFim() {
		//DADAS ESTAS ENTRADAS:
		LocalDate hoje = new LocalDate();
	  	LocalTime agora = new LocalTime();
	  	Periodicidade semanal = Periodicidade.SEMANAL;
	  
	  	//QUANDO EU DISPARAR O PROCESSAMENTO:
	  	Espetaculo show = new Espetaculo();
	  	List<Sessao> sessoes = show.criaSessoes(hoje, hoje, agora, semanal);
	  
	  	//ESTAS SAO AS SAIDAS ESPERADAS:
		Assert.assertEquals(1, sessoes.size());
	  
	  	//Nao basta apenas verificar o size da lista, precisa garantir que criou as sessoes corretamente:
	  	Sessao unica = sessoes.get(0);
	  	Assert.assertEquals(show, unica.getEspetaculo());
	    Assert.assertEquals(hoje.toDateTime(agora), unica.getInicio());
	}

	@Test
	public void deveriaCriarCincoSessoesParaPeriodicidadeDiariaComIntervaloDeCincoDias() {
		//DADAS ESTAS ENTRADAS:
		LocalDate hoje = new LocalDate();
	  	LocalDate daquiQuatroDias = hoje.plusDays(4);
	  	LocalTime agora = new LocalTime();
	  	Periodicidade diaria = Periodicidade.DIARIA;
	  
	  	//QUANDO EU DISPARAR O PROCESSAMENTO:
	  	Espetaculo show = new Espetaculo();
	  	List<Sessao> sessoes = show.criaSessoes(hoje, daquiQuatroDias, agora, diaria);
	  
	  	//ESTAS SAO AS SAIDAS ESPERADAS:
		Assert.assertEquals(5, sessoes.size());
	  
	  	//Nao basta apenas verificar o size da lista, precisa garantir que criou as sessoes corretamente:
		for(int i = 0; i <= sessoes.size(); i++) {
			Sessao criada = sessoes.get(i);
			Assert.assertEquals(show, criada.getEspetaculo());
			Assert.assertEquals(hoje.plusDays(i).toDateTime(agora), criada.getInicio());
	    }
	}

	@Test
	public void deveriaCriarCincoSessoesParaPeriodicidadeSemanalComIntervaloDeCincoSemanas() {
		//DADAS ESTAS ENTRADAS:
		LocalDate hoje = new LocalDate();
	  	LocalDate daquiQuatroSemanas = hoje.plusWeeks(4);
	  	LocalTime agora = new LocalTime();
	  	Periodicidade semanal = Periodicidade.SEMANAL;
	  
	  	//QUANDO EU DISPARAR O PROCESSAMENTO:
	  	Espetaculo show = new Espetaculo();
	  	List<Sessao> sessoes = show.criaSessoes(hoje, daquiQuatroSemanas, agora, semanal);
	  
	  	//ESTAS SAO AS SAIDAS ESPERADAS:
		Assert.assertEquals(5, sessoes.size());
	  
	  	//Nao basta apenas verificar o size da lista, precisa garantir que criou as sessoes corretamente:
		for(int i = 0; i < sessoes.size(); i++) {
			Sessao criada = sessoes.get(i);
			Assert.assertEquals(show, criada.getEspetaculo());
			Assert.assertEquals(hoje.plusWeeks(i).toDateTime(agora), criada.getInicio());
	    }
	}
}
