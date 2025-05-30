package cl.bermann.tc.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import cl.bermann.tc.model.Mensaje;
import cl.bermann.tc.repository.MensajeRepo;

@Service
public class MsgMetodos implements MensajeRepo{

	@Autowired
	private MongoTemplate mt;
	
	public List<Mensaje> buscarPorEstado(String estado){
		List<Mensaje> lm = null;
		Query qry = new Query();
		qry.addCriteria(Criteria.where("mc_estado").is(estado)).limit(50);
		lm = mt.find(qry, Mensaje.class);
		return lm;
		
	}

/* 	@Override
	public List<Mensaje> mensajesPendientesn(int estado, int vlimit) {
		List<Mensaje> lm = null;
	//	Date l_date = new Date(System.currentTimeMillis() - 3600 * 1000);
		// Date l_date = new Date();
		 Query qry = new Query();
		// System.out.println("HORA LIMITE :: " + l_date);
		// qry.addCriteria(Criteria.where("mc_estado").is(0).and("mc_fecha").gt(l_date)).limit(50);
	 	qry.addCriteria(Criteria.where("mc_estado").is(0)).limit(vlimit);
		lm = mt.find(qry, Mensaje.class);
		return lm;
	} */

	@Override
	public List<Mensaje> mensajesPendientes(int estado, int vlimit) {
		MatchOperation match = Aggregation.match( Criteria.where("mc_estado").is(estado));

		SortOperation sort = Aggregation.sort(Sort.Direction.ASC, "mc_fecha");

		GroupOperation group = Aggregation.group("mc_contenido.mc_patente").first(Aggregation.ROOT).as("registro");

		ReplaceRootOperation replaceRoot = Aggregation.replaceRoot("registro");

		//LimitOperation limit = Aggregation.limit(vlimit);

		Aggregation aggregation = Aggregation.newAggregation(
			match,
			sort,
			group,
			replaceRoot
			//,limit
		);

		AggregationResults<Mensaje> results = mt.aggregate(
			aggregation,
			"mensaje_concentrador_mc",
			Mensaje.class
		);

		return results.getMappedResults();
	}

	
	@Override
	public List<Mensaje> mensajesPendientes(int estado, int vlimit, String ultimoCaracter) {
		List<Mensaje> lm = null;
		Query qry = new Query();
		
		qry.addCriteria(Criteria.where("mc_estado").is(estado));
		//qry.addCriteria(Criteria.where("mc_contenido.mc_patente").regex(ultimoCaracter + "$", "i")); // Termina en 0
		Criteria terminaConCaracter = Criteria.where("mc_contenido.mc_patente").regex(ultimoCaracter + "$", "i");
		if ("0".equals(ultimoCaracter)) {
		// 	qry.addCriteria(Criteria.where("mc_contenido.mc_patente").regex("[^0-9]$", "i")); // O no termina en número
		// } 
			Criteria noTerminaEnNumero = Criteria.where("mc_contenido.mc_patente").not().regex("\\d$", "i");
			qry.addCriteria(new Criteria().orOperator(terminaConCaracter, noTerminaEnNumero));
		} else {
			qry.addCriteria(terminaConCaracter);
		}
		
		qry.limit(vlimit);
		
		lm = mt.find(qry, Mensaje.class);
		return lm;
	}
	
	public void marcaListo(Mensaje msg, int estado) {
//		Query qry = new Query();
//		qry.addCriteria(Criteria.where("_id").is(msg.getId()));
//		System.out.println(msg);
//		Update upd = new Update();
//		upd.set("mc_estado", 1);
		msg.setMc_estado(estado);
		msg.setId_proveedor("FS345e");
		Mensaje mv = mt.save(msg);
//		Mensaje mv = mt.findAndModify(qry, upd, Mensaje.class);
		System.out.println("[FIN PROCESO] " + mv);
	}
	

}
