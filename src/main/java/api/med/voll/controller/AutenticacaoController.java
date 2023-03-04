package api.med.voll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.med.voll.domain.usuario.DadosAutenticacao;
import api.med.voll.domain.usuario.Usuario;
import api.med.voll.infra.security.TokenDadosTokenJWT;
import api.med.voll.infra.security.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
		
		var autheticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
		var authentication = manager.authenticate(autheticationToken);
		var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
		
		return ResponseEntity.ok(new TokenDadosTokenJWT(tokenJWT));
	}
}
