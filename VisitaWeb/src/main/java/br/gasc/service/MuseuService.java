package br.gasc.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.gasc.dao.MuseuDao;
import br.gasc.model.Museu;

public class MuseuService {
	private MuseuDao museuDao;

	public MuseuService() {
		this.museuDao = new MuseuDao();
	}
	
	public List<LocalTime> gerarHorariosDisponiveis(Integer idMuseu) {
		Optional<Museu> museuOpt = museuDao.pesquisar(idMuseu);
		List<LocalTime> horariosList = new ArrayList<LocalTime>();
		
		if(museuOpt.isPresent()) {
			Museu museu = museuOpt.get();
			LocalTime horarioAbertura = museu.getHorarioAbertura();
			LocalTime horarioFechamento = museu.getHorarioFechamento();
			
			for(LocalTime horario = horarioAbertura; !horario.isAfter(horarioFechamento); horario = horario.plusHours(1))
				horariosList.add(horario);
		}
		
		return horariosList;
	}
	
	public Museu obterMuseuPorId(Integer idMuseu) {
		Optional<Museu> museuOpt = museuDao.pesquisar(idMuseu);
		
		if(museuOpt.isPresent())
			return museuOpt.get();
		return null;
	}
	
}