package org.example.hst.resource.serving.components;

import java.util.Calendar;

import org.hippoecm.hst.core.component.GenericHstComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePageMainComponent extends GenericHstComponent {

    private static Logger log = LoggerFactory.getLogger(HomePageMainComponent.class);

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        log.debug("HomePageMainComponent#doBeforeRender...");
        super.doBeforeRender(request, response);
    }

    @Override
    public void doBeforeServeResource(HstRequest request, HstResponse response) throws HstComponentException {
        log.debug("HomePageMainComponent#doBeforeServeResource...");

        if ("download".equals(request.getResourceID())) {
            final Calendar start = MyDemoMiscUtils.parseDate(request.getParameter("start"));
            final Calendar end = MyDemoMiscUtils.parseDate(request.getParameter("end"));
            // set a model object in request attributes, so that the resource dispatching servlet will read it to write CSV.
            request.setAttribute("documents", MyDemoMiscUtils.queryDocumentsBetweenPublicationDates(start, end));

            // Note: this is a bit tricky part, but response content type or headers must be set in this method,
            //       not in the 'dispatched' ('included') servlet because any settings there will be ignored
            //       in the original response in this component.
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=\"Document_List.csv\"");
        }
    }
}
