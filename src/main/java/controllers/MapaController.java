package controllers;

import db.RepositorioDispositivos;
import db.RepositorioTransformadores;
import db.RepositorioUsuarios;

public class MapaController {

	private RepositorioTransformadores repositorioTransformadores;

	public MapaController(RepositorioTransformadores repositorioTransformadores) {
		this.repositorioTransformadores = repositorioTransformadores;
	}

}
