/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amandine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 *
 * @author 222252
 */
public class NewEmptyJUnitTest {

    @Test
    public void NewEmptyJUnitTest() {
        //need the list of files
        //need the descriptions for each
        //create a fig where everything is known
        String description = "The Autumn Winter 2015 collection";
        String image = "/images/automn winter 2015/cover/homepagesyrma.jpg";
        String actual
                = imageText(description, image);
        String expected
                = "                <figure class=\"uk-overlay uk-overlay-hover\">\n"
                + "                    <img alt=\"The Autumn Winter 2015 collection\" src=\"/images/automn winter 2015/cover/homepagesyrma.jpg\">\n"
                + "                    <figcaption class=\"uk-overlay-panel uk-overlay-background uk-overlay-slide-left uk-flex uk-flex-top\">\n"
                + "                            <p>The Autumn Winter 2015 collection</p>\n"
                + "                            <a class=\"uk-position-cover\"></a>\n"
                + "                    </figcaption>\n"
                + "                </figure>\n";

        assertEquals(expected, actual);
    }

    public String imageText(String description, String image) {
        String actual
                = "                <figure class=\"uk-overlay uk-overlay-hover\">\n"
                + "                    <img alt=\"" + description + "\" src=\"" + image + "\"/>\n"
                + "                    <figcaption class=\"uk-overlay-panel uk-overlay-background uk-overlay-slide-left uk-flex uk-flex-top\">\n"
                + "                            <p>" + description + "</p>\n"
                + "                            <a class=\"uk-position-cover\"></a>\n"
                + "                    </figcaption>\n"
                + "                </figure>";
        return actual;
    }

    @Test
    public void imagefiles() {
        //List<String> results = new ArrayList<String>();
        String description = "The Spring Summer 2016 collection";
        File[] files = new File("C:\\Users\\janitha\\Desktop\\2016 approved").listFiles();
//If this pathname does not denote a directory, then listFiles() returns null. 
        assertEquals(20, files.length);
        for (File file : files) {
            if (file.isFile()) {
                System.out.println(imageText(getADescription(file.getName()), "images/spring summer 2016/" + file.getName()));
            }
        }
    }

//    @Test
    public String pdflookbook() throws IOException {
        String filePath = "C:\\Users\\janitha\\OneDrive\\Documents\\lookbookSS2016.pdf";
        InputStream inputStream = null;
        String statementPDF = null;
        try {
            inputStream = new FileInputStream(filePath);
            PDFParser parser = new PDFParser(inputStream);

            // This will parse the stream and populate the COSDocument object.
            parser.parse();

            // Get the document that was parsed.
            COSDocument cosDoc = parser.getDocument();

            // This class will take a pdf document and strip out all of the text and 
            // ignore the formatting and such.
            PDFTextStripper pdfStripper = new PDFTextStripper();

            // This is the in-memory representation of the PDF document
            PDDocument pdDoc = new PDDocument(cosDoc);
            pdfStripper.setStartPage(3);
            pdfStripper.setEndPage(pdDoc.getNumberOfPages() - 1);
            assertEquals(41, pdDoc.getNumberOfPages() - 1);

            // This will return the text of a document.
            statementPDF = pdfStripper.getText(pdDoc);
//            System.out.println(statementPDF);

//            String [] statementPDFArray = statementPDF.split("\\n");
//            assertEquals(256, statementPDFArray.length);
        } catch (Exception e) {
            //Syste
            String errorMessage = "\nUnexpected Exception: " + e.getClass() + "\n" + e.getMessage();
            for (StackTraceElement trace : e.getStackTrace()) {
                errorMessage += "\n\t" + trace;
            }
            System.out.println(errorMessage);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return statementPDF;
    }

    @Test
    public void hashmaptest() {
        HashMap<String, String> model_material = new HashMap<>();
        model_material.put("Robe MUSICI", "100% coton");
        assertEquals("100% coton", model_material.get("Robe MUSICI"));
        model_material.put("MUSICI", "Robe 100% coton");
        assertEquals("Robe 100% coton", model_material.get("MUSICI"));
    }

    @Test
    public void createSentenceFromModels() {
        String imageName = "alima ikaria front 2.jpg";
        HashMap<String, String> model_material = new HashMap<>();
        model_material.put("ALIMA", "Jacket mix material");
        model_material.put("IKARIA", "dress mix material");
        String actual = "ALIMA " + model_material.get("alima".toUpperCase()) + " IKARIA " + model_material.get("ikaria".toUpperCase());
        String expected = "ALIMA Jacket mix material IKARIA dress mix material";
        assertEquals(expected, actual);
    }

    @Test
    public void createSentenceFromImageName() {
        HashMap<String, String> model_material = new HashMap<>();
        model_material.put("ALIMA", "ALIMA Jacket mix material");
        model_material.put("IKARIA", "IKARIA dress mix material");
        String imageName = "alima ikaria front 2.jpg";
        String actual = createTheDescription(imageName, model_material);

        //String actual = "ALIMA " + model_material.get("alima".toUpperCase())+ " IKARIA " + model_material.get("ikaria".toUpperCase());
        String expected = "ALIMA Jacket mix material IKARIA dress mix material ";
        assertEquals(expected, actual);
    }

    private String createTheDescription(String imageName, HashMap<String, String> model_material) {
        String[] parts = imageName.split(" ");
        String actual = "";
        for (String p : parts) {
            if (model_material.get(p.toUpperCase()) != null) {
                actual += model_material.get(p.toUpperCase()) + " ";
            }
        }
        return actual;
    }

//    @Test
    public String getADescription(String imageName) {
        //String imageName = "alima ikaria front 2.jpg";
        //HashMap<String, String> model_material = null;
        String[] modelCompositionOnEachLine = null;
        try {
            modelCompositionOnEachLine = pdflookbook().split("\\n");
        } catch (IOException ex) {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        HashMap<String, String> model_material = getHashMapFromSkipArray(modelCompositionOnEachLine);
        String actual = createTheDescription(imageName, model_material);
//        String expected = "ALIMA Jacket mix material IKARIA dress mix material ";
//        assertEquals(expected, actual);
        return actual;
    }

    @Test
    public void createHashMapFromPdf() throws IOException {
        String textFromPDF = pdflookbook();
        String[] modelCompositionOnEachLine = textFromPDF.split("\\n");
        assertEquals(256, modelCompositionOnEachLine.length);
        assertEquals("Robe MUSICI", modelCompositionOnEachLine[0].trim());
        assertEquals("100% coton", modelCompositionOnEachLine[1].trim());

        String model = (modelCompositionOnEachLine[0].split(" "))[0];
        assertEquals("Robe", model);
//        model_material.put(model, modelCompositionOnEachLine[1]);
//        assertEquals(modelCompositionOnEachLine[1], model_material.get("Robe"));      

        HashMap<String, String> model_material = getHashMapFromSkipArray(modelCompositionOnEachLine);

        assertEquals(57, model_material.size());
        assertEquals("MUSICI dress 100% cotton", model_material.get("MUSICI").trim());
    }

    private HashMap<String, String> getHashMapFromSkipArray(String[] modelCompositionOnEachLine) {
        HashMap<String, String> model_material = new HashMap<>();
        for (int i = 0; i < modelCompositionOnEachLine.length; i += 2) {
            String modelType[] = (modelCompositionOnEachLine[i].split(" "));
            model_material.put(modelType[0], modelCompositionOnEachLine[i].trim() + " " + modelCompositionOnEachLine[i + 1].trim());
        }
        return model_material;
    }
}
