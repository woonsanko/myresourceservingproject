package org.example.hst.resource.serving.components;

import org.hippoecm.hst.core.component.GenericHstComponent;
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

}
