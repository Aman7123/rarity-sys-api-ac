package com.aaronrenner.camel;

import com.ms3_inc.tavros.extensions.rest.exception.BadRequestException;
import org.springframework.stereotype.Component;
import static com.aaronrenner.camel.CommonElements.throwBadRequest;

/**
 * The RoutesImplementation class holds implementations for the end points configured in RoutesGenerated.
 * These routes are based on operation Ids, that correspond to an API end point:  method+path.
 *
 * @author Maven Archetype (camel-openapi-archetype)
 */
@Component
public class RoutesImplementation extends BaseRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        // TODO: Replace stubs for each endpoint with real implementation. Implementation defaults to a simple response with operation Id.
        from(direct("get-rarity-collection-id"))
        	.setProperty("id", simple("${in.headers.id}"))
        	.setProperty("collection", simple("${in.headers.collection}"))
            .choice()
            	.when(simple("${exchangeProperty.collection} =~ 'deadfellaz'"))
	            	.to(direct("get-df-id-impl"))
            	.when(simple("${exchangeProperty.collection} =~ 'geisha'"))
            		.to(direct("get-geisha-id-impl"))
	            .otherwise()
	            	.throwException(new BadRequestException(throwBadRequest(simple("cml.exchangeProperty('url')"), "Collection could no be found")))
        ;
        from(direct("post-rarity-collection-id"))
	    	.setProperty("id", simple("${in.headers.id}"))
	    	.setProperty("collection", simple("${in.headers.collection}"))
	        .choice()
	        	.when(simple("${exchangeProperty.collection} =~ 'deadfellaz'"))
	        		.to(direct("post-df-id-impl"))
	        	.when(simple("${exchangeProperty.collection} =~ 'geisha'"))
	        		.to(direct("post-geisha-id-impl"))
	            .otherwise()
	            	.throwException(new BadRequestException(throwBadRequest(simple("cml.exchangeProperty('url')"), "Collection could no be found")))
        ;

    }
}
