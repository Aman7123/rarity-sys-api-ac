package com.aaronrenner.camel.implementations;

import static com.aaronrenner.camel.CommonElements.throwServerError;
import java.sql.SQLException;
import org.springframework.stereotype.Component;
import com.aaronrenner.camel.BaseRestRouteBuilder;
import com.ms3_inc.tavros.extensions.rest.exception.InternalServerException;

/**
 * @author Aaron Renner <admin@aaronrenner.com>
 *
 */
@Component
public class PostDFById extends BaseRestRouteBuilder {

	private String SQLPATH = "classpath:/sql/";
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		from(direct("post-df-id-impl"))
			.routeId("direct:post-df-id-impl")
			/** Pick K/V pairs from JSON incoming body */
            .setProperty("rarity", datasonnetEx("payload.rarity", Integer.class))
         
            .doTry()
	            /** Call Stored Procedure */
	            .to(sqlStored(SQLPATH+"create_deadfellaz.sql"))
	            
	            /** Clear response body */
	            .setBody(constant(""))
	            
            .doCatch(SQLException.class)
            	/** If an error occurs in the DB inform the client with 500 */
            	.throwException(
        			new InternalServerException(
    					throwServerError(
							"The Deadfellaz could not be uploaded to the database. Please try again and if the problem persists please contact an admin at admin@aaronrenner.com",
							"It is likely a resource with the provided ID or rarity exist."
						) // END throwServerError
					) // END InternalServerException
				) // END throwException
            .end()
        ;
	}
}
