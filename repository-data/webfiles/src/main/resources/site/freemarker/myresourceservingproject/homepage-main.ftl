<#include "../include/imports.ftl">

<@hst.setBundle basename="essentials.homepage"/>
<div>
  <h1><@fmt.message key="homepage.title" var="title"/>${title?html}</h1>
  <p><@fmt.message key="homepage.text" var="text"/>${text?html}</p>
  <#if !hstRequest.requestContext.cmsRequest>
    <p>
      [This text can be edited
      <a href="http://localhost:8080/cms/?1&path=/content/documents/administration/labels/homepage" target="_blank">here</a>.]
    </p>
  </#if>
</div>

<div style="background-color: #ddffdd">
  <h3>HST Resource URL Test</h3>
  <@hst.resourceURL var="downloadLink" resourceId="download" />
  <form method="POST" action="${downloadLink}">
    Download document list published on between
    <input type="text" name="start" value="2000-01-01" size="10" placeholder="yyyy-MM-dd" /> ~ 
    <input type="text" name="end" value="2018-12-31" size="10" placeholder="yyyy-MM-dd" />
    <input type="submit" value="Download Now!" />
  </form>
</div>

<div>
  <@hst.include ref="container"/>
</div>