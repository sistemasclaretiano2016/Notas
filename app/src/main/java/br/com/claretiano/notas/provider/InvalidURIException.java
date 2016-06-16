package br.com.claretiano.notas.provider;           
                                                                
import android.net.Uri;                                         
                                                                
// Exceção que representa uma URI inv�lida                      
@SuppressWarnings("serial")                                     
public class InvalidURIException extends RuntimeException {     
                                                                
	public InvalidURIException(Uri uri) {                         
		super("URI inválida: " + uri);                            
	}                                                             
}                                                               
