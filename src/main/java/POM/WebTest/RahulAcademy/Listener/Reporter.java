package POM.WebTest.RahulAcademy.Listener;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Reporter implements IReporter {

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xmlContent.append("<TestNGReport>\n");

		for (XmlSuite xmlSuite : xmlSuites) {
			xmlContent.append("  <Suite name=\"").append(xmlSuite.getName()).append("\">\n");
			xmlContent.append("    <Parameters>").append(xmlSuite.getParameters()).append("</Parameters>\n");
		}

		for (ISuite suite : suites) {
			xmlContent.append("  <ISuite name=\"").append(suite.getName()).append("\">\n");
			suite.getResults().forEach((k, v) -> {
				xmlContent.append("    <Test name=\"").append(k).append("\">\n");
				xmlContent.append("      <Passed>").append(v.getTestContext().getPassedTests().size()).append("</Passed>\n");
				xmlContent.append("      <Failed>").append(v.getTestContext().getFailedTests().size()).append("</Failed>\n");
				xmlContent.append("      <Skipped>").append(v.getTestContext().getSkippedTests().size()).append("</Skipped>\n");
				xmlContent.append("    </Test>\n");
			});
			xmlContent.append("  </ISuite>\n");
		}

		xmlContent.append("</TestNGReport>");

		String fileName = "Raport_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xml";
		Path reportsDir = Path.of("D:\\Programowanie\\Nauka\\SeleniumTestAutomation\\SeleniumTestAutomation\\Raports");
		Path reportFile = reportsDir.resolve(fileName);
		try {
			Files.createDirectories(reportsDir);
			Files.writeString(reportFile, xmlContent.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Raport zapisany do: " + reportFile.toAbsolutePath());

		System.out.println("----".repeat(3));
	}
}
