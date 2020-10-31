<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" uri="/WEB-INF/customTags/chatsTags.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cosmodrome</title>
    <link href="/css/styleChats.css" rel="stylesheet" type="text/css">
    <script>if (window.location.pathname !== "/m")
        window.history.replaceState({}, document.title, "/m");</script>
</head>
<body>
<img src="/imgs/data-original-cutted.jpg" id="back-img">
<div id="background">
    <div id="content">
        <div id="menu">

        </div>
        <div id="main">
            <div id="chat-list">
                <tags:list>
                    <c:out value="${profileID}"/>
                </tags:list>
                <!-- TODO jst tag that generate all divs with chat names -->
            </div>
            <div id="messages">
                <tags:messages/>
                <!-- TODO show selected chat messages -->
            </div>
        </div>
    </div>
</div>
</body>
</html>
