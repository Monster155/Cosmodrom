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
            <div id="chat-view">
                <div id="messages">
                    <tags:messages>
                        <c:out value="${param.chat}"/>
                    </tags:messages>
                    <!-- TODO show selected chat messages -->
                </div>
                <div id="send-message">
                    <input type="text" placeholder="Enter text..." name="text" id="send-message-input" required/>
                    <button type="submit" id="send-message-btn"></button>
                </div>
            </div>
        </div>
    </div>
</div>
<script>let profileID = ${profileID};</script>
<script src="/js/chatsJS/alignMessagesByOwner.js"></script>
<script src="/js/chatsJS/searchForNewMessages.js"></script>
<script src="/js/chatsJS/hideMessageInput.js"></script>
<script src="/js/chatsJS/selectMessage.js"></script>
<script src="/js/chatsJS/bindChats.js"></script>
</body>
</html>
