<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cosmodrome</title>
    <link href="/css/styleUserPage.css" rel="stylesheet" type="text/css">
    <script>if (window.location.pathname !== "/user")
        window.history.replaceState({}, document.title, "/user?id");</script>
</head>
<body>
<img src="/imgs/data-original-cutted.jpg" id="back-img">
<div id="background">
    <div id="content">
        <div id="menu">

        </div>
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
                    <button type="submit">
                        Open chat
                    </button>
                </div>
                <div id="chat-list">
                    <div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/userPageJS/loadInfo.js"></script>
</body>
</html>
