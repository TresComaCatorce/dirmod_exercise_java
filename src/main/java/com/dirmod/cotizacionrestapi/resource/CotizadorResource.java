package com.dirmod.cotizacionrestapi.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.dirmod.cotizacionrestapi.domain.Cotizador;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@RestController
public class CotizadorResource
{
	@GetMapping( value = "/cotizacion/{currencyName}", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseBodyEmitter getCotizacion( @PathVariable String currencyName)
	{
		ResponseBodyEmitter emitter = new ResponseBodyEmitter();
		
		try {
			String currencyCode;
			switch(currencyName)
			{
				case "dolar":
					currencyCode = "USD";
					break;
				case "euro":
					currencyCode = "EUR";
					break;
				case "real":
					currencyCode = "BRL";
					break;
				default:
					throw new IOException("Moneda incorrecta");
			}
			

			URL url = new URL("http://api.wahrungsrechner.org/v1/quotes/" + currencyCode + "/ARS/json?key=2797%7CXzQPUFsVVm~NN%5EEYbPFp*YFnxYFTwUkz");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200)
			{
				throw new RuntimeException("Error : HTTP error code : " + conn.getResponseCode());
			}
					
			BufferedReader br = new BufferedReader(new InputStreamReader( (conn.getInputStream())) );

			String output;
			String outputComplete = "";
			while( (output = br.readLine()) != null ){
				outputComplete += output;
			}

			//Parse response from external service
			JSONObject externalRespose = new JSONObject(outputComplete);
			JSONObject resultObject = externalRespose.getJSONObject("result");

			//Create response to send
			Long valueResponse = resultObject.getLong("value");
			Cotizador responsePOJO = new Cotizador( currencyCode, valueResponse );

			
			emitter.send(responsePOJO);
			emitter.complete();
			conn.disconnect();
			
        } catch (MalformedURLException e) {
            emitter.completeWithError(e);
        } catch (ProtocolException e) {
            emitter.completeWithError(e);
        } catch (IOException e) {
            emitter.completeWithError(e);
		}
		
		return emitter;
	}

}