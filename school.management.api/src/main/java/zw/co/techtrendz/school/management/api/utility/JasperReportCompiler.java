package zw.co.techtrendz.school.management.api.utility;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class JasperReportCompiler {

    private static final String JRXML_PATH = "src/main/resources/reports/jrxml/";
    private static final String JASPER_PATH = "src/main/resources/reports/compiled/";

    /**
     * Compiles all JRXML files to Jasper files
     */

    public JasperReportCompiler() {
        this.compileAllReports();
    }

    public void compileAllReports() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] jrxmlFiles = resolver.getResources("classpath:reports/jrxml/*.jrxml");

            for (Resource jrxmlResource : jrxmlFiles) {
                String filename = jrxmlResource.getFilename();
                if (filename != null) {
                    String jasperFilename = filename.replace(".jrxml", ".jasper");
                    this.compileReport(filename, jasperFilename);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to compile Jasper reports", e);
        }
    }

    /**
     * Compiles a single JRXML file to Jasper format
     */
    public void compileReport(String jrxmlFilename, String jasperFilename) {
        try {
            // Create directories if they don't exist
            createDirectoriesIfNeeded();

            // Compile the report
            InputStream jrxmlStream = getClass().getClassLoader()
                    .getResourceAsStream("reports/jrxml/" + jrxmlFilename);

            if (jrxmlStream == null) {
                throw new RuntimeException("JRXML file not found: " + jrxmlFilename);
            }

            // Create temporary file for compilation
            Path tempJrxml = Files.createTempFile("report", ".jrxml");
            Files.copy(jrxmlStream, tempJrxml, StandardCopyOption.REPLACE_EXISTING);

            // Compile to Jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(tempJrxml.toString());

            // Save compiled report
            String outputPath = JASPER_PATH + jasperFilename;
            JasperCompileManager.writeReportToXmlFile(jasperReport, outputPath);

            // Clean up temporary file
            Files.deleteIfExists(tempJrxml);

        } catch (Exception e) {
            throw new RuntimeException("Failed to compile report: " + jrxmlFilename, e);
        }
    }

    /**
     * Compiles a JRXML file with updated content
     */
    public void compileReportWithContent(String jrxmlContent, String jasperFilename) {
        try {
            createDirectoriesIfNeeded();

            // Create temporary JRXML file
            Path tempJrxml = Files.createTempFile("report", ".jrxml");
            Files.write(tempJrxml, jrxmlContent.getBytes());

            // Compile to Jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(tempJrxml.toString());

            // Save compiled report
            String outputPath = JASPER_PATH + jasperFilename;
            JasperCompileManager.writeReportToXmlFile(jasperReport, outputPath);

            // Clean up temporary file
            Files.deleteIfExists(tempJrxml);

        } catch (Exception e) {
            throw new RuntimeException("Failed to compile report with content: " + jasperFilename, e);
        }
    }

    private void createDirectoriesIfNeeded() throws IOException {
        // Create JRXML directory if it doesn't exist
        Path jrxmlDir = Paths.get(JRXML_PATH);
        if (!Files.exists(jrxmlDir)) {
            Files.createDirectories(jrxmlDir);
        }

        // Create Jasper directory if it doesn't exist
        Path jasperDir = Paths.get(JASPER_PATH);
        if (!Files.exists(jasperDir)) {
            Files.createDirectories(jasperDir);
        }
    }
}