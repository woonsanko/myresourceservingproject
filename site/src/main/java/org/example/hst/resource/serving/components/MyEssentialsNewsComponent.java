package org.example.hst.resource.serving.components;

import java.util.Calendar;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsNewsComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsNewsComponentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ParametersInfo(type = EssentialsNewsComponentInfo.class)
public class MyEssentialsNewsComponent extends EssentialsNewsComponent {

    private static Logger log = LoggerFactory.getLogger(MyEssentialsNewsComponent.class);

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        log.debug("MyEssentialsNewsComponent#doBeforeRender...");
        super.doBeforeRender(request, response);
    }

    @Override
    public void doBeforeServeResource(HstRequest request, HstResponse response) throws HstComponentException {
        log.debug("MyEssentialsNewsComponent#doBeforeServeResource...");

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
