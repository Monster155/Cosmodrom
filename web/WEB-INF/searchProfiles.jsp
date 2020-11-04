<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="menu" uri="/WEB-INF/customTags/menuTag.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cosmodrome</title>
    <link href="/css/mainStyle.css" rel="stylesheet" type="text/css">
    <link href="/css/styleSearch.css" rel="stylesheet" type="text/css">
    <script>window.history.replaceState({}, document.title, "/search");</script>
</head>
<body>
<img src="/imgs/data-original-cutted.jpg" id="back-img">
<div id="background">
    <div id="content">
        <menu:menu>
            <c:out value='${profileID}'/>
        </menu:menu>
        <div id="main">
            <div id="search">
                <div id="formSearch">
                    <input type="text" placeholder="Enter name..." name="name" id="search-input">
                    <button type="submit" id="search-button" onclick="searchProfiles()">
                        Search
                    </button>
                </div>
            </div>
            <div id="results">
            </div>
        </div>
    </div>
</div>
<script src="/js/searchJS/getProfiles.js"></script>
<script src="/js/searchJS/selectProfile.js"></script>
</body>
</html>
