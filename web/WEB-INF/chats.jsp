<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cosmodrome</title>
    <link href="/css/styleChats.css" rel="stylesheet" type="text/css">
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
            <div id="chat-list">
                <!-- TODO jst tag that generate all divs with chat names -->
            </div>
            <div id="messages">
                <!-- TODO show selected chat messages -->
            </div>
        </div>
    </div>
</div>
</body>
</html>
