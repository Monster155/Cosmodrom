<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="menu" uri="/WEB-INF/customTags/menuTag.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cosmodrome</title>
    <link href="/css/mainStyle.css" rel="stylesheet" type="text/css">
    <link href="/css/styleEditProfile.css" rel="stylesheet" type="text/css">
    <script>window.history.replaceState({}, document.title, "/create-profile");</script>
</head>
<body>
<img src="/imgs/data-original-cutted.jpg" id="back-img">
<div id="background">
    <div id="content">
        <menu:menu>
            <c:out value='${profileID}'/>
        </menu:menu>
        <div id="main">
            <form method="post" action="/reg" id="formSave" enctype="multipart/form-data">
                <div id="main-elements">
                    <div id="photo">
                        <img src="/imgs/ChooseUrProfImgGroup.png" id="photo-img">
                        <input type="file" placeholder="Photo" name="photo" id="photo-input"
                               accept="image/*" capture>
                    </div>
                    <div id="inputs">
                        <div id="name" class="input-area">
                            <input type="text" placeholder="Name..." name="name" required>
                        </div>
                        <line></line>
                        <div id="surname" class="input-area">
                            <input type="text" placeholder="Surname..." name="surname" required>
                        </div>
                        <line></line>
                        <div id="description" class="input-area">
                            <input type="text" placeholder="Description..." name="description">
                        </div>
                    </div>
                </div>
                <button type="submit" id="btn">
                    Save
                </button>
            </form>
        </div>
    </div>
</div>
<script src="/js/editProfileJS/inputPhoto.js"></script>
</body>
</html>
