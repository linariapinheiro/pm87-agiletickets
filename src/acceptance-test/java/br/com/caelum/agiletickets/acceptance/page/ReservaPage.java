package br.com.caelum.agiletickets.acceptance.page;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ReservaPage {
	private static final String BASE_URL = "http://localhost:8080";
	private final WebDriver driver;

	public ReservaPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void abreSessao() {
		driver.get(BASE_URL + "/sessao/7");
	}
	
	public void reservaIngresso(String quantidade) {
		WebElement form = form();
		form.findElement(By.id("qtde")).sendKeys(quantidade);
		form.submit();
	}
	
	public void verificaValor() {
		WebElement erros = driver.findElement(By.id("message"));

		assertThat(erros.getText(), containsString("55"));
	}
	
	private WebElement form() {
		return driver.findElement(By.id("reservaForm"));
	}
}
