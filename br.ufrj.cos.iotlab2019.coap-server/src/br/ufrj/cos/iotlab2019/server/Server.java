/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  UFRJ / COPPE / PESC
 * PROJECT       :  CPS731 :: IoT Lab
 * FILENAME      :  Server.java
 *
 * This file is an implementation work, part of a postgraduate course.
 * Course website (PT-BR):  https://sites.google.com/cos.ufrj.br/lab-iot
 * **********************************************************************
 * 
 * This code was written based on the Californium project examples
 * (COAP Server / Resources).

 * #L%
 */
package br.ufrj.cos.iotlab2019.server;

import org.eclipse.californium.core.CoapServer;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ufrj.cos.iotlab2019.server.resources.HumidityResource;
import br.ufrj.cos.iotlab2019.server.resources.SensorDHT11;
import br.ufrj.cos.iotlab2019.server.resources.TemperatureResource;

/**
 * This class is used set up a CoAp server that handles GET and PUT
 * requests to interact with a custom resources (DHT11 sensors).
 *
 * @author Egberto Rabello
 */
public class Server extends CoapServer {

	// Logger and ID
	private static final Logger s_logger = LoggerFactory.getLogger(Server.class);
    private static final String APP_ID = "br.ufrj.cos.iotlab2019.coap-server";
    Server server;
	
    // Activator
    protected void activate(ComponentContext componentContext) {

    	// Define CoAP server
		server = new Server();
		
		// create sensor objects
		SensorDHT11 sensor1 = new SensorDHT11("4");
		SensorDHT11 sensor2 = new SensorDHT11("17");
		
		// add a "temperature" resource of TemperatureResource type as observable for sensor 1
		TemperatureResource tempRes1 = new TemperatureResource("temperature-1", sensor1);
		tempRes1.setObservable(true);
		tempRes1.getAttributes().setObservable();
		server.add(tempRes1);
		
		// add a "humidity" resource of HumidityResource type as observable for sensor 1
		HumidityResource humiRes1 = new HumidityResource("humidity-1", sensor1);
		humiRes1.setObservable(true);
		humiRes1.getAttributes().setObservable();
		server.add(humiRes1);
		
		// add a "temperature" resource of TemperatureResource type as observable for sensor 2
		TemperatureResource tempRes2 = new TemperatureResource("temperature-2", sensor2);
		tempRes2.setObservable(true);
		tempRes2.getAttributes().setObservable();
		server.add(tempRes2);
		
		// add a "humidity" resource of HumidityResource type as observable for sensor 2
		HumidityResource humiRes2 = new HumidityResource("humidity-2", sensor2);
		humiRes2.setObservable(true);
		humiRes2.getAttributes().setObservable();
		server.add(humiRes2);
		
		// start CoAP server
		server.start();
    	
    	s_logger.info("Bundle " + APP_ID + " has started!");
    	s_logger.info("CoapServer instance has started!");
    	s_logger.info("Make sure the GPIO circuit is properly set up.");
    	s_logger.debug(APP_ID + ": This is a debug message.");

    }
    
    // Deactivator
    protected void deactivate(ComponentContext componentContext) {

        server.stop();
    	s_logger.info("Bundle " + APP_ID + " has stopped!");

    }

}
