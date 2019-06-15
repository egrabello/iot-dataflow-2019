/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  UFRJ / COPPE / PESC
 * PROJECT       :  CPS731 :: IoT Lab
 * FILENAME      :  ClientCLI.java
 *
 * This file is an implementation work, part of a postgraduate course.
 * Course website (PT-BR):  https://sites.google.com/cos.ufrj.br/lab-iot
 * **********************************************************************
 * 
 * This code was written based on the Californium project examples
 * (COAP Client).

 * #L%
 */
package br.ufrj.cos.iotlab2019.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class implements a Command Line Interface (CLI) to set up a CoAp
 * client that make requests to observe a remote resources (Temperature
 * and Humidity).
 *
 * @author Egberto Rabello
 */
public class Client {
	
	// Variables (measures)
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static String outputPath;
			
	// Method to set status on output server and console
	static void setOutput(CoapResponse response, String resource){
		
		String timeStamp = dateFormat.format(new Date());
		String output = "";
		try {
			output = new JSONObject().put("timeStamp", timeStamp)
											.put("resource", resource)
											.put("value", response.advanced().getPayloadString()).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		System.out.println(output);
		
		try {
			String fileName = outputPath + timeStamp.substring(0,16).replace(" ","_").replace(":", "-") + ".json";
			
			File file = new File(fileName);
			file.getParentFile().mkdirs();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		    writer.append(output + System.lineSeparator());
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Main method
	public static void main(String[] args) throws Exception {
		
		// Get input parameters from user
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String sensorHost;
		System.out.println(">> Please enter the input parameters below:");
		System.out.print(">> Sensor Host (blank for 'localhost'): ");
		sensorHost = reader.readLine();
		if (sensorHost.trim().isEmpty()) {
			sensorHost = "127.0.0.1";
		}
		System.out.print(">> Output Path (blank for '~/output/'): ");
		outputPath = reader.readLine();
		if (outputPath.trim().isEmpty()) {
			outputPath = System.getProperty("user.home") + "/output/";
		}
		
		// Create CoAP clients
		final CoapClient temp1Client = new CoapClient("coap://" + sensorHost + ":5683/temperature-1");
		final CoapClient humi1Client = new CoapClient("coap://" + sensorHost + ":5683/humidity-1");
		final CoapClient temp2Client = new CoapClient("coap://" + sensorHost + ":5683/temperature-2");
		final CoapClient humi2Client = new CoapClient("coap://" + sensorHost + ":5683/humidity-2");
		
		// Display disclaimer on console
		System.out.println();
		System.out.println(">> CoAP client has started to observe resources in " + sensorHost + ".");
		System.out.println(">> Press CTRL-C to exit.");
		
		// Establish observe relation to temperature-1
		temp1Client.observeAndWait(new CoapHandler() {
			
			// Define action taken when observed temperature changes
			@Override
			public void onLoad(CoapResponse response) {

				setOutput(response, "temperature-1");
				
			}

			// Handle connection error to output server
			@Override
			public void onError() {
				System.out.println(">> Erro na conexao com o servidor");
			}
		});
		
		// Establish observe relation to humidity-1
		humi1Client.observeAndWait(new CoapHandler() {
			
			// Define action taken when observed temperature changes
			@Override
			public void onLoad(CoapResponse response) {

				setOutput(response, "humidity-1");
				
			}

			// Handle connection error to output server
			@Override
			public void onError() {
				System.out.println(">> Erro na conexao com o servidor");
			}
		});
		
		// Establish observe relation to temperature-2
		temp2Client.observeAndWait(new CoapHandler() {
			
			// Define action taken when observed temperature changes
			@Override
			public void onLoad(CoapResponse response) {

				setOutput(response, "temperature-2");
				
			}

			// Handle connection error to output server
			@Override
			public void onError() {
				System.out.println(">> Erro na conexao com o servidor");
			}
		});
		
		// Establish observe relation to humidity-2
		humi2Client.observeAndWait(new CoapHandler() {
			
			// Define action taken when observed temperature changes
			@Override
			public void onLoad(CoapResponse response) {

				setOutput(response, "humidity-2");
				
			}

			// Handle connection error to output server
			@Override
			public void onError() {
				System.out.println(">> Erro na conexao com o servidor");
			}
		});
		
		// keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(500);
        }
	}

}
