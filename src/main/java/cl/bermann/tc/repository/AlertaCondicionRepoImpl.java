package cl.bermann.tc.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import cl.bermann.tc.model.AlertaCondicion;

@Repository
public class AlertaCondicionRepoImpl implements AlertaCondicionCustomRepo{

	@Autowired
    private EntityManager entityManager;

	@Override
	public List<AlertaCondicion> buscarAlertasxVh(int idvh, boolean f_sinsenal) {
		List<AlertaCondicion>  lac = new ArrayList<AlertaCondicion>();

		String cond_extra = f_sinsenal ? " union select t1.id id_alarma, s.se_identificador ident, vae2.vae_condicion condicion, vae2.vae_valor_txt valor,  vae2.vae_tipo tipo_alerta, fv.id_vehiculo, -1 "
		+ " from ( select va.id, va.vsa_alerta, vae.vae_valor_txt  from tcontrol.vehiculo_alarma va inner join tcontrol.horario h on h.id = any (va.id_horario_alarma) "
		+ " and extract (isodow from now()) = any(h.hor_dia) and current_time between h.hor_hora_inicio and h.hor_hora_termino and va.id_estado = 1 "
		+ " inner join tcontrol.vehiculo_alarma_evento vae on vae.id_alarma = va.id and  vae_tipo  = 405 and cast(vae.vae_valor_txt as int) >= 5 and vae.id_estado = 1) t1 "
		+ " inner join tcontrol.alerta_flota af on af.id_alerta = t1.id "
		+ " inner join tcontrol.flota_vehiculo fv on fv.id_flota = af.id_flota "
		+ " inner join tcontrol.ultpos_up uu on uu.id_dispositivo = fv.id_vehiculo and (now() - uu.up_fecha ) > cast((t1.vae_valor_txt || ' minutes') as interval) "
		+ " inner join tcontrol.vehiculo_alarma_evento vae2 on vae2.id_alarma = t1.id "
		+ " left  join tcontrol.sensor s on vae2.id_nev = s.id  group by 1,2,3,4,5,6,7 ":"";
		String consulta = " select t2.*, now() from (select va.id id_alarma, s.se_identificador ident, vae.vae_condicion condicion, "
		+ " case when vae.vae_tipo = 310 and vae.vae_valor_txt = 'rs' then (select '{'||array_to_string((select array_agg(distinct(tr.id_tramo))  from tcontrol.ruta r inner join tcontrol.viajes v on v.origen = r.origen and v.destino = r.destino  inner join tcontrol.tramo_ruta tr on tr.id_ruta = r.id where r.habilitado and v.id_vehiculo = fv.id_vehiculo  and v.estado_viaje = 190),',')||'}' ) else vae.vae_valor_txt end  valor, "
		+ " vae.vae_tipo tipo_alerta, fv.id_vehiculo, "
		+ " case when va.va_obsolescencia then va.va_obsolescencia_tiempo else -1 end "
		+ " from tcontrol.flota_vehiculo fv inner join tcontrol.alerta_flota af on af.id_flota = fv.id_flota "
		+ " inner join tcontrol.vehiculo_alarma va on af.id_alerta =va.id and va.id  not in (select distinct(id_alarma) from tcontrol.vehiculo_alarma_evento vae where vae_tipo = 405 and vae.id_estado = 1) " 
		+ " inner join tcontrol.vehiculo_alarma_evento vae on va.id = vae.id_alarma left  join tcontrol.sensor s on vae.id_nev = s.id inner join tcontrol.horario h on h.id = any (va.id_horario_alarma) "
		+ " where fv.id_vehiculo = :id_vehiculo and extract (isodow from now()) = any(h.hor_dia) and " 
		// current_time between h.hor_hora_inicio and h.hor_hora_termino "
		+ " case when h.hor_hora_inicio > h.hor_hora_termino then current_time > h.hor_hora_inicio or current_time < h.hor_hora_termino else current_time between h.hor_hora_inicio and h.hor_hora_termino end"
		+ " and va.id_estado = 1 and vae.id_estado = 1 and not exists (select id from alerta_historial ah where ah.id_vehiculo = fv.id_vehiculo and ah.id_alerta = va.id " 
		+ " and ((va.va_persistencia_defecto and ah.id_estado_atencion < 189) or (not va.va_persistencia_defecto and va.va_persistencia_tipo != 4 and ah.ah_fecha_activacion > now() - (va.va_tiempo_persistencia || ' minute') :: interval)))" + cond_extra + " ) t2 "
// join para casos periodo activacion
		+ " left join tcontrol.vehiculo_alarma_evento vae3  on vae3.id_alarma = t2.id_alarma and vae3.vae_tipo = 479 " 
		+ " left join tcontrol.viajes v3 on v3.id_vehiculo = t2.id_vehiculo and  v3.estado_viaje != 422 and v3.fecha_origen > now() - cast('1 day' as interval) and vae3.vae_tipo = 479 "
/* 		+ " left join (select case when vae2.vae_condicion = 'V-INI' then v2.fecha_origen else v2.fecha_destino end hito, vae2.id_alarma, vae2.vae_valor_txt valor, v2.id_vehiculo from tcontrol.vehiculo_alarma_evento vae2, tcontrol.viajes v2 where v2.estado_viaje != 422 and v2.fecha_origen > now() - cast('1 day' as interval) and vae2.vae_tipo = 479) t3 on t3.id_alarma = t2.id_alarma and t3.id_vehiculo =  t2.id_vehiculo " 
		+ " where coalesce (case when cast(t3.valor as int) < 0 then now() between t3.hito + cast(t3.valor || ' minutes' as interval) and t3.hito else now()between t3.hito and t3.hito +  cast(t3.valor || ' minutes' as interval) end, true) and t2.tipo_alerta != 479 "   
	*/
		+ " where coalesce(case when cast(vae3.vae_valor_txt as int) < 0 then now() between (case when vae3.vae_condicion = 'V-INI' then v3.fecha_origen else v3.fecha_destino end ) + cast(vae3.vae_valor_txt || ' minutes' as interval) and (case when vae3.vae_condicion = 'V-INI' then v3.fecha_origen else v3.fecha_destino end ) else now()between (case when vae3.vae_condicion = 'V-INI' then v3.fecha_origen else v3.fecha_destino end ) and (case when vae3.vae_condicion = 'V-INI' then v3.fecha_origen else v3.fecha_destino end ) +  cast(vae3.vae_valor_txt || ' minutes' as interval) end, true) " 
		+ " and t2.tipo_alerta != 479  and not (v3.id is null and vae3.id is not null) "
		+ " order by t2.id_vehiculo, t2.id_alarma, tipo_alerta ";
		Query query = entityManager.createNativeQuery(consulta);
				
		query.setParameter("id_vehiculo", idvh);
		//System.out.println(consulta);
		List<Object[]> resp = query.getResultList();
//		 List<AlertaCondicion> resp = query.getResultList();
		for (Object[] elem : resp) {
			lac.add(new AlertaCondicion(elem));
			//System.out.println(new AlertaCondicion(elem).toString());
		}
		return lac;
//		return resp;
		
	}

	
	
