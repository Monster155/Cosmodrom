<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="menu" uri="/WEB-INF/customTags/menuTag.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cosmodrome</title>
    <link href="/css/mainStyle.css" rel="stylesheet" type="text/css">
    <link href="/css/styleUserPage.css" rel="stylesheet" type="text/css">
    <script>if (window.location.pathname !== "/user")
        window.history.replaceState({}, document.title, "/user?id");</script>
</head>
<body>
<img src="/imgs/data-original-cutted.jpg" id="back-img">
<div id="background">
    <div id="content">
        <menu:menu>
            <c:out value='${profileID}'/>
        </menu:menu>
        <div id="main">
            <div id="info1">
                <div id="photo">
                    <img src="/imgs/ChooseUrProfImgGroup.png" id="photo-img">
                </div>
                <div id="names">
                    <div id="name">
                        <div>
                            Name Surname
                        </div>
                    </div>
                    <div id="description">
                        <div>
                            Description
                        </div>
                    </div>
                </div>
            </div>
            <div id="info2">
                <div id="btn-div">
                    <form method="get" action='/m'>
                        <input type="text" style="display: none" name="id" value="${param.id}">
                        <button type="submit">
                            Open chat
                        </button>
                    </form>
                </div>
                <div id="chat-list">
                    <div>

                    </div>
                </div>
            </div>
            <form method="get" action='/edit' id="info3">
                <button type="submit">
                    Edit profile
                </button>
            </form>
        </div>
    </div>
</div>
<script>let profileID = ${profileID};</script> <!-- # -->
<script src="/js/userPageJS/loadInfo.js"></script>
</body>
</html>
