<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" uri="/WEB-INF/customTags/chatsTags.tld" %>
<%@ taglib prefix="menu" uri="/WEB-INF/customTags/menuTag.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cosmodrome</title>
    <link href="/css/mainStyle.css" rel="stylesheet" type="text/css">
    <link href="/css/styleChats.css" rel="stylesheet" type="text/css">
    <script>if (window.location.pathname !== "/m")
        window.history.replaceState({}, document.title, "/m");</script>
</head>
<body>
<img src="/imgs/data-original-cutted.jpg" id="back-img">
<div id="background">
    <div id="content">
        <menu:menu>
            <c:out value='${profileID}'/>
        </menu:menu>
        <div id="main">
            <div id="chat-list">
                <tags:list>
                    <c:out value="${profileID}"/>
                </tags:list>
            </div>
            <div id="chat-view">
                <div id="messages">
                    <tags:messages>
                        <c:out value="${param.chat}"/>
                    </tags:messages>
                </div>
                <div id="change">
                    <button id="edit-button" class="change-buttons" onclick="editMessage()">
                        <div>
                            Edit
                        </div>
                    </button>
                    <button id="delete-button" class="change-buttons" onclick="deleteMessage()">
                        <div>
                            Delete
                        </div>
                    </button>
                    <button id="close-button" class="change-buttons" onclick="deselectMessage()">
                        <div>
                            X
                        </div>
                    </button>
                </div>
                <div id="send-message-background">
                    <div id="send-message">
                        <input type="text" placeholder="Enter text..." name="text" id="send-message-input" required/>
                        <button id="send-message-btn" onclick="sendMessage('send-message-input')">
                            <img src="/imgs/SendImg.png" id="send-message-btn-img">
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- script has methods # script uses methods -->
<script>let profileID = ${profileID};</script> <!-- # -->
<script>let isChanging = false;</script> <!-- # -->
<script src="/js/chatsJS/triggerButtonOnEnterPressed.js"></script> <!-- # -->
<script src="/js/chatsJS/showChangeButtons.js"></script> <!-- showChangeButtons, hideChangeButtons # -->
<script src="/js/chatsJS/changeMessage.js"></script> <!-- editMessage, deleteMessage # -->
<script src="/js/chatsJS/alignMessagesByOwner.js"></script> <!-- alignMessages # -->
<script src="/js/chatsJS/hideMessageInput.js"></script> <!-- hideMessages, unhideMessages # -->
<script src="/js/chatsJS/selectMessage.js"></script> <!-- selectMessage, deselectMessage # show(hide)ChangeButtons -->
<script src="/js/chatsJS/searchForNewMessages.js"></script> <!-- searchForNewMessages # alignMessages, selMesVar -->
<script src="/js/chatsJS/sendMessage.js"></script> <!-- sendMessage # searchForNewMessages -->
<script src="/js/chatsJS/bindChats.js"></script> <!-- replaceURL # searchForNewMessages, unhideMessages -->
</body>
</html>
