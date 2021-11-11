package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}") //injeta a propriedade que esta no application.properties 
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API do Forum da Alura") //aplicacao que gerou o token
				.setSubject(logado.getId().toString()) //dono do token (usuario)
				.setIssuedAt(hoje) //data de geracao do token
				.setExpiration(dataExpiracao) //data de expiracao
				.signWith(SignatureAlgorithm.HS256, secret) //informar o algoritmo de criptografia e a senha da aplicacao (esta no application.properties)
				.compact(); //compacta e transforma em String
		
	}

	
	
}
