package org.example.hst.resource.serving.components;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.example.hst.resource.serving.beans.BaseDocument;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.builder.Constraint;
import org.hippoecm.hst.content.beans.query.builder.ConstraintBuilder;
import org.hippoecm.hst.content.beans.query.builder.HstQueryBuilder;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.repository.util.DateTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MyDemoMiscUtils {

    private static Logger log = LoggerFactory.getLogger(MyDemoMiscUtils.class);

    private static final String[] DATE_FORMATS = { "yyyy-MM-dd" };

    private static final Calendar MIN_DATE = Calendar.getInstance();
    private static final Calendar MAX_DATE = Calendar.getInstance();

    static {
        try {
            MIN_DATE.setTime(DateUtils.parseDate("2000-01-01", DATE_FORMATS));
            MAX_DATE.setTime(DateUtils.parseDate("2018-12-31", DATE_FORMATS));
        } catch (ParseException e) {
            log.error("Date parsing error.", e);
        }
    }

    static Calendar parseDate(final String s) {
        if (StringUtils.isNotBlank(s)) {
            try {
                Date date = DateUtils.parseDate(s, DATE_FORMATS);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return cal;
            } catch (ParseException e) {
                log.error("Invalid date value: '{}'.", s);
            }
        }

        return null;
    }

    static List<HippoBean> queryDocumentsBetweenPublicationDates(final Calendar start, final Calendar end) {
        List<HippoBean> docs = new LinkedList<>();

        try {
            HstRequestContext requestContext = RequestContextProvider.get();

            HippoBean scope = requestContext.getSiteContentBaseBean();

            Constraint constraint =
                    ConstraintBuilder.constraint("hippostdpubwf:publicationDate")
                    .between((start != null) ? start : MIN_DATE, (end != null) ? end : MAX_DATE, DateTools.Resolution.DAY);

            HstQuery hstQuery =
                    HstQueryBuilder.create(scope)
                    .ofTypes(BaseDocument.class)
                    .where(constraint).build();

            HstQueryResult result = hstQuery.execute();

            for (HippoBeanIterator it = result.getHippoBeans(); it.hasNext(); ) {
                docs.add(it.next());
            }
        } catch (Exception e) {
            log.error("Failed to query documents.", e);
        }

        return docs;
    }

    private MyDemoMiscUtils() {
    }
}
