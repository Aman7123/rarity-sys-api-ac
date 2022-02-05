package com.aaronrenner.camel.implementations;

import static com.aaronrenner.camel.CommonElements.throwNotFound;
import java.sql.SQLException;
import org.springframework.stereotype.Component;
import com.aaronrenner.camel.BaseRestRouteBuilder;
import com.datasonnet.document.MediaTypes;
import com.ms3_inc.tavros.extensions.rest.exception.NotFoundException;

/**
 * @author Aaron Renner <admin@aaronrenner.com>
 *
 */
@Component
public class GetGeishaById extends BaseRestRouteBuilder {

	private String SQLPATH = "classpath:/sql/";
	private String DSPATH = "resource:classpath:/scripts/";
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		from(direct("get-geisha-id-impl"))
			.routeId("direct:get-geisha-id-impl")
            .doTry()
	            /** Send SQL call */
	            .to(sqlStored(SQLPATH+"get_geisha_by_id.sql"))
	            /** Validate response was returned or throw 404 */
	            .to(direct("validate-sql-get-operation"))
	            /** format response */
	            .transform(datasonnetEx(DSPATH+"getGeishaByIdResponse.ds", String.class)
	            		.bodyMediaType(MediaTypes.APPLICATION_JAVA)
	            		.outputMediaType(MediaTypes.APPLICATION_JSON))
            .doCatch(SQLException.class)
            	.choice()
            		.when(simple("${exception.message} contains 'Resource could not be located'"))
            			.throwException(
    						new NotFoundException(
								throwNotFound(
									null
								) // END throwBadRequest
							) // END BadRequestException
						) // END throwException
        		.endChoice()
            .end()
        ;
	}
}
