# myresourceservingproject

This project shows how to use HST Resource URL and Resource Serving operations in HstComponents, and it shows how to use a separate servlet, which write a binary stream to the client, as ```hst:resourcetemplate```.

## Build and Run

```bash
mvn clean verify && mvn -Pcargo.run
```

## Test

Visit Homepage (http://localhost:8080/site/) and click on "Download Now!" button in the homepage. You will be able to download a CSV file.

Also, visit News Page (http://localhost:8080/site/news/) and click on "Download Now!" link. You will be able to download a CSV file.

The Homepage uses an HST Resource URL in the form action attribute and form POST method while the News Page uses query strings and GET method in a link.

## How it works

### The Download Now! button in the Homepage

- The homepage template [homepage-main.ftl](repository-data/webfiles/src/main/resources/site/freemarker/myresourceservingproject/homepage-main.ftl) has a download form with an HST Resource URL:

```
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
```

- When the "Download Now!" button is clicked, HST Container invokes the ```#doBeforeServeResource(...)``` method of [HomePageMainComponent](site/src/main/java/org/example/hst/resource/serving/components/HomePageMainComponent.java).

- Afterward, HST Container will dispatch to the [DocumentListBinariesSerlvet](site/src/main/java/org/example/hst/resource/serving/servlet/DocumentListBinariesSerlvet.java) resource which is configured by ```@hst:resourcetemplate``` and its associated ```hst:template``` node containing the servlet path).

### The Download Now! link in the News Page

- The news list template [newslist-main-newslist.ftl](repository-data/webfiles/src/main/resources/site/freemarker/hstdefault/newslist-main-newslist.ftl) has a download link with an HST Resource URL:

```
  <div style="background-color: #ddffdd">
    <h3>HST Resource URL Test</h3>
    <@hst.resourceURL var="downloadLink" resourceId="download">
      <@hst.param name="start" value="2000-01-01" />
      <@hst.param name="end" value="2018-12-31" />
    </@hst.resourceURL>
    <a href="${downloadLink}">Download Now!</a>
  </div>
```

- When the "Download Now!" link is clicked, HST Container invokes the ```#doBeforeServeResource(...)``` method of [MyEssentialsNewsComponent](site/src/main/java/org/example/hst/resource/serving/components/MyEssentialsNewsComponent.java).

- Afterward, HST Container will dispatch to the [DocumentListBinariesSerlvet](site/src/main/java/org/example/hst/resource/serving/servlet/DocumentListBinariesSerlvet.java) resource which is configured by ```@hst:resourcetemplate``` and its associated ```hst:template``` node containing the servlet path).

## Gotchas

- Because the resource templates are dispatched through ```RequestDispatcher#include(...)```, any call on ```ServletResponse#setContentType(...)``` or ```HttpServletResponse#setHeader(...)``` or ```HttpServletResponse.addHeader(...)``` are not passed back to the original ```HttpServletResponse``` object which is available in the ```#doBeforeServeResource(...)``` method of your ```HstComponent```. Therefore, ContentType or Headers must be set in your ```#doBeforeServeResource(...)``` method, not in the dispatched servlet code.
