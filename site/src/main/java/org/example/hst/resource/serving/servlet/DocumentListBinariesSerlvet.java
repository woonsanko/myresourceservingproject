package org.example.hst.resource.serving.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentListBinariesSerlvet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(DocumentListBinariesSerlvet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("DocumentListBinariesSerlvet#doGet...");

        final List<HippoBean> documents = (List<HippoBean>) request.getAttribute("documents");

        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=\"Document_List.csv\"");

        try (ServletOutputStream out = response.getOutputStream()) {
            out.println("Name,Path,Pub_Date");

            if (documents != null) {
                for (HippoBean document : documents) {
                    final Calendar pubDate = JcrUtils.getDateProperty(document.getNode(), "hippostdpubwf:publicationDate", null);
                    final String pubDateStr = (pubDate != null) ? DateFormatUtils.format(pubDate, "yyyy-MM-dd") : "";
                    out.println(String.format("%s,%s,%s", document.getDisplayName(), document.getPath(), pubDateStr));
                }
            }
        } catch (RepositoryException e) {
            log.error("Repository exception occurred.", e);
        }
    }
}
