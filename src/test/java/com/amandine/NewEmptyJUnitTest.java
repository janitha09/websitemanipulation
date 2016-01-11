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
                + "                    <img alt=\"" + description + "\" src=\"" + image + "\">\n"
                + "                    <figcaption class=\"uk-overlay-panel uk-overlay-background uk-overlay-slide-left uk-flex uk-flex-top\">\n"
                + "                            <p>" + description + "</p>\n"
                + "                            <a class=\"uk-position-cover\"></a>\n"
                + "                    </figcaption>\n"
                + "                </figure>\n";
        return actual;
    }

    @Test
    public void imagefiles() {
        //List<String> results = new ArrayList<String>();
        String description = "The Spring Summer 2016 collection";
        File[] files = new File("C:\\Users\\222252\\OneDrive\\Pictures\\SS2016\\capsule slide show").listFiles();
//If this pathname does not denote a directory, then listFiles() returns null. 
        assertEquals(113, files.length);
        for (File file : files) {
            if (file.isFile()) {
                System.out.println(imageText(file.getName(), "/images/spring summer 2016/" + file.getName()));
            }
        }
    }

    @Test
    public void pdflookbook() throws IOException {
        String filePath = "C:\\Users\\222252\\OneDrive\\Documents\\lookbookSS2016.pdf";
        InputStream inputStream = null;

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
            PDDocument pdDoc = new PDDocument(cosDoc.);
            pdfStripper.setStartPage(3);
            pdfStripper.setEndPage(pdDoc.getNumberOfPages()-1);

            // This will return the text of a document.
            String statementPDF = pdfStripper.getText(pdDoc);
            System.out.println(statementPDF);
        } catch (Exception e) {
            String errorMessage;// += "\nUnexpected Exception: "  + e.getClass() + "\n" + e.getMessage();
//            for (StackTraceElement trace in  e.getStackTrace()) {
//                errorMessage += "\n\t" + trace;
//            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
