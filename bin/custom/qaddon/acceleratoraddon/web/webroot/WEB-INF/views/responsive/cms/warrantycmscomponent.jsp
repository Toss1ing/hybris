<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="warranty-section">
    <h3>Warranty</h3>
    <p>This product comes with ${warrantyYears} year<c:if test="${warrantyYears > 1}">s</c:if> warranty.</p>
</div>

