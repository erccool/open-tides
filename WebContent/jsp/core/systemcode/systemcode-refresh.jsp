<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<c:if test="${newRow}">
<div id="row-${systemCode.id}" status="new">
</c:if>
  <span class="systemcode-category"><c:out value="${systemCode.category}"/></span>
  <span class="systemcode-key"><c:out value="${systemCode.key}"/></span>
  <span class="systemcode-value"><c:out value="${systemCode.value}"/></span>
  <span class="action">
    <ot:update_button id="${systemCode.id}" page="admin/lookup.jspx"/>
  	<ot:delete_button id="${systemCode.id}" page="admin/lookup.jspx" title="${systemCode.key}"/>
  </span>
<c:if test="${newRow}">
</div>
</c:if>