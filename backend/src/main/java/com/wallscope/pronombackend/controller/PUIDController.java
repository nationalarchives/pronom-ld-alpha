package com.wallscope.pronombackend.controller;

import com.wallscope.pronombackend.dao.FileFormatDAO;
import com.wallscope.pronombackend.model.FileFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
 * This controller handles all calls to pages where the content does not depend on fetching data from the database
 * Hence the name static. The actual content may change based on the Markdown workflow
 * */
@Controller
public class PUIDController {
    Logger logger = LoggerFactory.getLogger(PUIDController.class);

    @GetMapping(value = {"/fmt/{puid}", "/x-fmt/{puid}"})
    public String fileFormatHandler(Model model, HttpServletRequest request, @PathVariable(required = false) String puid) {
        String[] parts = request.getRequestURI().split("/");
        String puidType = parts[parts.length - 2];
        FileFormatDAO dao = new FileFormatDAO();
        FileFormat f = dao.getFileFormatByPuid(puid, puidType);
        if (f == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no File Format with puid: " + puid);
        }
        logger.debug("File format: "+f);
        model.addAttribute("ff", f);
        return "file-format";
    }

    @GetMapping(value = {"/fmt/{puid}.xml", "/x-fmt/{puid}.xml"}, produces = "text/xml")
    public String xmlSingleSignatureHandler(Model model, HttpServletRequest request, @PathVariable(required = false) String puid) {
        String[] parts = request.getRequestURI().split("/");
        String puidType = parts[parts.length - 2];
        FileFormatDAO dao = new FileFormatDAO();
        FileFormat f = dao.getFileFormatByPuid(puid, puidType);
        if (f == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no File Format with puid: " + puid);
        }
        model.addAttribute("formats", List.of(f));
        return "xml_signatures";
    }

    @GetMapping(value = {"/signature.xml"}, produces = "text/xml")
    public String xmlAllSignatureHandler(Model model) {
        FileFormatDAO dao = new FileFormatDAO();
        List<FileFormat> fs = dao.getAll();
        model.addAttribute("formats", fs);
        return "xml_signatures";
    }

    @GetMapping(value = {"/chr/{puid}", "/x-chr/{puid}", "/sfw/{puid}", "/x-sfw/{puid}", "/cmp/{puid}", "/x-cmp/{puid}"})
    public String genericPUIDHandler(Model model, @PathVariable(required = false) String puid) {
        return "generic-puid";
    }
}
