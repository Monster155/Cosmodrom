<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" uri="/WEB-INF/customTags/indexTags.tld" %>
<html>
<head>
    <title>Cosmodrome</title>
    <link href="/css/styleIndex.css" rel="stylesheet" type="text/css">
    <script src="js/indexJS/catchParams.js"></script>
</head>
<body>
<img src="/imgs/data-original-cutted.jpg" id="back-img">
<div id="background">
    <div id="content">
        <div id="side-bar">
            <div id="choose-btn"></div>
            <div id="side-bar-buttons">
                <tags:sidebar-button oninputFunctionName="login()" text="Login">
                    <svg width="25px" height="25px" viewBox="0 0 16 16" class="bi bi-person-circle" fill="currentColor"
                         xmlns="http://www.w3.org/2000/svg">
                        <path d="M13.468 12.37C12.758 11.226 11.195 10 8 10s-4.757 1.225-5.468 2.37A6.987 6.987 0 0 0 8 15a6.987 6.987 0 0 0 5.468-2.63z"/>
                        <path fill-rule="evenodd" d="M8 9a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                        <path fill-rule="evenodd"
                              d="M8 1a7 7 0 1 0 0 14A7 7 0 0 0 8 1zM0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8z"/>
                    </svg>
                </tags:sidebar-button>
                <tags:sidebar-button oninputFunctionName="registration()" text="Sign In">
                    <svg width="25px" height="25px" viewBox="0 0 16 16" class="bi bi-pencil-square" fill="currentColor"
                         xmlns="http://www.w3.org/2000/svg">
                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                        <path fill-rule="evenodd"
                              d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                    </svg>
                </tags:sidebar-button>
                <tags:sidebar-button oninputFunctionName="restore()" text="Forgot Password?">
                    <svg width="20px" height="27px" viewBox="0 0 16 16" class="bi bi-lock-fill" fill="currentColor"
                         xmlns="http://www.w3.org/2000/svg">
                        <path d="M2.5 9a2 2 0 0 1 2-2h7a2 2 0 0 1 2 2v5a2 2 0 0 1-2 2h-7a2 2 0 0 1-2-2V9z"/>
                        <path fill-rule="evenodd" d="M4.5 4a3.5 3.5 0 1 1 7 0v3h-1V4a2.5 2.5 0 0 0-5 0v3h-1V4z"/>
                    </svg>
                </tags:sidebar-button>
            </div>
        </div>
        <div id="main">
            <div id="groups-container">
                <tags:container action="in" method="post" groupName="login" headText="Login Here">
                    <tags:input id="login1" inputType="email" inputPlaceholder="E-mail"
                                inputName="email" inputID="email1">
                        <svg width="40px" height="25px" viewBox="0 0 16 16" class="bi bi-envelope"
                             fill="currentColor"
                             xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                  d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2zm13 2.383l-4.758 2.855L15 11.114v-5.73zm-.034 6.878L9.271 8.82 8 9.583 6.728 8.82l-5.694 3.44A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.739zM1 11.114l4.758-2.876L1 5.383v5.73z"/>
                        </svg>
                    </tags:input>
                    <tags:input id="login2" inputType="password" inputPlaceholder="Password"
                                inputName="password" inputID="password1">
                        <svg width="50px" height="25px" viewBox="0 0 16 16" class="bi bi-key" fill="currentColor"
                             xmlns="http://www.w3.org/2000/svg"
                             style="transform: rotate(90deg) scale(1,-1);">
                            <path fill-rule="evenodd"
                                  d="M0 8a4 4 0 0 1 7.465-2H14a.5.5 0 0 1 .354.146l1.5 1.5a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0L13 9.207l-.646.647a.5.5 0 0 1-.708 0L11 9.207l-.646.647a.5.5 0 0 1-.708 0L9 9.207l-.646.647A.5.5 0 0 1 8 10h-.535A4 4 0 0 1 0 8zm4-3a3 3 0 1 0 2.712 4.285A.5.5 0 0 1 7.163 9h.63l.853-.854a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .708 0l.646.647.793-.793-1-1h-6.63a.5.5 0 0 1-.451-.285A3 3 0 0 0 4 5z"/>
                            <path d="M4 8a1 1 0 1 1-2 0 1 1 0 0 1 2 0z"/>
                        </svg>
                    </tags:input>
                    <tags:container-btn text="Login">
                        <svg width="23px" height="25px" viewBox="0 0 16 16" class="bi bi-door-open"
                             fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                  d="M1 15.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5zM11.5 2H11V1h.5A1.5 1.5 0 0 1 13 2.5V15h-1V2.5a.5.5 0 0 0-.5-.5z"/>
                            <path fill-rule="evenodd"
                                  d="M10.828.122A.5.5 0 0 1 11 .5V15h-1V1.077l-6 .857V15H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117z"/>
                            <path d="M8 9c0 .552.224 1 .5 1s.5-.448.5-1-.224-1-.5-1-.5.448-.5 1z"/>
                        </svg>
                    </tags:container-btn>
                </tags:container>
                <tags:container action="up" method="post" groupName="register" headText="Registration">
                    <tags:input id="reg1" inputType="email" inputPlaceholder="E-mail"
                                inputName="email" inputID="email2">
                        <svg width="40px" height="25px" viewBox="0 0 16 16" class="bi bi-envelope"
                             fill="currentColor"
                             xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                  d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2zm13 2.383l-4.758 2.855L15 11.114v-5.73zm-.034 6.878L9.271 8.82 8 9.583 6.728 8.82l-5.694 3.44A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.739zM1 11.114l4.758-2.876L1 5.383v5.73z"/>
                        </svg>
                    </tags:input>
                    <tags:input id="reg2" inputType="password" inputPlaceholder="Password"
                                inputName="password" inputID="password2">
                        <svg width="50px" height="25px" viewBox="0 0 16 16" class="bi bi-key"
                             fill="currentColor"
                             xmlns="http://www.w3.org/2000/svg"
                             style="transform: rotate(90deg) scale(1,-1);">
                            <path fill-rule="evenodd"
                                  d="M0 8a4 4 0 0 1 7.465-2H14a.5.5 0 0 1 .354.146l1.5 1.5a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0L13 9.207l-.646.647a.5.5 0 0 1-.708 0L11 9.207l-.646.647a.5.5 0 0 1-.708 0L9 9.207l-.646.647A.5.5 0 0 1 8 10h-.535A4 4 0 0 1 0 8zm4-3a3 3 0 1 0 2.712 4.285A.5.5 0 0 1 7.163 9h.63l.853-.854a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .708 0l.646.647.793-.793-1-1h-6.63a.5.5 0 0 1-.451-.285A3 3 0 0 0 4 5z"/>
                            <path d="M4 8a1 1 0 1 1-2 0 1 1 0 0 1 2 0z"/>
                        </svg>
                    </tags:input>
                    <tags:input id="reg3" inputType="password" inputPlaceholder="Repeat password"
                                inputName="password-repeat" inputID="password-repeat2">
                        <svg width="50px" height="25px" viewBox="0 0 16 16" class="bi bi-key"
                             fill="currentColor"
                             xmlns="http://www.w3.org/2000/svg"
                             style="transform: rotate(90deg) scale(1,-1);">
                            <path fill-rule="evenodd"
                                  d="M0 8a4 4 0 0 1 7.465-2H14a.5.5 0 0 1 .354.146l1.5 1.5a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0L13 9.207l-.646.647a.5.5 0 0 1-.708 0L11 9.207l-.646.647a.5.5 0 0 1-.708 0L9 9.207l-.646.647A.5.5 0 0 1 8 10h-.535A4 4 0 0 1 0 8zm4-3a3 3 0 1 0 2.712 4.285A.5.5 0 0 1 7.163 9h.63l.853-.854a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .708 0l.646.647.793-.793-1-1h-6.63a.5.5 0 0 1-.451-.285A3 3 0 0 0 4 5z"/>
                            <path d="M4 8a1 1 0 1 1-2 0 1 1 0 0 1 2 0z"/>
                        </svg>
                    </tags:input>
                    <tags:container-btn text="Sign Up">
                        <svg width="23px" height="25px" viewBox="0 0 16 16" class="bi bi-door-open"
                             fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                  d="M1 15.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5zM11.5 2H11V1h.5A1.5 1.5 0 0 1 13 2.5V15h-1V2.5a.5.5 0 0 0-.5-.5z"/>
                            <path fill-rule="evenodd"
                                  d="M10.828.122A.5.5 0 0 1 11 .5V15h-1V1.077l-6 .857V15H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117z"/>
                            <path d="M8 9c0 .552.224 1 .5 1s.5-.448.5-1-.224-1-.5-1-.5.448-.5 1z"/>
                        </svg>
                    </tags:container-btn>
                </tags:container>
                <tags:container action="rest" method="post" groupName="restore" headText="Restore Password">
                    <tags:input id="restore1" inputType="email" inputPlaceholder="E-mail"
                                inputName="email" inputID="email3">
                        <svg width="40px" height="25px" viewBox="0 0 16 16" class="bi bi-envelope"
                             fill="currentColor"
                             xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                  d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2zm13 2.383l-4.758 2.855L15 11.114v-5.73zm-.034 6.878L9.271 8.82 8 9.583 6.728 8.82l-5.694 3.44A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.739zM1 11.114l4.758-2.876L1 5.383v5.73z"/>
                        </svg>
                    </tags:input>
                    <tags:container-btn text="Restore">
                        <svg width="40px" height="40px" viewBox="0 0 16 16" class="bi bi-arrow-repeat"
                             fill="currentColor"
                             xmlns="http://www.w3.org/2000/svg">
                            <path d="M11.534 7h3.932a.25.25 0 0 1 .192.41l-1.966 2.36a.25.25 0 0 1-.384 0l-1.966-2.36a.25.25 0 0 1 .192-.41zm-11 2h3.932a.25.25 0 0 0 .192-.41L2.692 6.23a.25.25 0 0 0-.384 0L.342 8.59A.25.25 0 0 0 .534 9z"/>
                            <path fill-rule="evenodd"
                                  d="M8 3c-1.552 0-2.94.707-3.857 1.818a.5.5 0 1 1-.771-.636A6.002 6.002 0 0 1 13.917 7H12.9A5.002 5.002 0 0 0 8 3zM3.1 9a5.002 5.002 0 0 0 8.757 2.182.5.5 0 1 1 .771.636A6.002 6.002 0 0 1 2.083 9H3.1z"/>
                        </svg>
                    </tags:container-btn>
                </tags:container>
            </div>
        </div>
    </div>
</div>
<script src="js/indexJS/checkPassword.js"></script>
<script src="js/indexJS/codeIndex.js"></script>
</body>
</html>
