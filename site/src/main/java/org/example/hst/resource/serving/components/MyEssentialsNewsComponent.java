package org.example.hst.resource.serving.components;

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

}