	@PostConstruct
	public void postConstruct() {
		Objects.requireNonNull(entityManager);
	}



	@Override
	public List<AlertaCondicion> buscarAlertasxVh(int idvh, boolean f_sinsenal, Timestamp fechaevento) {
		List<AlertaCondicion>  lac = new ArrayList<AlertaCondicion>();

		String cond_extra = f_sinsenal ? " union select t1.id id_alarma, s.se_identificador ident, vae2.vae_condicion condicion, vae2.vae_valor_txt valor,  vae2.vae_tipo tipo_alerta, fv.id_vehiculo, -1 "
		+ " from ( select va.id, va.vsa_alerta, vae.vae_valor_txt  from tcontrol.vehiculo_alarma va inner join tcontrol.horario h on h.id = any (va.id_horario_alarma) "
		+ " and extract (isodow from now()) = any(h.hor_dia) and current_time between h.hor_hora_inicio and h.hor_hora_termino and va.id_estado = 1 "
		+ " inner join tcontrol.vehiculo_alarma_evento vae on vae.id_alarma = va.id and  vae_tipo  = 405 and cast(vae.vae_valor_txt as int) >= 5 and vae.id_estado = 1) t1 "
		+ " inner join tcontrol.alerta_flota af on af.id_alerta = t1.id "
		+ " inner join tcontrol.flota_vehiculo fv on fv.id_flota = af.id_flota "
		+ " inner join tcontrol.ultpos_up uu on uu.id_dispositivo = fv.id_vehiculo and (now() - uu.up_fecha ) > cast((t1.vae_valor_txt || ' minutes') as interval) "
		+ " inner join tcontrol.vehiculo_alarma_evento vae2 on vae2.id_alarma = t1.id "
		+ " left  join tcontrol.sensor s on vae2.id_nev = s.id where not exists (select id from alerta_historial ah where ah.id_vehiculo = fv.id_vehiculo and ah.id_alerta = t1.id " 
		+ " and ((t1.va_persistencia_defecto and ah.id_estado_atencion < 189) or (not t1.va_persistencia_defecto and t1.va_persistencia_tipo != 4  and ah.ah_fecha_activacion > now() - cast((t1.va_tiempo_persistencia || ' minute') as interval))))"
		+ " group by 1, 2, 3 ,4, 5, 6, 7 " : "";

		String consulta = " select t2.*, now() from (select t3.id id_alarma, s.se_identificador ident, vae.vae_condicion condicion,   " 
		+ " case when vae.vae_tipo = 310 and vae.vae_valor_txt = 'rs' then (select '{'||array_to_string((select array_agg(distinct(tr.id_tramo))  from tcontrol.ruta r inner join tcontrol.viajes v on v.origen = r.origen and v.destino = r.destino  inner join tcontrol.tramo_ruta tr on tr.id_ruta = r.id where r.habilitado and v.id_vehiculo = t3.id_vehiculo  and v.estado_viaje = 190),',')||'}' ) else vae.vae_valor_txt end  valor,  " 
		+ " vae.vae_tipo tipo_alerta, t3.id_vehiculo, t3.obsole " 
		+ " from (select distinct(va.id) , va.id_horario_alarma, fv.id_vehiculo,  " 
		+ " case when va.va_obsolescencia then va.va_obsolescencia_tiempo else -1 end obsole " 
		+ " from tcontrol.flota_vehiculo fv  " 
		+ " inner join tcontrol.alerta_flota af on af.id_flota = fv.id_flota  " 
		+ " inner join vehiculo_alarma va on va.id = af.id_alerta  " 
		+ " where fv.id_vehiculo = :id_veh and va.id_estado = 1 and not exists (select id from alerta_historial ah where ah.id_vehiculo = fv.id_vehiculo and ah.id_alerta = va.id "
		+ " and ((va.va_persistencia_defecto and ah.id_estado_atencion < 189) or (not va.va_persistencia_defecto and va.va_persistencia_tipo != 4 and ah.ah_fecha_activacion > now() - cast ((va.va_tiempo_persistencia || ' minute') as interval))))) t3 " 
		+ " inner join tcontrol.horario h on h.id = any(t3.id_horario_alarma) " 
		+ " inner join tcontrol.vehiculo_alarma_evento vae on t3.id = vae.id_alarma  " 
		+ " left  join tcontrol.sensor s on vae.id_nev = s.id  " 
		+ " where vae.id_estado = 1 " 
		+ " and extract (isodow from cast( :fechahora as timestamp)) = any(h.hor_dia) and cast( to_char(cast( :fechahora as timestamp), 'HH24:MI:SS') as time) between h.hor_hora_inicio and h.hor_hora_termino " + cond_extra + " ) t2 "
		
		+ " left join tcontrol.vehiculo_alarma_evento vae3  on vae3.id_alarma = t2.id_alarma and vae3.vae_tipo = 479 " 
		+ " left join tcontrol.viajes v3 on v3.id_vehiculo = t2.id_vehiculo and  v3.estado_viaje != 422 and v3.fecha_origen > now() - cast('1 day' as interval) and vae3.vae_tipo = 479 "
		+ " where coalesce(case when cast(vae3.vae_valor_txt as int) < 0 then now() between (case when vae3.vae_condicion = 'V-INI' then v3.fecha_origen else v3.fecha_destino end ) + cast(vae3.vae_valor_txt || ' minutes' as interval) and (case when vae3.vae_condicion = 'V-INI' then v3.fecha_origen else v3.fecha_destino end ) else now()between (case when vae3.vae_condicion = 'V-INI' then v3.fecha_origen else v3.fecha_destino end ) and (case when vae3.vae_condicion = 'V-INI' then v3.fecha_origen else v3.fecha_destino end ) +  cast(vae3.vae_valor_txt || ' minutes' as interval) end, true) " 
		+ " and t2.tipo_alerta != 479  and not (v3.id is null and vae3.id is not null) "
		+ " order by t2.id_vehiculo, t2.id_alarma, tipo_alerta ";
		Query query = entityManager.createNativeQuery(consulta);
		//System.out.println("QUERY : " + consulta +" :: "+ idvh);
		query.setParameter("id_veh", idvh);
		query.setParameter("fechahora", fechaevento);
		//System.out.println(getQueryStringWithParameters(query));
		List<Object[]> resp = query.getResultList();
		for (Object[] elem : resp) {
			lac.add(new AlertaCondicion(elem));
		}
		return lac;
	}

	public String getQueryStringWithParameters(Query query) {
        String queryString = query.toString();
        
        if (query.getParameters() != null && !query.getParameters().isEmpty()) {
            Iterator<Parameter<?>> parameters = query.getParameters().iterator();
            //for (Map.Entry<String, Object> entry : parameters.iterator()..entrySet()) {
			for (; parameters.hasNext();) {
				Parameter<?> entry = parameters.next();
                String parameterName = entry.getName();
                Object parameterValue = entry.toString();
                String parameterValueString = parameterValue != null ? parameterValue.toString() : "null";
                queryString = queryString.replace(":" + parameterName, parameterValueString);
            }
        }

        return queryString;
    }

}
