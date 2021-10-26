package br.com.alura.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //springboot identifica a classe como controller
public class HelloController {
	@RequestMapping("/") //informa quando o spring vai chamar esse metodo de acordo com a url informada
	@ResponseBody //para o spring nao considerar que esse retorno é uma pagina.
	public String hello() {
		return "Hello word!";
	}
	
	
}
