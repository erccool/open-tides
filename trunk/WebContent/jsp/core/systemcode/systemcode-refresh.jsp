<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>

<c:if test="${newRow}">
<tr id="lookup-row-${systemCode.id}" status="new">
</c:if>
	<td><c:out value="${systemCode.category}" /></td>
	<td><c:out value="${systemCode.key}" /></td>
	<td><c:out value="${systemCode.value}" /></td>
	<td>
		<ot:update_button id="${systemCode.id}" page="admin/lookup.jspx" prefix="lookup"/>
		<ot:delete_button id="${systemCode.id}" page="admin/lookup.jspx" title="${systemCode.key}" prefix="lookup"/>                  		
	</td>
<c:if test="${newRow}">
</tr>
<script language="javascript">
IDEYATECH.crud.cancelNew('lookup-');
IDEYATECH.crud.refreshTable('lookup-table-results', {})
</script>
</c:if>